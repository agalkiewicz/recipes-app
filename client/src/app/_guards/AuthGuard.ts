import {Injectable} from '@angular/core';
import {Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';

@Injectable()
export class AuthGuard implements CanActivate {

  isSignedIn = false;

  constructor(private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot) {
    if (localStorage.getItem('currentUser') && JSON.parse(localStorage.getItem('currentUser')).idToken) {
      this.isSignedIn = true;
    }

    if (this.isSignedIn && route.url.toString() === 'sign-in') {
      this.router.navigate(['/recipes']);
    }

    if (!this.isSignedIn && route.url.toString() !== 'sign-in') {
      this.router.navigate(['/sign-in'], {queryParams: {returnUrl: state.url}});
    }

    if (!this.isSignedIn && route.url.toString() === 'sign-in') {
      return true;
    }

    return this.isSignedIn;
  }
}
