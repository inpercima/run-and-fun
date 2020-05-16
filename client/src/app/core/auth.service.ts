import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { AppState } from './appState.model';
import { StorageService } from './storage.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  // store the URL so we can redirect after logging in
  redirectUrl: string;

  constructor(private http: HttpClient, private storageService: StorageService) { }

  state(): Observable<AppState> {
    return this.http.get<AppState>(environment.api + 'state').pipe(map(response => response));
  }

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

  logout(): void {
    this.http.get(environment.api + 'logout').subscribe();
    this.storageService.remove();
  }

  isAuthenticated(): Observable<boolean> {
    return this.state().pipe(map(response => response.accessToken !== null && response.accessToken !== ''));
  }
}
