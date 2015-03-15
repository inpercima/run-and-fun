(function() {
  'use strict';
  angular.module('runAndFun').controller('ActivityController', ActivityController);

  ActivityController.$inject = [ 'ActivityService', '$filter' ];

  function ActivityController(ActivityService, $filter) {
    var vm = this;

    // public methods
    vm.list = list;
    vm.remove = remove;

    // public fields
    vm.activities = {
      totalElements : 0
    };
    vm.size = 10;
    vm.filterType = 'running';

    vm.totalActivities = 0;
    vm.totalDistance = 0;
    vm.totalDuration = 0;
    vm.totalTimePerKm = 0;
    vm.totalTimePer5Km = 0;
    vm.totalTimePer10Km = 0;

    // init
    list();

    function list() {
      console.debug('ActivityController.list');
      var minDate = $filter('date')(vm.filterMinDate, 'yyyy-MM-dd');
      var maxDate = $filter('date')(vm.filterMaxDate, 'yyyy-MM-dd');
      if (vm.filterYear) {
        minDate = vm.filterYear + '-01-01';
        maxDate = vm.filterYear + '-12-31';
      }
      ActivityService.list(vm.size, vm.filterType, minDate, maxDate, vm.filterMinDistance, vm.filterMaxDistance, vm.filterFulltext).then(
          function(data) {
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
  }
})();
