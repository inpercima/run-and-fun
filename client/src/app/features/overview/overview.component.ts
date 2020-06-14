import { Component, OnInit } from '@angular/core';

import { ActivitiesService } from '../activities/activities.service';
import { Activity } from '../activities/activity.model';

@Component({
  selector: 'raf-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

  activity: Activity;

  constructor(private activitiesService: ActivitiesService) { }

  ngOnInit(): void {
    this.activitiesService.last().subscribe(response => this.activity = response);
  }

  activityType() {
    switch (this.activity.type) {
      case 'Running': return 'directions_run';
      case 'Walking': case 'Hiking': return 'directions_walk';
      default: return 'help';
    }
  }
}
