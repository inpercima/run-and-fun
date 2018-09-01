import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { Friend } from './friend';

@Injectable()
export class FriendsService {

  constructor(private http: HttpClient) { }

  public list(): Observable<Array<Friend>> {
    return this.http.get<Array<Friend>>('/listFriends').pipe(response => {
      return response;
    });
  }

}
