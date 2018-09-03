import {Injectable} from '@angular/core';
import {User} from "../dto/user";
import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {Observable} from "rxjs/Observable";
import {SignInProvider} from "../dto/sign-in-provider";
import {GoogleSignInProvider} from "../google-sign-in-provider";

@Injectable()
export class SignInService {
  private provider: SignInProvider;
  private user: User = null;
  private authenticationState: BehaviorSubject<User> = new BehaviorSubject(null);

  get authState(): Observable<User> {
    return this.authenticationState.asObservable();
  }

  constructor() {
    this.provider = new GoogleSignInProvider();
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
    return new Promise((resolve, reject) => {
      if (this.user) {
        this.provider.signOut().then(() => {
          this.user = null;
          this.authenticationState.next(null);
          resolve();
        }).catch((err) => {
          this.authenticationState.next(null);
        });
      }
    });
  }

}