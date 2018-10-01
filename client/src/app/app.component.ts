import {AfterViewInit, Component, OnInit} from '@angular/core';
import {SignInService} from "./_service/sign-in.service";
import {User} from "./_model/user";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit, OnInit {
  title = 'Przepiśnik';
  user: User;
  isMenuVisible = false;

  constructor(private signInService: SignInService) {
  }

  ngOnInit() {
    this.signInService.authState.subscribe((user: User) => {
      this.user = user;
      if (user && user.idToken && localStorage.getItem('currentUser')) {
        let currentUser: User = JSON.parse(localStorage.getItem('currentUser'));
        currentUser.idToken = user.idToken;
        localStorage.setItem('currentUser', JSON.stringify(currentUser));
      }
    });
  }

  ngAfterViewInit() {

  }

  public onActivate(componentToLoad) {
    this.isMenuVisible = false;

    if (localStorage.getItem('currentUser') && JSON.parse(localStorage.getItem('currentUser')).idToken) {
      this.isMenuVisible = true;
    }
  }
}
