import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';

import { AppState } from 'src/app/core/appState.model';
import { AuthService } from 'src/app/core/auth.service';
import { Subscription, interval, Observable } from 'rxjs';

@Component({
  selector: 'raf-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, OnDestroy {

  constructor(private authService: AuthService, private router: Router) { }

  appState: AppState;

  state: string;

  subscription: Subscription;

  authenticated: boolean;

  ngOnInit() {
    this.subscription = interval(500).subscribe(() => {
      const redirectUrl = this.authService.redirectUrl;
      this.state = redirectUrl ? `&state=${redirectUrl.substr(1)}` : '';
    });
    this.authService.state().subscribe(appState => this.appState = appState);
    this.authService.isAuthenticated().subscribe(authenticated => this.authenticated = authenticated);
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  logout() {
    this.authService.logout();
  }
}
