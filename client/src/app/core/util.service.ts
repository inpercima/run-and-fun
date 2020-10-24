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
    const params = {};
    const controls = form.controls;
    for (const control in controls) {
      if (controls[control].value !== null) {
        params[control] = controls[control].value;
      }
    }

    const year = form.value.year;
    if (form.value.minDate) {
      params[this.MIN_DATE] = `${year}-${this.datePipe.transform(params[this.MIN_DATE], 'MM-dd')}`;
    } else {
      params[this.MIN_DATE] = `${year}-01-01`;
    }
    if (form.value.maxDate) {
      params[this.MAX_DATE] = `${year}-${this.datePipe.transform(params[this.MAX_DATE], 'MM-dd')}`;
    } else {
      params[this.MAX_DATE] = `${year}-12-31`;
    }

    if (form.value.allTypes) {
      params[this.TYPE] = [];
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
