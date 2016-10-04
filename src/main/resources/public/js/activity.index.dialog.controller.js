(function() {
  'use strict';
  angular.module('app').controller('ActivityDialogController', ActivityDialogController);

  ActivityDialogController.$inject = ['$log', '$uibModalInstance', 'activityService'];

  function ActivityDialogController($log, $uibModalInstance, activityService) {
    const logger = $log.getInstance('ActivityDialogController');
    const vm = this;

    // public methods
    vm.ok = ok;

    // public fields
    vm.activities = null;

    // init
    indexActivities();

    function indexActivities() {
      logger.debug('indexActivities');
      activityService.indexActivities().then((promise) => {
        vm.activities = promise.data ? promise.data : 0;
        vm.activities += promise.data < 2 ? ' new activity' : ' new activities';
      });
    }

    function ok() {
      logger.debug('ok');
      $uibModalInstance.close('close');
    }
  }
})();
