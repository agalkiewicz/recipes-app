import {Injectable} from '@angular/core';
import {User} from "../_model/user";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/Observable";
import {SignInProvider} from "../_model/sign-in-provider";
import {GoogleSignInProvider} from "./google-sign-in-provider";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable()
export class SignInService {
  private user: User = null;
  private authenticationState: BehaviorSubject<User> = new BehaviorSubject(null);

  get authState(): Observable<User> {
    return this.authenticationState.asObservable();
  }

  constructor(private http: HttpClient,
              private router: Router,
              private provider: GoogleSignInProvider) {
    this.provider.initialize().then((user: User) => {
      this.user = user;
      this.authenticationState.next(user);
    }).catch((err) => {
      // this._authState.next(null);
    });
  }

  signIn(): Promise<User> {
    return new Promise((resolve, reject) => {
      this.provider.signIn().then((user: User) => {
        resolve(user);
        this.user = user;
        this.authenticationState.next(user);
      });
    });
  }

  signOut(): Promise<any> {
    console.log('sign out _service');
    return new Promise((resolve, reject) => {
      if (this.user) {
        this.provider.signOut().then(() => {
          console.log('sign out provider');
          localStorage.removeItem('currentUser');
          this.user = null;
          this.authenticationState.next(null);
          resolve();
        }).catch((err) => {
          this.authenticationState.next(null);
        });
      }
    });
  }

  sendIdToken(idToken: string) {
    console.log('send id token');
    let signInUrl = 'http://localhost:8080/sign-in';
    return this.http.post(signInUrl, {
      "idToken": idToken
    });
  }
}
