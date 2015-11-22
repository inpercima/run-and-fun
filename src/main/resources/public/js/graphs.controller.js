(function() {
  'use strict';
  angular.module('app').controller('GraphsController', GraphsController);

  GraphsController.$inject = [ 'activityService', 'loginService', '$filter' ];

  function GraphsController(activityService, loginService, $filter) {
    var vm = this;

    // public methods
    vm.refreshLineKmPerMonth = refreshLineKmPerMonth;

    // public fields
    vm.activities = {
      totalElements : 0
    };
    vm.runs = {
      totalElements : 0
    };
    vm.averageKmDropdown = 'month';

    //private fields
    vm.changeChartType = changeChartType;
    vm.kindOfChart = 'Pie';
    vm.lineKmPerMonthData = [];
    vm.lineKmPerMonthLabels = [];
    vm.pieOverviewData = [];
    vm.pieOverviewLabels = [];

    // init
    list();

    function list() {
      loginService.state(vm);
      var params = {
        'size': -1
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

    function getLineKmPerMonth(activities, groupBy){
      vm.lineKmPerMonthData = [];
      vm.lineKmPerMonthLabels = [];

      activities = _.sortBy(activities, 'date');

      var data = {};
      var labels = {};
      var dash = '-';
      var dot = '.';
      for (var i = 0, len = activities.length; i < len; i++) {
        var readableDateLong = $filter('date')(activities[i].date, 'yyyy-MMM-dd').split(dash);
        var readableDateShort = $filter('date')(activities[i].date, 'yyyy-MM-dd').split(dash);

        var label;
        var groupByKey;
        if (groupBy === 'year') {
          label = readableDateLong[0];
          groupByKey = label;
        } else if (groupBy === 'month') {
          label = readableDateLong[1] + dash + readableDateLong[0];
          groupByKey = label;
        } else if (groupBy === 'day') {
          label = readableDateShort[2] + dot + readableDateShort[1];
          groupByKey = label + dot + readableDateShort[0];
        }
        var distance = parseFloat(activities[i].distance.toFixed(2,2));
        data[groupByKey] = data[groupByKey] +  distance || distance;
        labels[groupByKey] = labels[groupByKey] = label;
      }

      var series = [];
      for (var key in data) {
        series.push(data[key]);
        vm.lineKmPerMonthLabels.push(labels[key]);
      }

      // need array within array because series allows multiple lines -> multiple data-arrays in main data-array
      vm.lineKmPerMonthData.push(series);
    }

    function getPieOverview(activities){
      var data = {};

      activities.reduce(function(obj, val){
        obj[val.type] = obj[val.type] + 1 || 1;
        return obj;
      }, data);

      for (var key in data) {
        vm.pieOverviewData.push(data[key]);
        vm.pieOverviewLabels.push(key);
      }
    }

    function refreshLineKmPerMonth() {
      getLineKmPerMonth(vm.runs.content, vm.averageKmDropdown);
    }

    function changeChartType() {
      vm.kindOfChart = vm.kindOfChart === 'PolarArea' ? 'Pie' : 'PolarArea';
    }
  }
})();
