(function() {
  'use strict';
  angular.module('app').controller('GraphsController', GraphsController);

  GraphsController.$inject = [ '$log', 'activityService', 'CONST', 'loginService', 'utilService' ];

  function GraphsController($log, activityService, CONST, loginService, utilService) {
    var logger = $log.getInstance('GraphsController');
    var vm = this;

    const DASH = '-';
    const DOT = '.';

    const CHART_PIE = 'Pie';
    const CHART_POLAR_AREA = 'PolarArea';

    const KM_PER_YEAR = 'year';
    const KM_PER_MONTH = 'month';
    const KM_PER_DAY = 'day';

    // public methods
    vm.list = list;
    vm.refreshDistanceChart = refreshDistanceChart;
    vm.changeDistributionChartType = changeDistributionChartType;

    // public fields
    vm.activities = {
      totalElements: 0
    };
    vm.runs = {
      totalElements: 0
    };
    vm.distanceGrouping = KM_PER_MONTH;

    vm.filterYear = utilService.simpleKeyYear(CONST.KEY_ALL);
    vm.years = utilService.listYears(vm.filterYear);
    vm.distributionChartType = CHART_PIE;

    // init
    list();

    function list() {
      logger.debug('list');
      loginService.state(vm);
      vm.page = utilService.getCurrentPage();

      var dates = utilService.getMinMaxDate(vm.filterYear, vm.filterMinDate, vm.filterMaxDate);
      var params = {
        'size': 1000,
        'minDate': dates.minDate,
        'maxDate': dates.maxDate,
      };
      activityService.list(params).then(function(data) {
        vm.activities = data;
        renderDistributionChart(vm.activities.content);
      });
      params.type = CONST.DEFAULT_ACTIVITY_TYPE;
      activityService.list(params).then(function(data) {
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

      activities = _.sortBy(activities, CONST.DATE);

      var data = {};
      var labels = {};

      var multipleSeries = vm.filterYear.key === CONST.KEY_ALL && groupBy === KM_PER_MONTH;
      if (multipleSeries) {
        for (var month = 1; month <= 12; month++) {
          vm.distanceLabels.push((month < 10 ? '0' : '') + month);
        }
      }

      for (var i = 0, len = activities.length; i < len; i++) {
        var readableDateLong = utilService.dateFilter(activities[i].date).split(DASH);
        var readableDateShort = utilService.dateFilter(activities[i].date).split(DASH);

        var label;
        var groupByKey;
        var year;

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
        var distance = parseFloat(activities[i].distance.toFixed(2, 2));

        data[groupByKey] = data[groupByKey] + distance || distance;
        labels[groupByKey] = labels[groupByKey] = label;

        if (i + 1 === len || year && year !== utilService.dateFilter(activities[i + 1].date).split(DASH)[0]) {
          logger.debug('end of series');

          var series = [];
          if (multipleSeries) {
            vm.distanceSeriesLabels.push(year);
            for (var monthKey in vm.distanceLabels) {
              series.push(data[vm.distanceLabels[monthKey]] || 0);
            }
          } else {
            for (var key in labels) {
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

      var data = {};
      activities.reduce(function(obj, val) {
        obj[val.type] = obj[val.type] + 1 || 1;
        return obj;
      }, data);

      for (var key in data) {
        vm.distributionData.push(data[key]);
        vm.distributionLabels.push(key);
      }
    }

    function refreshDistanceChart() {
      logger.debug('refreshDistanceChart');
      renderDistanceChart(vm.runs.content, vm.distanceGrouping);
    }

    function changeDistributionChartType() {
      logger.debug('changeChartType');
      vm.distributionChartType = vm.distributionChartType === CHART_POLAR_AREA ? CHART_PIE : CHART_POLAR_AREA;
    }
  }
})();
