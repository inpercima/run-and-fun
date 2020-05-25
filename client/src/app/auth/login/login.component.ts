import { Component, OnInit, OnDestroy } from '@angular/core';

import { Subscription, interval, Observable } from 'rxjs';

import { AppState } from '../app-state.model';
import { AuthService } from '../auth.service';

@Component({
  selector: 'raf-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit, OnDestroy {

  constructor(private authService: AuthService) { }

  appState: AppState;

  state: string;

  subscription: Subscription;

  isAuthenticated$: Observable<boolean>;

  ngOnInit() {
    this.subscription = interval(500).subscribe(() => {
      const redirectUrl = this.authService.redirectUrl;
      this.state = redirectUrl ? `&state=${redirectUrl.substr(1)}` : '';
    });
    this.authService.state().subscribe(appState => this.appState = appState);
    this.isAuthenticated$ = this.authService.isAuthenticated;
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  logout() {
    this.authService.logout();
  }
}
