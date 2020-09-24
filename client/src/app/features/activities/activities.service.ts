import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormGroup } from '@angular/forms';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../environments/environment';
import { Activity } from './activity.model';

@Injectable({
  providedIn: 'root'
})
export class ActivitiesService {

  count: number;

  constructor(private http: HttpClient) { }

  index(): Observable<string> {
    return this.http.get<string>(environment.api + 'activities/index').pipe(map(response => response));
  }

  totalCount(): Observable<number> {
    return this.http.get<number>(environment.api + 'activities/count').pipe(map(response => response));
  }

  last(): Observable<Activity> {
    return this.http.get<Activity>(environment.api + 'activities/last').pipe(map(response => response));
  }

  listAndEnrich(params: any): Observable<Activity[]> {
    return this.list(params).pipe(map(response => {
      response.forEach((activity: Activity) => {
        activity.formattedDuration = this.formatTime(activity.duration);
        activity.timePerKm = this.formatTime(this.calcTimePerKm(activity.duration, activity.distance));
        activity.timePer5Km = this.formatTime(this.calcTimePerKm(5 * activity.duration, activity.distance));
        activity.timePer10Km = this.formatTime(this.calcTimePerKm(10 * activity.duration, activity.distance));
      });
      return response;
    }));
  }

  list(params: any): Observable<Activity[]> {
    const activities: Activity[] = [];
    return this.http.get<any>(environment.api + 'activities', { params }).pipe(map(response => {
      this.count = response.totalHits;
      response.searchHits.forEach((element: any) => {
        activities.push(element.content);
      });
      return activities;
    }));
  }

  /**
   * Convert 71 to '00:01:11'
   */
  private formatTime(seconds: number): string {
    return new Date(seconds * 1000).toISOString().substr(11, 8);
  }

  private calcTimePerKm(time: number, distance: number): number {
    return distance !== 0 ? time / distance : 0;
  }
}
