import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';

@Injectable()
export class AuthGuard implements CanActivate {

  isLogged = false;

  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot) {
    if (localStorage.getItem('id_token')) {
      this.isLogged = true;
    }

    if (this.isLogged && route.url.toString() === 'sign-in') {
      this.router.navigate(['/recipes']);
    }

    if (!this.isLogged && route.url.toString() !== 'sign-in') {
      this.router.navigate(['/sign-in'], {queryParams: {returnUrl: state.url}});
    }

    if (!this.isLogged && route.url.toString() === 'sign-in') {
      return true;
    }

    return this.isLogged;
  }
}
