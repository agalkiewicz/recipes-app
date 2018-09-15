import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {RecipesComponent} from './recipes/recipes.component';
import {RecipeComponent} from './recipe/recipe.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {AuthGuard} from "./_guards/AuthGuard";

const routes: Routes = [
  {path: '', redirectTo: '/recipes', pathMatch: 'full'},
  {path: 'sign-in', component: SignInComponent, canActivate: [AuthGuard]},
  {path: 'recipes', component: RecipesComponent, canActivate: [AuthGuard]},
  {path: 'recipes/:id', component: RecipeComponent, canActivate: [AuthGuard]}
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      routes,
      {enableTracing: false} // debugging purposes only
    )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
