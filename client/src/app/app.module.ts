import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card';
import {FlexLayoutModule} from '@angular/flex-layout';
import {
  MatFormFieldModule, MatPaginatorIntl, MatPaginatorModule, MatTableModule,
  MatToolbarModule, MatButtonModule, MatChipsModule, MatIconModule, MatInputModule,
  MatSnackBar, MatSnackBarModule, MatMenuModule
} from '@angular/material';

import {AppComponent} from './app.component';
import {RecipeService} from './_service/recipe.service';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {RecipesComponent} from './recipes/recipes.component';
import {MessagesComponent} from './messages/messages.component';
import {MessageService} from './_service/message.service';
import {RecipeComponent} from './recipe/recipe.component';
import {SignInService} from "./_service/sign-in.service";
import {MatPaginatorIntlPL} from './_helper/mat-paginator-intl-pl';
import {AppRoutingModule} from './_module/app-routing.module';
import {SignInComponent} from './sign-in/sign-in.component';
import {Interceptor} from "./_interceptor/interceptor";
import {Router} from "@angular/router";
import {AuthGuard} from "./_guard/AuthGuard";
import { NavbarComponent } from './navbar/navbar.component';
import {GoogleSignInProvider} from "./_service/google-sign-in-provider";

@NgModule({
  declarations: [
    AppComponent,
    RecipesComponent,
    MessagesComponent,
    RecipeComponent,
    SignInComponent,
    NavbarComponent
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
    MatFormFieldModule,
    MatInputModule,
    AppRoutingModule,
    MatChipsModule,
    MatIconModule,
    MatSnackBarModule,
    MatButtonModule,
    MatMenuModule
  ],
  providers: [
    RecipeService,
    MessageService,
    {
      provide: MatPaginatorIntl,
      useClass: MatPaginatorIntlPL
    },
    SignInService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true,
      deps: [
        Router,
        MatSnackBar
      ]
    },
    AuthGuard,
    GoogleSignInProvider
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
