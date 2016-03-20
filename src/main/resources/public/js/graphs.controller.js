(function() {
  'use strict';
  angular.module('app').controller('GraphsController', GraphsController);

  GraphsController.$inject = [ '$filter', '$log', 'activityService', 'loginService' ];

  function GraphsController($filter, $log, activityService, loginService) {
    var vm = this;

    const DASH = '-';
    const DOT = '.';

    const DATE = 'date';
    const DATE_PATTERN = 'yyyy-MM-dd';

    const CHART_PIE = 'Pie';
    const CHART_POLAR_AREA = 'PolarArea';

    const KM_PER_YEAR = 'year';
    const KM_PER_MONTH = 'month';
    const KM_PER_DAY = 'day';

    // public methods
    vm.list = list;
    vm.refreshLineKmPerMonth = refreshLineKmPerMonth;
    vm.changeChartType = changeChartType;

    // public fields
    vm.activities = {
      totalElements: 0
    };
    vm.runs = {
      totalElements: 0
    };
    vm.averageKmDropdown = KM_PER_MONTH;

    vm.years = [];
    vm.kindOfChart = CHART_PIE;

    // init
    years();
    list();

    function list() {
      $log.debug('GraphsController.list');
      loginService.state(vm);

      var minDate = $filter(DATE)(vm.filterMinDate, DATE_PATTERN);
      var maxDate = $filter(DATE)(vm.filterMaxDate, DATE_PATTERN);
      if (vm.filterYear.key !== 'all') {
        minDate = vm.filterYear.year + '-01-01';
        maxDate = vm.filterYear.year + '-12-31';
      }

      var params = {
        'size': 1000,
        'minDate': minDate,
        'maxDate': maxDate,
      };
      activityService.list(params).then(function(data) {
        vm.activities = data;
        getPieOverview(vm.activities.content);
      });
      params.type = 'Running';
      activityService.list(params).then(function(data) {
        vm.runs = data;
        refreshLineKmPerMonth();
      });
    }

    function getLineKmPerMonth(activities, groupBy) {
      vm.lineKmPerMonthData = [];
      vm.lineKmPerMonthLabels = [];
      vm.lineKmPerMonthSeriesLabels = [];

      if (_.isEmpty(activities)) {
        // reset graphics canvas
        vm.lineKmPerMonthData.push(0);
        vm.lineKmPerMonthSeriesLabels.push(0);
        return;
      }

      activities = _.sortBy(activities, DATE);

      var data = {};
      var labels = {};

      var multipleSeries = !vm.filterYear.key && groupBy === KM_PER_MONTH;
      if (multipleSeries) {
        for (var month = 1; month <= 12; month++) {
          vm.lineKmPerMonthLabels.push((month < 10 ? '0' : '') + month);
        }
      }

      for (var i = 0, len = activities.length; i < len; i++) {
        var readableDateLong = $filter(DATE)(activities[i].date, DATE_PATTERN).split(DASH);
        var readableDateShort = $filter(DATE)(activities[i].date, DATE_PATTERN).split(DASH);

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

        if (i + 1 === len || year && year != $filter(DATE)(activities[i + 1].date, DATE_PATTERN).split(DASH)[0]) {
          $log.debug('end of series');

          var series = [];
          if (multipleSeries) {
            vm.lineKmPerMonthSeriesLabels.push(year);
            for (var monthKey in vm.lineKmPerMonthLabels) {
              series.push(data[vm.lineKmPerMonthLabels[monthKey]] || 0);
            }
          } else {
            for (var key in data) {
              series.push(data[key]);
              vm.lineKmPerMonthLabels.push(labels[key]);
            }
          }

          // need array within array because series allows multiple lines -> multiple data-arrays in main data-array
          vm.lineKmPerMonthData.push(series);

          data = {};
          labels = {};
        }
      }
    }

    function getPieOverview(activities) {
      $log.debug('GraphsController.getPieOverview');
      vm.pieOverviewData = [];
      vm.pieOverviewLabels = [];

      if (_.isEmpty(activities)) {
        // reset graphics canvas
        vm.pieOverviewData.push(0);
        vm.pieOverviewLabels.push('No data');
        return;
      }

      var data = {};
      activities.reduce(function(obj, val) {
        obj[val.type] = obj[val.type] + 1 || 1;
        return obj;
      }, data);

      for (var key in data) {
        vm.pieOverviewData.push(data[key]);
        vm.pieOverviewLabels.push(key);
      }
    }

    function refreshLineKmPerMonth() {
      $log.debug('GraphsController.refreshLineKmPerMonth');
      getLineKmPerMonth(vm.runs.content, vm.averageKmDropdown);
    }

    function changeChartType() {
      $log.debug('GraphsController.changeChartType');
      vm.kindOfChart = vm.kindOfChart === CHART_POLAR_AREA ? CHART_PIE : CHART_POLAR_AREA;
    }

    function years() {
      var filterAll = simpleKeyYear('all');
      vm.years.push(filterAll);
      var startYear = 2010;
      var endYear = new Date().getFullYear();
      for (var i = startYear; i <= endYear; i++) {
        vm.years.push(simpleKeyYear(i));
      }
      vm.filterYear = filterAll;
    }

    function simpleKeyYear(key) {
      return {
        'key': key,
        'year': key === 'all' ? 'All years' : key
      };
    }
  }
})();
