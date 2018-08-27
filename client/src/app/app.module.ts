import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card';
import {FlexLayoutModule} from '@angular/flex-layout';
import {
  MatButtonModule,
  MatChipsModule, MatFormFieldModule, MatIcon, MatIconModule, MatInputModule, MatPaginatorIntl, MatPaginatorModule,
  MatSnackBar, MatSnackBarModule,
  MatTableModule, MatToolbarModule
} from '@angular/material';

import {AppComponent} from './app.component';
import {RecipeService} from './recipe.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {RecipesComponent} from './recipes/recipes.component';
import {MessagesComponent} from './messages/messages.component';
import {MessageService} from './message.service';
import {RecipeComponent} from './recipe/recipe.component';
import {MatPaginatorIntlPL} from './mat-paginator-intl-pl';
import {AppRoutingModule} from './app-routing.module';
import {SignInComponent} from './sign-in/sign-in.component';
import {Interceptor} from "./_interceptor/interceptor";
import {Router} from "@angular/router";

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
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    AppRoutingModule,
    MatChipsModule,
    MatIconModule,
    MatSnackBarModule
  ],
  providers: [
    RecipeService,
    MessageService,
    {
      provide: MatPaginatorIntl,
      useClass: MatPaginatorIntlPL
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true,
      deps: [
        Router,
        MatSnackBar
      ]
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
