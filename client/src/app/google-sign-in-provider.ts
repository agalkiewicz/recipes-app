import {SignInProvider} from "./dto/sign-in-provider";
import {User} from "./dto/user";

declare let gapi: any;

export class GoogleSignInProvider implements SignInProvider {
  private clientId = '680092708222-dv2se5gsv573lmas6saf6j4m7niee35b.apps.googleusercontent.com';
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
    user.idtoken = authResponseObj.id_token;
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
