import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {RecipeUrlDto} from './dto/recipe-url-dto';
import {Recipe} from './dto/recipe';
import {catchError} from "rxjs/operators";
import {of} from "rxjs/observable/of";

@Injectable()
export class RecipeService {

  private recipesUrl = 'http://localhost:8080/api/recipes';

  constructor(private http: HttpClient) {
  }

  add(url: RecipeUrlDto): Observable<Recipe> {
    return this.http.post<Recipe>(this.recipesUrl, url).pipe(
      catchError(error => {
        console.log('error', error);
        return of(error);
      })
    );
  }

  getAll(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.recipesUrl);
  }

  getOne(id: number): Observable<Recipe> {
    const url = `${this.recipesUrl}/${id}`;
    return this.http.get<Recipe>(url);
  }

  searchByTerms(terms: string[]) {
    console.log('service searchRecipesByTerms', terms);

    let query = '';
    terms.forEach(term => {
      query += 'terms=' + term + '&'
    });
    const url = `${this.recipesUrl}/search?${query}`;

    return this.http.get<Recipe[]>(url);
  }
}
