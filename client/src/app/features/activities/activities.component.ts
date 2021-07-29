import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { merge, of } from 'rxjs';
import { startWith, switchMap, map, catchError } from 'rxjs/operators';

import { UtilService } from '../../core/util.service';
import { DatepickerHeaderComponent } from '../../shared/datepicker-header/datepicker-header.component';
import { ActivitiesService } from './activities.service';
import { Activity } from './activity.model';

@Component({
  selector: 'raf-activities',
  templateUrl: './activities.component.html',
})
export class ActivitiesComponent implements AfterViewInit, OnInit {

  displayedColumns: string[] = ['functions', 'date', 'type', 'distance', 'duration', 'timePerKm', 'timePer5Km', 'timePer10Km'];
  dataSource = new MatTableDataSource<Activity>();
  pageSizeOptions: number[] = [5, 10, 25, 50];
  length!: number;
  isLoading = true;

  filterForm = this.utilService.defaultOptions(5);

  types: string[] = ['Running', 'Hiking', 'Cycling', 'Walking'];

  years: string[] = [];

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  datepickerHeader: typeof DatepickerHeaderComponent;

  constructor(private formBuilder: FormBuilder, private activitiesService: ActivitiesService, private utilService: UtilService) {
    this.datepickerHeader = DatepickerHeaderComponent;
  }

  ngOnInit(): void {
    this.years = this.utilService.prepareYears();
    this.filterForm = this.formBuilder.group({
      ...this.filterForm.controls,
      page: [0],
      type: [this.types],
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
          this.filterForm.get('page')?.setValue(this.paginator.pageIndex);
          this.filterForm.get('size')?.setValue(this.paginator.pageSize);
          if (this.sort.active && this.sort.direction) {
            this.filterForm.get('sort')?.setValue(`${this.sort.active},${this.sort.direction}`);
          }
          return this.activitiesService.listAndEnrich(this.utilService.prepareParams(this.filterForm));
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

  remove(activity: Activity): void {
    const index = this.dataSource.data.indexOf(activity);
    this.dataSource.data.splice(index, 1);
    this.dataSource._updateChangeSubscription();
  }
}
