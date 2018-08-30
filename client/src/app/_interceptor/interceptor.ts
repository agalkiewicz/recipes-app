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

@Injectable()
export class Interceptor implements HttpInterceptor {

  constructor(private router: Router,
              private snackBar: MatSnackBar) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!request.headers.has('Content-Type')) {
      request = request.clone({
        headers: request.headers.set('Content-Type', 'application/json')
      });
    }

    if (!request.headers.has('Accept')) {
      request = request.clone({
        headers: request.headers.set('Accept', 'application/json')
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
    if (err.status === 409) {
      const errorSnackbar = this.snackBar.open('Dodałeś już przepis z tej strony.', 'Zamknij', {
        duration: 7000
      });
    } else if (err.status === 500) {
      const errorSnackbar = this.snackBar.open('Wystąpił błąd pobierania danych', 'Odśwież');
      errorSnackbar.onAction().subscribe(() => {
        window.location.reload();
      });
    } else if (err.status === 501) {
      const errorSnackbar = this.snackBar.open('Nie można dodać przepisu z tej strony. Strona nie implementuje znaczników Schema.org.', 'Zamknij', {
        duration: 7000
      });
    }
    throw err;
  }
}
