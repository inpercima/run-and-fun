import { Component, OnInit } from '@angular/core';

import { ActivitiesService } from './features/activities/activities.service';

@Component({
  selector: 'raf-index-activities-dialog',
  templateUrl: 'app-index-activities-dialog.component.html',
})
export class IndexActivitiesDialogComponent implements OnInit {

  private activitiesSuffix: string;

  private activities: number;

  private finished: boolean;

  constructor(private activitiesService: ActivitiesService) { }

  ngOnInit() {
    this.activitiesService.indexActivities().subscribe(response => {
      this.activities = response || 0;
      this.activitiesSuffix = response == 1  ? 'activity' : 'activities';
      this.finished = true;
    });
  }

}
