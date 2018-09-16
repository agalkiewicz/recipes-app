import {AfterViewInit, Component, OnInit} from '@angular/core';
import {SignInService} from "../service/sign-in.service";
import {User} from "../dto/user";
import {Router} from "@angular/router";
import {AuthGuard} from "../_guards/AuthGuard";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  user = new User();

  constructor(private signInService: SignInService,
              private router: Router,
              private guard: AuthGuard,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
  }

  signOut() {
    console.log('sign out');
    this.signInService.signOut().then(
      () => {
        this.guard.isSignedIn = false;
        this.router.navigate(['/sign-in']);
        this.snackBar.open('Zostałeś wylogowany.', 'Zamknij', {
          duration: 3000
        });
      }
    );
  }
}
