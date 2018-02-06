import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { catchError, tap } from 'rxjs/operators';
import { RecipeUrlDto } from './dto/recipe-url-dto';
import { Recipe } from './dto/recipe';
import { MessageService } from './message.service';
import { of } from 'rxjs/observable/of';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class RecipeService {

  private recipesUrl = 'http://localhost:8080/recipes';

  constructor(private http: HttpClient,
              private messageService: MessageService) {
  }

  add(url: RecipeUrlDto): Observable<Recipe> {
    return this.http.post<Recipe>(this.recipesUrl, url, httpOptions)
      .pipe(
        tap((recipe: Recipe) => {
          this.log(`Dodano przepis, id=${recipe.id}.`);
          console.log(recipe);
        }),
        catchError(this.handleError<Recipe>('add recipe'))
      );
  }

  getAll(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.recipesUrl)
      .pipe(
        catchError(this.handleError('getAll', []))
      );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      if (error.status === 501) {
        this.log(`Nie można przeanalizować treści podanej strony. Brak znaczników Schema.org`);
      } else if (error.status === 409) {
        this.log(`Przepis z podanej strony jest już zapisany.`);
      } else {
        this.log(`${operation} failed: ${error.message}`);
      }

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a RecipeService message with the MessageService */
  private log(message: string) {
    this.messageService.add('RecipeService: ' + message);
  }
}
