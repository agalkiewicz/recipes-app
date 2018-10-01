import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {RecipeUrlDto} from '../dto/recipe-url-dto';
import {Recipe} from '../_model/recipe';
import {catchError} from "rxjs/operators";
import {of} from "rxjs/observable/of";
import {environment} from "../../environments/environment";

@Injectable()
export class RecipeService {

  private recipesUrl = `${environment.apiURL}/api/recipes`;

  constructor(private http: HttpClient) {
  }

  add(url: RecipeUrlDto): Observable<Recipe> {
    return this.http.post<Recipe>(this.recipesUrl, url).pipe(
      catchError(error => {
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
    let query = '';
    terms.forEach(term => {
      query += 'terms=' + term + '&'
    });
    const url = `${this.recipesUrl}/search?${query}`;

    return this.http.get<Recipe[]>(url);
  }

  update(id: number, propertyToChange) {
    const url = `${this.recipesUrl}/${id}`;
    return this.http.put(url, propertyToChange);
  }
}
