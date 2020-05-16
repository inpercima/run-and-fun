import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  save(accessToken: string) {
    localStorage.setItem('accessToken', accessToken);
  }

  read(): string {
    return localStorage.getItem('accessToken');
  }

  remove(): void {
    localStorage.removeItem('accessToken');
  }
}
