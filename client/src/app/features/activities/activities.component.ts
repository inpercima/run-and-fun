import { DatePipe } from '@angular/common';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { merge, of } from 'rxjs';
import { startWith, switchMap, map, catchError } from 'rxjs/operators';

import { ActivitiesService } from './activities.service';
import { Activity } from './activity.model';
import { DatepickerHeaderComponent } from '../../shared/datepicker-header/datepicker-header.component';

@Component({
  selector: 'raf-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.css']
})
export class ActivitiesComponent implements AfterViewInit, OnInit {

  displayedColumns: string[] = ['functions', 'date', 'type', 'distance', 'duration', 'timePerKm', 'timePer5Km', 'timePer10Km'];
  dataSource = new MatTableDataSource();

  pageSizeOptions: number[] = [5, 10, 25, 50];
  length: number;
  isLoading = true;

  searchForm: FormGroup;

  types: string[] = ['Running', 'Hiking', 'Cycling', 'Walking'];

  years: string[] = [];

  datePipe = new DatePipe('en-US');

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  datepickerHeader;

  constructor(private formBuilder: FormBuilder, private activitiesService: ActivitiesService) {
    this.datepickerHeader = DatepickerHeaderComponent;
  }

  ngOnInit(): void {
    this.years.push('All years');
    const currentYear = new Date().getFullYear();
    for (let year = currentYear; year >= 2010; year--) {
      this.years.push(year.toString());
    }

    this.searchForm = this.formBuilder.group({
      page: [0],
      size: [5],
      sort: ['date,desc'],
      type: [this.types],
      allTypes: [true],
      minDate: [],
      maxDate: [],
      minDistance: [],
      maxDistance: [],
      year: [this.years[0]],
    });
  }

  ngAfterViewInit(): void {
    // if the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.list();
  }

  list(): void {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoading = true;
          this.searchForm.value.page = this.paginator.pageIndex;
          this.searchForm.value.size = this.paginator.pageSize;
          if (this.sort.active && this.sort.direction) {
            this.searchForm.value.sort = `${this.sort.active},${this.sort.direction}`;
          }
          return this.activitiesService.listAndEnrich(this.prepareParams());
        }),
        map(response => {
          // flag to show that loading has finished
          this.isLoading = false;
          this.length = this.activitiesService.count;
          return response;
        }),
        catchError(() => {
          this.isLoading = false;
          return of([]);
        })
      ).subscribe(response => this.dataSource = new MatTableDataSource(response));
  }

  prepareParams(): any {
    const params = {};
    const controls = this.searchForm.controls;
    for (const control in controls) {
      if (controls[control].value !== null) {
        params[control] = controls[control].value;
      }
    }

    if (this.searchForm.value.minDate && this.searchForm.value.maxDate) {
      params['minDate'] = this.datePipe.transform(params['minDate'], 'yyyy-MM-dd');
      params['maxDate'] = this.datePipe.transform(params['maxDate'], 'yyyy-MM-dd');
    }

    if (this.searchForm.value.allTypes) {
      const key = 'type';
      params[key] = [];
    }

    return params;
  }

  remove(activity: Activity): void {
    const index = this.dataSource.data.indexOf(activity);
    this.dataSource.data.splice(index, 1);
    this.dataSource._updateChangeSubscription();
  }
}
