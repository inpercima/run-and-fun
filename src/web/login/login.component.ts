import { Component, OnInit } from '@angular/core';
import { AppState } from '../core/appState';
import { AuthService } from '../core/auth.service';


@Component({
  selector: 'raf-login',
  templateUrl: './login.component.html',
})
export class LoginComponent implements OnInit {

  private appState: AppState = null;

  private isAuthenticated: boolean;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    this.authService.appState().subscribe(response => this.appState = response);
    this.authService.isAuthenticated().subscribe(response => this.isAuthenticated = response);
  }

}
