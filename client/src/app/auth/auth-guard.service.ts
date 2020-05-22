import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';

import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) { }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | boolean {
    console.log(state.url);
    return this.authService.verifyAuthentication().pipe(
      take(1),
      map(response => {
      if (!response) {
        // store the attempted URL for redirecting
        this.authService.redirectUrl = state.url;
        // navigate to the login page
        this.router.navigate(['login']);
      }
      return response;
    }));
  }
}
