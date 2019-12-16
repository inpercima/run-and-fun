import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from '../core/auth.service';
import { AppState } from '../core/appState.model';

@Component({
  selector: 'randf-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  public appState: AppState;

  ngOnInit() {
    this.authService.state().subscribe(appState => {
      this.appState = appState;
    });
  }

  isAuthenticated() {
    return this.authService.isAuthenticated();
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['login']);
  }

}
