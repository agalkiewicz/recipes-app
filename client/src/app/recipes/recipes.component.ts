import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Recipe} from '../_model/recipe';
import {RecipeUrlDto} from '../dto/recipe-url-dto';
import {RecipeService} from '../_service/recipe.service';
import {SignInService} from "../_service/sign-in.service";
import {MatChipInputEvent, MatInput, MatPaginator, MatSnackBar, PageEvent} from '@angular/material';
import {COMMA, ENTER} from "@angular/cdk/keycodes";
import {HttpErrorResponse} from "@angular/common/http";

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
  @ViewChild('searchInput') searchInput: ElementRef;
  @ViewChild('recipeUrl') recipeUrl: ElementRef;

  readonly separatorKeysCodes: number[] = [COMMA, ENTER];
  searchTerms: string[];

  noRecipesInfo = 'Nie dodano jeszcze żadnych przepisów.';

  constructor(private recipeService: RecipeService,
              private snackBar: MatSnackBar) {
    this.searchTerms = [];
  }

  showSearch = false;
  showAddDiv = false;

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
      .subscribe((recipe: Recipe) => {
          this.recipes.unshift(recipe);
          let pageEvent = new PageEvent();
          pageEvent.pageIndex = this.topPaginator.pageIndex;
          pageEvent.length = this.recipes.length;
          pageEvent.pageSize = this.topPaginator.pageSize;
          this.changeList(pageEvent);

          this.showAddDiv = false;

          this.snackBar.open('Pomyślnie dodano przepis.', 'Zamknij', {
            duration: 5000
          });
        },
        (err: HttpErrorResponse) => {
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
    if (this.searchTerms.length) {
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
  }

  private getAllRecipes() {
    this.recipeService.getAll()
      .subscribe(recipes => {
        this.recipes = recipes;
        this.recipesLength = this.recipes.length;
        this.pagedRecipes = this.recipes.slice(0, this.pageSize);
      });
  }

  removeRecipe(recipe: Recipe, event: any) {
    event.stopPropagation();

    this.recipeService.update(recipe.id, {isDeleted: true}).subscribe(() => {
      const recipeIndex = this.recipes.indexOf(recipe);
      this.recipes.splice(recipeIndex, 1);
      let pageEvent = new PageEvent();
      pageEvent.pageIndex = this.topPaginator.pageIndex;
      pageEvent.length = this.recipes.length;
      pageEvent.pageSize = this.topPaginator.pageSize;
      this.changeList(pageEvent);

      const snackBar = this.snackBar.open('Pomyślnie usunięto przepis.', 'Cofnij', {
        duration: 5000
      });
      snackBar.onAction().subscribe(() => {
        this.recipeService.update(recipe.id, {isDeleted: false}).subscribe(() => {
          this.recipes.splice(recipeIndex, 0, recipe);
          pageEvent.pageIndex = this.topPaginator.pageIndex;
          pageEvent.length = this.recipes.length;
          pageEvent.pageSize = this.topPaginator.pageSize;
          this.changeList(pageEvent);
        });
      });
    });
  }

  showSearchDiv() {
    this.showSearch = true;
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
    setTimeout(() => {
      this.searchInput.nativeElement.focus();
    }, 200);
  }

  showAddRecipeDiv() {
    this.showAddDiv = true;
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
    setTimeout(() => {
      this.recipeUrl.nativeElement.focus();
    }, 200);
  }
}
