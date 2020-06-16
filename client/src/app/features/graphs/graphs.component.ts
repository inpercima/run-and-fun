import { Component, OnInit } from '@angular/core';

import { ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';

import { Activity } from '../activities/activity.model';
import { ActivitiesService } from '../activities/activities.service';

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

  constructor(private activitiesService: ActivitiesService) { }

  ngOnInit() {
    this.activitiesService.list().subscribe(response => {
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
