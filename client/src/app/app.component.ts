import {AfterViewInit, Component, OnInit} from '@angular/core';
import {SignInService} from "./service/sign-in.service";
import {User} from "./dto/user";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements AfterViewInit {
  title = 'Aplikacja kulinarna';
  user: User;

  constructor(private signInService: SignInService) {}

  ngAfterViewInit() {
    this.signInService.authState.subscribe((user: User) => {
      this.user = user;
      if (user) {
        localStorage.setItem('id_token', user.idtoken);
      }
      console.log('user changed');
      console.log(user);
    });
  }
}
