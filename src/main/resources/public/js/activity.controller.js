(function() {
  'use strict';
  angular.module('app').controller('ActivityController', ActivityController);

  ActivityController.$inject = [ '$log', 'activityService', 'CONST', 'loginService', 'utilService' ];

  function ActivityController($log, activityService, CONST, loginService, utilService) {
    var logger = $log.getInstance('ActivityController');
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

    vm.filterYear = utilService.simpleKeyYear(CONST.KEY_ALL);
    vm.years = utilService.listYears(vm.filterYear);
    vm.filterType = [];
    vm.types = [];

    // init
    types();
    list();

    function list() {
      logger.debug('list');
      loginService.state(vm);

      var filterType = [];
      if (!vm.allActivityTypes) {
        angular.forEach(vm.filterType, function(type) {
          filterType.push(type.key);
        });
      } else {
        filterType = '';
      }
      var dates = utilService.getMinMaxDate(vm.filterYear, vm.filterMinDate, vm.filterMaxDate);
      var params = {
        'size': vm.size,
        'type': filterType,
        'minDate': dates.minDate,
        'maxDate': dates.maxDate,
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
      logger.debug('ActivityController.remove');
      var index = vm.activities.content.indexOf(activity);
      vm.activities.content.splice(index, 1);
      activityService.recalculateTotals(vm);
    }

    function types() {
      vm.filterType.push(utilService.simpleKeyType(CONST.DEFAULT_ACTIVITY_TYPE));
      vm.types.push(vm.filterType[0]);
      vm.types.push(utilService.simpleKeyType('Cycling'));
      vm.types.push(utilService.simpleKeyType('Hiking'));
      vm.types.push(utilService.simpleKeyType('Walking'));
    }
  }
})();
