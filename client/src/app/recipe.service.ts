import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {RecipeUrlDto} from './dto/recipe-url-dto';
import {Recipe} from './dto/recipe';

@Injectable()
export class RecipeService {

  private recipesUrl = 'http://localhost:8080/recipes';

  constructor(private http: HttpClient) {
  }

  add(url: RecipeUrlDto): Observable<Recipe> {
    return this.http.post<Recipe>(this.recipesUrl, url);
  }

  getAll(): Observable<Recipe[]> {
    return this.http.get<Recipe[]>(this.recipesUrl);
  }

  getOne(id: number): Observable<Recipe> {
    const url = `${this.recipesUrl}/${id}`;
    return this.http.get<Recipe>(url);
  }

  searchByTerms(terms: string[]) {
    console.log('service searchByTerms');

    const url = `${this.recipesUrl}/search?terms=${JSON.stringify(terms)}`;

    return this.http.get<Recipe[]>(url);
  }
}
