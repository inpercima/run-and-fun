import { Component, OnInit } from '@angular/core';

import { ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';

import { UtilService } from '../../core/util.service';
import { ActivitiesService } from '../activities/activities.service';

@Component({
  selector: 'raf-graphs',
  templateUrl: './graphs.component.html',
})
export class GraphsComponent implements OnInit {

  chartData!: ChartDataSets[];
  chartLabels!: Label[];
  chartOptions = {
    responsive: true,
  };

  years: string[] = [];

  filterForm = this.utilService.defaultOptions(0);

  constructor(private activitiesService: ActivitiesService, private utilService: UtilService) { }

  ngOnInit(): void {
    this.years = this.utilService.prepareYears();
    this.activitiesService.totalCount().subscribe(size => {
      this.filterForm = this.utilService.defaultOptions(size);
      this.list();
    });
  }

  list(): void {
    this.activitiesService.list(this.utilService.prepareParams(this.filterForm)).subscribe(response => {
      const activities: { [k: string]: any } = {};
      response.map(activity => {
        if (activities.hasOwnProperty(activity.type)) {
          activities[activity.type] = activities[activity.type] + 1;
        } else {
          activities[activity.type] = 1;
        }
      });

      const data: number[] = [];
      const labels: Label[] = [];
      for (const activityType in activities) {
        if (activities.hasOwnProperty(activityType)) {
          data.push(activities[activityType]);
          labels.push(activityType);
        }
      }
      this.chartData = [{ data, label: 'Activities by Type' }];
      this.chartLabels = labels;
    });
  }
}
