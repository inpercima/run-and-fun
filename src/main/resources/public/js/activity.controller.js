(function() {
  'use strict';
  angular.module('runAndFun').controller('ActivityController', ActivityController);

  ActivityController.$inject = [ 'ActivityService', 'LoginService', '$filter' ];

  function ActivityController(ActivityService, LoginService, $filter) {
    var vm = this;

    // public methods
    vm.list = list;
    vm.remove = remove;

    // public fields
    vm.activities = {
      totalElements : 0
    };
    vm.predicate = 'date';
    vm.reverse = true;

    vm.size = 10;

    vm.totalActivities = 0;
    vm.totalDistance = 0;
    vm.totalDuration = 0;
    vm.totalTimePerKm = 0;
    vm.totalTimePer5Km = 0;
    vm.totalTimePer10Km = 0;

    vm.allActivityTypes = false;

    vm.years = [];
    vm.types = [];
    vm.filterType = [];

    // init
    types();
    years();
    list();

    function list() {
      console.debug('ActivityController.list');
      LoginService.state(vm);
      var minDate = $filter('date')(vm.filterMinDate, 'yyyy-MM-dd');
      var maxDate = $filter('date')(vm.filterMaxDate, 'yyyy-MM-dd');
      if (vm.filterYear.key) {
        minDate = vm.filterYear.year + '-01-01';
        maxDate = vm.filterYear.year + '-12-31';
      }
      var filterType = [];
      if (!vm.allActivityTypes) {
        angular.forEach(vm.filterType, function(type) {
          filterType.push(type.key);
        });
      }
      ActivityService.list(vm.size, filterType, minDate, maxDate, vm.filterMinDistance, vm.filterMaxDistance,
          vm.filterFulltext).then(function(data) {
        vm.activities = data;
        ActivityService.recalculateTotals(vm);
      });
    }

    function remove(activity) {
      console.debug('ActivityController.remove');
      var index = vm.activities.content.indexOf(activity);
      vm.activities.content.splice(index, 1);
      ActivityService.recalculateTotals(vm);
    }

    function years() {
      var filterAll = simpleKeyYear('');
      vm.years.push(filterAll);
      var startYear = 2010;
      var endYear = new Date().getFullYear();
      for (var i = startYear; i <= endYear; i++) {
        vm.years.push(simpleKeyYear(i));
      }
      vm.filterYear = filterAll;
    }

    function types() {
      var filterRunning = simpleKeyType('Running');
      vm.types.push(filterRunning);
      vm.types.push(simpleKeyType('Cycling'));
      vm.types.push(simpleKeyType('Hiking'));
      vm.types.push(simpleKeyType('Walking'));
      vm.filterType.push(filterRunning);
    }
    
    function simpleKeyYear(key) {
      return {
        'key' : key,
        'year' : key === '' ? 'All years' : key
      };
    }

    function simpleKeyType(key) {
      return {
        'key' : key,
        'type' : key
      };
    }
  }
})();
