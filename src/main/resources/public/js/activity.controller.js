(function() {
  'use strict';
  angular.module('app').controller('ActivityController', ActivityController);

  ActivityController.$inject = [ '$filter', '$log', 'activityService', 'loginService', 'utilService' ];

  function ActivityController($filter, $log, activityService, loginService, utilService) {
    var vm = this;

    // public methods
    vm.list = list;
    vm.remove = remove;

    // public fields
    vm.activities = {
      totalElements: 0
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
      $log.debug('ActivityController.list');
      loginService.state(vm);

      var dates = utilService.getMinMaxDate(vm.filterYear, vm.filterMinDate, vm.filterMaxDate);
      var minDate = dates.minDate;
      var maxDate = dates.maxDate;

      var filterType = [];
      if (!vm.allActivityTypes) {
        angular.forEach(vm.filterType, function(type) {
          filterType.push(type.key);
        });
      } else {
        filterType = '';
      }
      var params = {
        'size': vm.size,
        'type': filterType,
        'minDate': minDate,
        'maxDate': maxDate,
        'minDistance': vm.filterMinDistance,
        'maxDistance': vm.filterMaxDistance,
        'query': vm.filterFulltext
      };
      activityService.list(params).then(function(data) {
        vm.activities = data;
        activityService.recalculateTotals(vm);
      });
    }

    function remove(activity) {
      $log.debug('ActivityController.remove');
      var index = vm.activities.content.indexOf(activity);
      vm.activities.content.splice(index, 1);
      activityService.recalculateTotals(vm);
    }

    function years() {
      var filterAll = utilService.simpleKeyYear('all');
      vm.filterYear = filterAll;
      vm.years = utilService.listYears(filterAll);
    }

    function types() {
      var filterRunning = utilService.simpleKeyType('Running');
      vm.filterType.push(filterRunning);
      vm.types.push(filterRunning);
      vm.types.push(utilService.simpleKeyType('Cycling'));
      vm.types.push(utilService.simpleKeyType('Hiking'));
      vm.types.push(utilService.simpleKeyType('Walking'));
    }
  }
})();
