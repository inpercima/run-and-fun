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

  list(): Observable<Activity[]> {
    const activities: Activity[] = [];
    return this.http.get<any>(environment.api + 'activities').pipe(map(response => {
      response.searchHits.forEach((element: any) => {
        activities.push(element.content);
      });
      return activities;
    }));
  }

  last(): Observable<Activity> {
    return this.http.get<Activity>(environment.api + 'activities/last').pipe(map(response => response));
  }
}
