import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

import { AuthService } from './auth.service';
import { ConfigService } from './config.service';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private configService: ConfigService, private router: Router) { }

  canActivate(): Observable<boolean> {
    // should a login will be used the state will be checked otherwise it will return always logged in
    return this.configService.isActivateLogin() ? this.checkLogin() : of(true);
  }

  checkLogin(): Observable<boolean> {
    return this.authService.isAuthenticated().pipe(map(response => {
      if (!response) {
        // navigate to the login page
        this.router.navigate(['login']);
      }
      return response;
    }));
  }

}
