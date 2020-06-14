import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from 'src/environments/environment';
import { Activity } from './activity.model';

@Injectable({
  providedIn: 'root'
})
export class ActivitiesService {

  constructor(private http: HttpClient) { }

  index(): Observable<string> {
    return this.http.get<string>(environment.api + 'activities/index').pipe(map(response => response));
  }

  list(): Observable<any> {
    return this.http.get<string>(environment.api + 'activities').pipe(map(response => response));
  }

  last(): Observable<Activity> {
    return this.http.get<Activity>(environment.api + 'activities/last').pipe(map(response => response));
  }
}
