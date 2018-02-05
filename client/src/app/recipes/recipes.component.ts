import { Component, OnInit } from '@angular/core';
import { Recipe } from '../dto/recipe';
import { RecipeUrlDto } from '../dto/recipe-url-dto';
import { RecipeService } from '../recipe.service';

@Component({
  selector: 'app-recipes',
  templateUrl: './recipes.component.html',
  styleUrls: ['./recipes.component.css']
})
export class RecipesComponent implements OnInit {
  recipes: Recipe[];

  constructor(private recipeService: RecipeService) {
  }

  add(url: string): void {
    console.log('add');
    console.log(this.recipes.length);

    url = url.trim();
    if (!url) {
      return;
    }
    this.recipeService.add({url: url} as RecipeUrlDto)
      .subscribe(recipe => {
        this.recipes.push(recipe);
        console.log(this.recipes.length);
        console.log(recipe);
      });

  }

  getAll(): void {
    this.recipeService.getAll()
      .subscribe(recipes => {
        this.recipes = recipes;
        console.log('getAll');
        console.log(recipes.length);
        console.log(this.recipes.length);
      });
  }

  ngOnInit() {
    this.getAll();
  }
}
