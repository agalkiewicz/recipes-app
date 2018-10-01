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
      this.signInService.sendIdToken(user.idToken).subscribe(() => {
        localStorage.setItem('currentUser', JSON.stringify(user));
        this.router.navigate(['/recipes']);
      });
    });
  }
}
