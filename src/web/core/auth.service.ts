import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { AppState } from './appState';

@Injectable()
export class AuthService {

  // store the URL so we can redirect after logging in
  public redirectUrl: string;

  constructor(private http: HttpClient) { }

  public appState(): Observable<AppState> {
    return this.http.get<AppState>('/state').pipe(response => {
      return response;
    });
  }

  public isAuthenticated(): Observable<boolean> {
    return this.appState().pipe(map(response => {
      return response != null && response.accessToken !== null;
    }));
  }

}
