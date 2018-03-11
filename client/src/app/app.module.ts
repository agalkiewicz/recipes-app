import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from '@angular/material/card';
import {FlexLayoutModule} from '@angular/flex-layout';
import {MatPaginatorIntl, MatPaginatorModule, MatTableModule} from '@angular/material';

import {AppComponent} from './app.component';
import {RecipeService} from './recipe.service';
import {HttpClientModule} from '@angular/common/http';
import {RecipesComponent} from './recipes/recipes.component';
import {MessagesComponent} from './messages/messages.component';
import {MessageService} from './message.service';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material';
import {RecipeComponent} from './recipe/recipe.component';
import {MatPaginatorIntlPL} from './mat-paginator-intl-pl'

@NgModule({
  declarations: [
    AppComponent,
    RecipesComponent,
    MessagesComponent,
    RecipeComponent
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
    MatInputModule
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
