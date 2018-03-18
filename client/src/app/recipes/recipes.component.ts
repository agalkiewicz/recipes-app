import {Component, OnInit, ViewChild} from '@angular/core';
import {Recipe} from '../dto/recipe';
import {RecipeUrlDto} from '../dto/recipe-url-dto';
import {RecipeService} from '../recipe.service';
import {MatPaginator, PageEvent} from '@angular/material';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.scss']
})
export class RecipesComponent implements OnInit {
  recipes: Recipe[];
  pagedRecipes: Recipe[];

  recipesLength = 0;
  pageSize = 12;

  @ViewChild('bottomPaginator') bottomPaginator: MatPaginator;
  @ViewChild('topPaginator') topPaginator: MatPaginator;

  constructor(private recipeService: RecipeService) {
  }

  ngOnInit() {
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
      });

  }

  private getAll(): void {
    this.recipeService.getAll()
      .subscribe(recipes => {
        this.recipes = recipes;
        this.recipesLength = this.recipes.length;
        console.log(this.recipes);
        this.pagedRecipes = this.recipes.slice(0, this.pageSize);
      });
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
}
