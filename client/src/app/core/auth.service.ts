import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup } from '@angular/forms';

import { JwtHelperService } from '@auth0/angular-jwt';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // store the URL so we can redirect after logging in
  public redirectUrl: string;

  constructor(private http: HttpClient, private jwtHelper: JwtHelperService) { }

  /**
   * This is a very simple authentication you should change for production use!
   *
   * @param formGroup loginForm
   */
  public login(formGroup: FormGroup) {
    return this.http.post<any>(environment.api + 'auth', formGroup.value).pipe(map(response => {
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
