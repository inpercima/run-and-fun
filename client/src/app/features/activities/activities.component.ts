import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';

import { ActivitiesService } from './activities.service';

@Component({
  selector: 'raf-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.css']
})
export class ActivitiesComponent implements OnInit {

  displayedColumns: string[] = ['date', 'type', 'distance', 'duration'];
  dataSource = new MatTableDataSource();

  constructor(private activitiesService: ActivitiesService) { }

  ngOnInit(): void {
    this.activitiesService.list().subscribe(response => {
      this.dataSource = new MatTableDataSource(response);
    });
  }
}
