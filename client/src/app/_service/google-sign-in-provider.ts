import {SignInProvider} from "../_model/sign-in-provider";
import {User} from "../_model/user";
import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";

declare let gapi: any;

@Injectable()
export class GoogleSignInProvider implements SignInProvider {
  private clientId = environment.clientId;
  private scope = [
    'profile',
    'email'
  ].join(' ');
  private auth2: any;

  initialize(): Promise<User> {
    return new Promise((resolve, reject) => {
      gapi.load('auth2', () => {
        this.auth2 = gapi.auth2.init({
          client_id: this.clientId,
          scope: this.scope
        });

        this.auth2.then(() => {
          if (this.auth2.isSignedIn.get()) {
            resolve(this.drawUser());
          }
        });
      });
    });
  }

  private drawUser(): User {
    let user: User = new User();
    let profile = this.auth2.currentUser.get().getBasicProfile();
    let authResponseObj = this.auth2.currentUser.get().getAuthResponse(true);
    console.log('authResponseObj', authResponseObj);
    user.name = profile.getName();
    user.email = profile.getEmail();
    user.imageUrl = profile.getImageUrl();
    user.idToken = authResponseObj.id_token;
    return user;
  }


  signIn(): Promise<User> {
    return new Promise((resolve, reject) => {
      let promise = this.auth2.signIn();
      promise.then(() => {
        resolve(this.drawUser());
      });
    });
  }

  signOut(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.auth2.signOut().then((err: any) => {
        if (err) {
          reject(err);
        } else {
          resolve();
        }
      });
    });
  }
}
