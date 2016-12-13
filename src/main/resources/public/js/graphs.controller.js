(function() {
  'use strict';
  angular.module('app').controller('GraphsController', GraphsController);

  GraphsController.$inject = ['$log', 'activityService', 'CONST', 'loginService', 'utilService'];

  function GraphsController($log, activityService, CONST, loginService, utilService) {
    const logger = $log.getInstance('GraphsController');
    const vm = this;

    const DASH = '-';
    const DOT = '.';

    const CHART_BAR = 'bar';
    const CHART_LINE = 'line';

    const KM_PER_YEAR = 'year';
    const KM_PER_MONTH = 'month';
    const KM_PER_DAY = 'day';

    // public methods
    vm.list = list;
    vm.refreshDistanceChart = refreshDistanceChart;
    vm.changeDistributionChartType = changeDistributionChartType;

    // public fields
    vm.activities = {
      totalElements: 0,
    };
    vm.runs = {
      totalElements: 0,
    };
    vm.distanceGrouping = KM_PER_MONTH;

    vm.filterYear = utilService.simpleKeyYear(CONST.KEY_ALL);
    vm.years = utilService.listYears(vm.filterYear);
    vm.distributionChartType = CHART_LINE;

    // init
    list();

    function list() {
      logger.debug('list');
      vm.page = utilService.getCurrentPage();

      const dates = utilService.getMinMaxDate(vm.filterYear, vm.filterMinDate, vm.filterMaxDate);
      const params = {
        size: 1000,
        minDate: dates.minDate,
        maxDate: dates.maxDate,
      };
      activityService.list(params).then((data) => {
        vm.activities = data;
        renderDistributionChart(vm.activities.content);
      });
      params.type = CONST.DEFAULT_ACTIVITY_TYPE;
      activityService.list(params).then((data) => {
        vm.runs = data;
        refreshDistanceChart();
      });
    }

    function renderDistanceChart(activities, groupBy) {
      vm.distanceData = [];
      vm.distanceLabels = [];
      vm.distanceSeriesLabels = [];

      if (_.isEmpty(activities)) {
        // reset graphics canvas
        vm.distanceData.push(0);
        vm.distanceSeriesLabels.push(0);
        return;
      }

      const sortedActivities = _.sortBy(activities, CONST.DATE);

      let data = {};
      let labels = {};

      const multipleSeries = vm.filterYear.key === CONST.KEY_ALL && groupBy === KM_PER_MONTH;
      if (multipleSeries) {
        for (let month = 1; month <= 12; month++) {
          vm.distanceLabels.push(`${(month < 10 ? '0' : '')}${month}`);
        }
      }

      for (let item = 0, len = sortedActivities.length; item < len; item++) {
        const readableDateLong = utilService.dateFilter(sortedActivities[item].date).split(DASH);
        const readableDateShort = utilService.dateFilter(sortedActivities[item].date).split(DASH);

        let label;
        let groupByKey;
        let year;

        if (multipleSeries) {
          label = readableDateLong[1];
          groupByKey = label;
          year = readableDateLong[0];
        } else {
          if (groupBy === KM_PER_YEAR) {
            label = readableDateLong[0];
            groupByKey = label;
          } else if (groupBy === KM_PER_MONTH) {
            label = readableDateLong[1] + DASH + readableDateLong[0];
            groupByKey = label;
          } else if (groupBy === KM_PER_DAY) {
            label = readableDateShort[2] + DOT + readableDateShort[1];
            groupByKey = label + DOT + readableDateShort[0];
          }
        }
        const distance = parseFloat(sortedActivities[item].distance.toFixed(2, 2));

        data[groupByKey] = data[groupByKey] + distance || distance;
        labels[groupByKey] = labels[groupByKey] = label;

        if (item + 1 === len || year && year !== utilService.dateFilter(sortedActivities[item + 1].date)
            .split(DASH)[0]) {
          logger.debug('end of series');

          const series = [];
          if (multipleSeries) {
            vm.distanceSeriesLabels.push(year);
            for (const monthKey in vm.distanceLabels) {
              series.push(data[vm.distanceLabels[monthKey]] || 0);
            }
          } else {
            for (const key in labels) {
              series.push(data[key]);
              vm.distanceLabels.push(labels[key]);
            }
          }

          // need array within array because series allows multiple lines -> multiple data-arrays in main data-array
          vm.distanceData.push(series);

          data = {};
          labels = {};
        }
      }
    }

    function renderDistributionChart(activities) {
      logger.debug('renderDistributionChart');
      vm.distributionData = [];
      vm.distributionLabels = [];

      if (_.isEmpty(activities)) {
        // reset graphics canvas
        vm.distributionData.push(0);
        vm.distributionLabels.push('No data');
        return;
      }

      const data = {};
      activities.reduce((obj, val) => {
        obj[val.type] = obj[val.type] + 1 || 1;
        return obj;
      }, data);

      for (const key in data) {
        vm.distributionData.push(data[key]);
        vm.distributionLabels.push(key);
        vm.distributionOptions = {
          legend: {
            display: true,
          },
        };
      }
    }

    function refreshDistanceChart() {
      logger.debug('refreshDistanceChart');
      renderDistanceChart(vm.runs.content, vm.distanceGrouping);
    }

    function changeDistributionChartType() {
      logger.debug('changeChartType');
      vm.distributionChartType = vm.distributionChartType === CHART_BAR ? CHART_LINE : CHART_BAR;
    }
  }
})();
