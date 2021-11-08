import { DatePipe } from '@angular/common';
import { Injectable } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  readonly ALL_YEARS = 'All years';

  readonly MIN_DATE = 'minDate';
  readonly MAX_DATE = 'maxDate';
  readonly TYPE = 'type';

  datePipe = new DatePipe('en-US');

  constructor(private formBuilder: FormBuilder) { }

  prepareYears(): string[] {
    const years = [this.ALL_YEARS];
    for (let year = new Date().getFullYear(); year >= 2010; year--) {
      years.push(year.toString());
    }
    return years;
  }

  prepareParams(form: FormGroup): any {
    const params: { [k: string]: any } = {};
    Object.keys(form.controls).forEach(key => {
      const value = form.get(key)?.value;
      if (key && value) {
        // do not add year if all years
        if (key !== 'year' || (key === 'year' && value !== this.ALL_YEARS)) {
          params[key] = value;
        }
      }
    });

    if (params['year']) {
      if (params['minDate']) {
        params['minDate'] = `${params['year']}-${this.datePipe.transform(params['minDate'], 'MM-dd')}`;
      } else {
        params['minDate'] = `${params['year']}-01-01`;
      }
      if (params['maxDate']) {
        params['maxDate'] = `${params['year']}-${this.datePipe.transform(params['minDate'], 'MM-dd')}`;
      } else {
        params['maxDate'] = `${params['year']}-12-31`;
      }
    }

    if (params['allTypes']) {
      params['allTypes'] = [];
    }
    return params;
  }

  defaultOptions(size: number): FormGroup {
    return this.formBuilder.group({
      size: [size],
      minDate: [],
      maxDate: [],
      minDistance: [],
      maxDistance: [],
      year: [this.ALL_YEARS],
      sort: ['date,desc'],
      allTypes: [true],
    });
  }
}
