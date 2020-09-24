import { Component, OnInit } from '@angular/core';

import { ChartDataSets } from 'chart.js';
import { Label } from 'ng2-charts';

import { UtilService } from '../../core/util.service';
import { Activity } from '../activities/activity.model';
import { ActivitiesService } from '../activities/activities.service';
import { FormBuilder, FormGroup } from '@angular/forms';

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

  years = [];

  searchForm: FormGroup;

  constructor(private activitiesService: ActivitiesService, private formBuilder: FormBuilder, private utilService: UtilService) { }

  ngOnInit(): void {
    this.years = this.utilService.prepareYears();

    this.searchForm = this.formBuilder.group({
      size: [],
      minDate: [],
      maxDate: [],
      year: [this.years[0]],
    });
    this.activitiesService.totalCount().subscribe(size => {
      this.searchForm.get('size').setValue(size);
      this.list();
    });
  }

  list(): void {
    const year = this.searchForm.value.year;
    this.searchForm.get('minDate').setValue(`${year}-01-01`);
    this.searchForm.get('maxDate').setValue(`${year}-12-31`);
    this.activitiesService.list(this.searchForm.value).subscribe(response => {
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
