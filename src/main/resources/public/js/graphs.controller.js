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
    var countObject = {};
    var groupByString = null;
    var readableDateLong = [];
    var readableDateShort = [];
    var seriesArray = [];

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

    function getLineKmPerMonth(activites, groupBy){
      countObject = {};
      seriesArray = [];
      vm.lineKmPerMonthData = [];
      vm.lineKmPerMonthLabels = [];

      activites = _.sortBy(activites, 'date');

      for (var i = 0, len = activites.length; i < len; i++) {
        readableDateLong = $filter('date')(activites[i].date, 'yyyy-MMM-dd').split('-');
        readableDateShort = $filter('date')(activites[i].date, 'yyyy-MM-dd').split('-');

        if (groupBy === 'year') { groupByString = readableDateLong[0]; }
        else if (groupBy === 'month') { groupByString = readableDateLong[1] + '-' + readableDateLong[0]; }
        else if (groupBy === 'day') { groupByString = readableDateShort[2] + '.' + readableDateShort[1]; }

        if(!countObject[groupByString]) {
          countObject[groupByString] = parseFloat((activites[i].distance).toFixed(2,10));
        } else {
          countObject[groupByString] += parseFloat((activites[i].distance).toFixed(2,10));
        }
      }

      for (var key in countObject) {
        seriesArray.push(countObject[key]);
        vm.lineKmPerMonthLabels.push(key);
      }

      //need Array in Array for data since "series" allows multiple lines -> multiple data-arrays in main data-array
      vm.lineKmPerMonthData.push(seriesArray);
    }

    function getPieOverview(activites){
      countObject = {};

      for (var i = 0, len = activites.length; i < len; i++) {
        if(!countObject[activites[i].type]) {
          countObject[activites[i].type] = 1;
        } else {
          countObject[activites[i].type] = parseInt(countObject[activites[i].type]) + 1;
        }
      }

      for (var key in countObject) {
        vm.pieOverviewData.push(countObject[key]);
        vm.pieOverviewLabels.push(key);
      }
    }

    function refreshLineKmPerMonth() {
      getLineKmPerMonth(vm.runs.content, vm.averageKmDropdown);
    }
  }
})();
