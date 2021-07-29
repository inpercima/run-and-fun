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
    const year = form.value.year;
    if (form.value.minDate) {
      form.get(this.MIN_DATE)?.setValue(`${year}-${this.datePipe.transform(form.value.minDate, 'MM-dd')}`);
    } else {
      form.get(this.MIN_DATE)?.setValue(`${year}-01-01`);
    }
    if (form.value.maxDate) {
      form.get(this.MAX_DATE)?.setValue(`${year}-${this.datePipe.transform(form.value.minDate, 'MM-dd')}`);
    } else {
      form.get(this.MAX_DATE)?.setValue(`${year}-12-31`);
    }

    if (form.value.allTypes) {
      form.get(this.TYPE)?.setValue([]);
    }
    return form;
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
