import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor() { }

  prepareYears(): string[] {
    const years = [ 'All years' ];
    for (let year = new Date().getFullYear(); year >= 2010; year--) {
      years.push(year.toString());
    }
    return years;
  }
}
