import { Component, OnInit } from '@angular/core';

import { ActivitiesService } from 'src/app/features/activities/activities.service';

@Component({
  selector: 'raf-index-dialog',
  templateUrl: './index-dialog.component.html',
  styleUrls: ['./index-dialog.component.css']
})
export class IndexDialogComponent implements OnInit {

  activities: string;

  loading: boolean;

  constructor(private activitiesService: ActivitiesService) { }

  ngOnInit(): void {
    this.loading = true;
    this.activitiesService.index().subscribe(response => {
      this.loading = false;
      const count: number = Number(response) || 0;
      this.activities = count.toString();
      this.activities += count === 1 ? ' new activity' : ' new activities';
    });
  }
}
