import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable()
export class ActivitiesService {

  constructor(private http: HttpClient) { }

  indexActivities(): Observable<number> {
    return this.http.get<number>('/indexActivities').pipe(response => {
      return response;
    });
  }

  list(params: Array<string>): Observable<any> {
    let url = '/listActivities';
    if (params.length === -1) {
      url += 'ByType';
    }
    return this.http.get<any>(url).pipe(response => {
      return response;
    });
  }

}
