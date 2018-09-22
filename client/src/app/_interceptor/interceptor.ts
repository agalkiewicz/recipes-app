import {Injectable, Injector} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material';
import {Observable} from "rxjs/Observable";
import {catchError} from "rxjs/operators";
import {of} from "rxjs/observable/of";
import {SignInService} from "../service/sign-in.service";
import {User} from "../dto/user";
import {current} from "codelyzer/util/syntaxKind";

@Injectable()
export class Interceptor implements HttpInterceptor {
  private token: string;

  constructor(private router: Router,
              private snackBar: MatSnackBar) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (localStorage.getItem('currentUser')) {
      const currentUser: User = JSON.parse(localStorage.getItem('currentUser'));
      if (currentUser.idToken) {
        this.token = currentUser.idToken;
      } else {
        this.token = null;
      }
    } else {
      this.token = null;
    }

    if (!request.headers.has('Accept')) {
      request = request.clone({
        headers: request.headers.set('Accept', 'application/json')
      });
    }

    if (!request.headers.has('Authorization') && this.token) {
      request = request.clone({
        headers: request.headers.set('Authorization', `Bearer ${this.token}`)
      });
    }

    return next.handle(request).pipe(
      catchError((error, caught) => {
        this.handleError(error);
        return of(error);
      }) as any
    );
  }

  private handleError(err: HttpErrorResponse): Observable<any> {
    if (err.status === 401) {
      this.snackBar.open('Błąd uwierzytelnienia.', 'Zamknij', {
        duration: 7000
      });
    } else if (err.status === 403) {
      this.snackBar.open('Brak dostępu.', 'Zamknij', {
        duration: 7000
      });
    } else if (err.status === 409) {
      this.snackBar.open('Dodałeś już przepis z tej strony.', 'Zamknij', {
        duration: 7000
      });
    } else if (err.status === 500) {
      const errorSnackbar = this.snackBar.open('Wystąpił błąd pobierania danych', 'Odśwież');
      errorSnackbar.onAction().subscribe(() => {
        window.location.reload();
      });
    } else if (err.status === 501) {
      this.snackBar.open('Nie można dodać przepisu z tej strony. Strona nie implementuje znaczników Schema.org.', 'Zamknij', {
        duration: 7000
      });
    }
    throw err;
  }
}
