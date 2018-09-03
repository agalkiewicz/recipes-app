import {AfterViewInit, Component, OnInit} from '@angular/core';
import {GoogleSignIn} from "../globals";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {
  title = 'Aplikacja kulinarna';
  private clientId = '680092708222-dv2se5gsv573lmas6saf6j4m7niee35b.apps.googleusercontent.com';
  private scope = [
    'profile',
    'email'
  ].join(' ');

  ngAfterViewInit() {
    // GoogleSignIn.gapi.load('auth2', () => {
    //   GoogleSignIn.auth2 = GoogleSignIn.gapi.auth2.init({
    //     client_id: this.clientId,
    //     scope: this.scope
    //   });
    // });
  }
}
