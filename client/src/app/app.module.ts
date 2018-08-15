import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { FlexLayoutModule } from '@angular/flex-layout';
import {
  MatChipsModule, MatIcon, MatIconModule, MatPaginatorIntl, MatPaginatorModule,
  MatTableModule
} from '@angular/material';

import { AppComponent } from './app.component';
import { RecipeService } from './recipe.service';
import { HttpClientModule } from '@angular/common/http';
import { RecipesComponent } from './recipes/recipes.component';
import { MessagesComponent } from './messages/messages.component';
import { MessageService } from './message.service';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material';
import { RecipeComponent } from './recipe/recipe.component';
import { MatPaginatorIntlPL } from './mat-paginator-intl-pl';
import { AppRoutingModule } from './app-routing.module';
import { SignInComponent } from './sign-in/sign-in.component';
import { FormsModule } from '@angular/forms';

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
    MatIconModule
  ],
  providers: [
    RecipeService,
    MessageService,
    {
      provide: MatPaginatorIntl,
      useClass: MatPaginatorIntlPL
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
