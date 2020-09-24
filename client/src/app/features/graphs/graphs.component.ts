import { Component, OnInit } from '@angular/core';

import { ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';

import { Activity } from '../activities/activity.model';
import { ActivitiesService } from '../activities/activities.service';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'raf-graphs',
  templateUrl: './graphs.component.html',
  styleUrls: ['./graphs.component.css']
})
export class GraphsComponent implements OnInit {

  chartData: ChartDataSets[];

  chartLabels: Label[];

  chartOptions = {
    responsive: true,
  };

  constructor(private activitiesService: ActivitiesService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.count();
  }

  count(): void {
    this.activitiesService.totalCount().subscribe(count => this.list(count));
  }

  list(size: number): void {
    const minDate = '2020-01-01';
    const maxDate = '2020-12-31';
    this.activitiesService.list({ size, minDate, maxDate }).subscribe(response => {
      const labels: Label[] = [];
      const activities = {};
      response.forEach((elem: Activity) => {
        if (activities.hasOwnProperty(elem.type)) {
          activities[elem.type] = activities[elem.type] + 1;
        } else {
          activities[elem.type] = 1;
        }
      });
      const data: number[] = [];
      for (const key in activities) {
        if (activities.hasOwnProperty(key)) {
          data.push(activities[key]);
          labels.push(key);
        }
      }
      this.chartData = [{ data, label: 'Activities by Type' }];
      this.chartLabels = labels;
    });
  }
}
