import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

import { merge, of } from 'rxjs';
import { startWith, switchMap, map, catchError } from 'rxjs/operators';

import { ActivitiesService } from './activities.service';
import { Activity } from './activity.model';

@Component({
  selector: 'raf-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.css']
})
export class ActivitiesComponent implements AfterViewInit, OnInit {

  displayedColumns: string[] = ['functions', 'date', 'type', 'distance', 'duration', 'timePerKm', 'timePer5Km', 'timePer10Km'];
  dataSource = new MatTableDataSource();

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private formBuilder: FormBuilder, private activitiesService: ActivitiesService) { }

  pageSizeOptions: number[] = [5, 10, 25, 50];
  length: number;
  isLoading = true;

  searchForm: FormGroup;

  ngOnInit(): void {
    this.searchForm = this.formBuilder.group({
      page: [0],
      size: [5],
      sort: ['date,desc'],
    });
  }

  ngAfterViewInit(): void {
    // if the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

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
          return this.activitiesService.listAndEnrich(this.searchForm);
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
