import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

import { StorageService } from '../core/storage.service';
import { AppState } from './app-state.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  // used for app.component
  private authenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private storageService: StorageService) { }

  get isAuthenticated() {
    return this.authenticated.asObservable();
  }

  state(): Observable<AppState> {
    return this.http.get<AppState>(environment.api + 'state').pipe(map(response => response));
  }

  /**
   *
   * @param code verify code from runkeeper
   * @param error a error message if login on runkeeper fails
   */
  verify(code: string, error: string) {
    return this.http.get<any>(environment.api + 'verify', {
      params: { code, error },
      // to get x-headers
      observe: 'response',
    }).pipe(map(response => {
      const accessToken = response.headers.get('x-accessToken');
      this.storageService.save(accessToken);
    }));
  }

  /**
   * Logs out the user on the server and removes the accessToken in localStorage to force log out the user on client.
   */
  logout(): void {
    this.http.get(environment.api + 'logout').subscribe(() => this.storageService.remove());
  }

  /**
   * Validates the accessToken on the server in used session.
   * This method is used by the AuthGuard and app.component on initialization to set the BehaviorSubject 'authenticated'.
   */
  verifyAuthentication(): Observable<boolean> {
    return this.state().pipe(map(response => {
      const result = response.accessToken !== null && response.accessToken !== '';
      this.authenticated.next(result);
      return result;
    }));
  }
}
