import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card';
import {FlexLayoutModule} from '@angular/flex-layout';
import {
  MatFormFieldModule, MatPaginatorIntl, MatPaginatorModule, MatTableModule,
  MatToolbarModule
} from '@angular/material';

import {AppComponent} from './app.component';
import {RecipeService} from './recipe.service';
import {HttpClientModule} from '@angular/common/http';
import {RecipesComponent} from './recipes/recipes.component';
import {MessagesComponent} from './messages/messages.component';
import {MessageService} from './message.service';
import {RecipeComponent} from './recipe/recipe.component';
import {AppRoutingModule} from './app-routing.module';
import {SignInComponent} from './sign-in/sign-in.component';
import {MatPaginatorIntlPL} from './mat-paginator-intl-pl';
import {SignInService} from "./service/sign-in.service";

@NgModule({
  declarations: [
    AppComponent,
    RecipesComponent,
    MessagesComponent,
    RecipeComponent,
    SignInComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MatCardModule,
    FlexLayoutModule,
    MatPaginatorModule,
    MatTableModule,
    AppRoutingModule,
    MatToolbarModule,
    MatFormFieldModule
  ],
  providers: [
    RecipeService,
    MessageService,
    {
      provide: MatPaginatorIntl,
      useClass: MatPaginatorIntlPL
    },
    SignInService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
