import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {SignInService} from "../_service/sign-in.service";
import {User} from "../_model/user";
import {AppComponent} from "../app.component";
import {Router} from "@angular/router";
import {AuthGuard} from "../_guard/AuthGuard";

declare const gapi: any;

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.scss']
})
export class SignInComponent {

  constructor(private signInService: SignInService,
              private router: Router) {
  }

  signIn() {
    this.signInService.signIn().then((user: User) => {
      console.log("sign in data : ", user);
      this.signInService.sendIdToken(user.idToken).subscribe(() => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.router.navigate(['/recipes']);
        console.log('send id token subscribe');
      });
    });
  }

  ngOnInit() {

  }

  // ngAfterViewInit() {
  //   this.signInService.authState.subscribe((user: User) => {
  //     this.user = user;
  //     console.log('user changed');
  //     console.log(user);
  //   });
  // }


  //
  // ngOnInit() {
  // }
  //
  // attachGoogleSignIn(element) {
  //   let that = this;
  //   this.auth2.attachClickHandler(element, {},
  //     function (user) {
  //       let userProfile = user.getBasicProfile();
  //       that.idToken = user.getAuthResponse().id_token;
  //       console.log(user.getAuthResponse());
  //       console.log(`Access token: ${user.getAuthResponse().access_token}`);
  //       console.log(`ID token: ${that.idToken}`);
  //       console.log(`Name: ${userProfile.getName()}`);
  //       console.log(`Image URL: ${userProfile.getImageUrl()}`);
  //       console.log(`Email: ${userProfile.getEmail()}`);
  //     }, function (error) {
  //       console.log(JSON.stringify(error, undefined, 2));
  //     });
  // }
  //
  // ngAfterViewInit() {
  //   gapi.load('auth2', () => {
  //     this.auth2 = gapi.auth2.init({
  //       client_id: this.clientId,
  //       scope: this.scope
  //     });
  //     this.attachGoogleSignIn(this.googleSignIn.nativeElement);
  //     console.log(gapi.auth2.getAuthInstance().currentUser.get());
  //   });
  // };
  //
  // googleSignOut() {
  //   let auth2 = gapi.auth2.getAuthInstance();
  //   console.log(auth2.isSignedIn.get());
  //   auth2.signOut().then(function () {
  //     console.log('User signed out.');
  //     console.log(auth2.isSignedIn.get());
  //   });
  // }
  //
  // silentSignIn() {
  //   gapi.auth2.getAuthInstance().currentUser.get().reloadAuthResponse()
  //     .then(data => console.log(data))
  //     .catch(err => console.log(err));
  // }
}
