import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Recipe} from '../dto/recipe';
import {RecipeUrlDto} from '../dto/recipe-url-dto';
import {RecipeService} from '../recipe.service';
import {SignInService} from "../service/sign-in.service";
import {MatChipInputEvent, MatPaginator, MatSnackBar, PageEvent} from '@angular/material';
import {COMMA, ENTER} from "@angular/cdk/keycodes";

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.scss']
})
export class RecipesComponent implements OnInit, AfterViewInit {
  recipes: Recipe[] = [];
  pagedRecipes: Recipe[] = [];

  recipesLength = 0;
  pageSize = 12;
  @ViewChild('bottomPaginator') bottomPaginator: MatPaginator;
  @ViewChild('topPaginator') topPaginator: MatPaginator;

  readonly separatorKeysCodes: number[] = [COMMA, ENTER];
  searchTerms: string[];

  noRecipesInfo = 'Nie dodano jeszcze żadnych przepisów.';

  constructor(private recipeService: RecipeService,
              private snackBar: MatSnackBar) {
    this.searchTerms = [];
  }

  ngOnInit() {

  }

  ngAfterViewInit() {
    this.getAll();
  }

  add(url: string): void {
    url = url.trim();
    if (!url) {
      return;
    }
    this.recipeService.add({url: url} as RecipeUrlDto)
      .subscribe(recipe => {
        this.recipes.unshift(recipe);
        if (this.topPaginator.pageIndex === 0) {
          let pageEvent = new PageEvent();
          pageEvent.pageIndex = 0;
          pageEvent.length = this.recipes.length;
          pageEvent.pageSize = this.topPaginator.pageSize;
          this.changeList(pageEvent);
        }

        this.snackBar.open('Pomyślnie dodano nowy przepis.', 'Zamknij', {
          duration: 3000
        });
      });
  }

  private getAll(): void {
    this.getAllRecipes();
  }

  changeListTop(event: PageEvent) {
    this.changeList(event);
    this.bottomPaginator.pageSize = event.pageSize;
    this.bottomPaginator.length = event.length;
    this.bottomPaginator.pageIndex = event.pageIndex;
  }

  changeListBottom(event: PageEvent) {
    this.changeList(event);
    this.topPaginator.pageSize = event.pageSize;
    this.topPaginator.length = event.length;
    this.topPaginator.pageIndex = event.pageIndex;
  }

  private changeList(event: PageEvent) {
    this.pagedRecipes = this.recipes.slice(event.pageIndex * event.pageSize, event.pageIndex * event.pageSize + event.pageSize);
    this.recipesLength = event.length;
  }

  addSearchTerm(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.searchTerms.push(value.trim());
    }

    if (input) {
      input.value = '';
    }
  }

  removeSearchTerm(searchTerm: string): void {
    const index = this.searchTerms.indexOf(searchTerm);
    if (index >= 0) {
      this.searchTerms.splice(index, 1);
    }
    if (!this.searchTerms.length) {
      this.getAllRecipes();
    }
  }

  search() {
    this.searchRecipesByTerms();
  }

  private searchRecipesByTerms() {
    this.recipeService.searchByTerms(this.searchTerms)
      .subscribe(recipes => {
        if (!recipes.length) {
          this.noRecipesInfo = 'Nie znaleziono przepisów pasujących do kryteriów.';
        }
        this.recipes = recipes;
        this.recipesLength = this.recipes.length;
        this.pagedRecipes = this.recipes.slice(0, this.pageSize);
      });
  }

  private getAllRecipes() {
    this.recipeService.getAll()
      .subscribe(recipes => {
        this.recipes = recipes;
        this.recipesLength = this.recipes.length;
        this.pagedRecipes = this.recipes.slice(0, this.pageSize);
      });
  }
}
