import { Component, OnInit } from '@angular/core';

import { ActivitiesService } from './activities.service';

@Component({
  selector: 'raf-activities',
  templateUrl: './activities.component.html',
})
export class ActivitiesComponent implements OnInit {

  constructor(private activitiesService: ActivitiesService) { }

  ngOnInit() {
    this.activitiesService.list([]).subscribe(response => console.log(response));
  }
  
}
