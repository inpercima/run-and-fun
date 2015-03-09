(function() {
  'use strict';
  angular.module('runAndFun').controller('ActivityController', ActivityController);

  ActivityController.$inject = [ 'ActivityService' ];

  function ActivityController(ActivityService) {
    var vm = this;

    // public methods
    vm.list = list;
    vm.remove = remove;

    // public fields
    vm.activities = {
      totalElements : 0
    };
    vm.query = '';
    vm.size = 10;
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
      ActivityService.list(vm.size, vm.query).then(function(data) {
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