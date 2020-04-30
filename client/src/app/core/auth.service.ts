import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';

import { JwtHelperService } from '@auth0/angular-jwt';
import { map } from 'rxjs/operators';

import { AppState } from './appState.model';
import { RequestService } from './request.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // store the URL so we can redirect after logging in
  public redirectUrl: string;

  constructor(private http: HttpClient, private jwtHelper: JwtHelperService, private requestService: RequestService) { }

  public state() {
    return this.http.get<AppState>(this.requestService.url('state')).pipe(map(response => {
      if (response !== null) {
        return response;
      }
    }));
  }

  /**
   * This is a very simple authentication you should change for production use!
   *
   * @param formGroup loginForm
   */
  public login(formGroup: FormGroup) {
    return this.http.post<any>(this.requestService.url('auth'), formGroup.value).pipe(map(response => {
      if (response !== null) {
        // set the token property for validate token in the app
        localStorage.setItem('access_token', response.token);
      }
    }));
  }

  public logout(): void {
    localStorage.removeItem('access_token');
  }

  public isAuthenticated(): boolean {
    try {
      return !this.jwtHelper.isTokenExpired();
    } catch (e) {
      return false;
    }
  }

}
