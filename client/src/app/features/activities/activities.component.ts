import { Component, OnInit } from '@angular/core';
import { ActivitiesService } from './activities.service';

@Component({
  selector: 'raf-activities',
  templateUrl: './activities.component.html',
  styleUrls: ['./activities.component.css']
})
export class ActivitiesComponent implements OnInit {

  constructor(private activitiesService: ActivitiesService) { }

  ngOnInit(): void {
    this.activitiesService.list().subscribe();
  }

}
