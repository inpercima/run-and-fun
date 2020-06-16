(function() {
  'use strict';
  angular.module('app').service('dateTimeUtils', dateTimeUtils);

  dateTimeUtils.$inject = ['$log'];

  function dateTimeUtils($log) {
    const logger = $log.getInstance('dateTimeUtils');
    const vm = this;

    // public methods
    vm.formattedTimeToSeconds = formattedTimeToSeconds;
    vm.formatTime = formatTime;

    // convert '00:01:11' to 71
    function formattedTimeToSeconds(formattedTime) {
      logger.debug(`formattedTimeToSeconds: ${formattedTime}`);
      return formattedTime.split(':').reduce((val, entry, idx) => val + entry * (3600 / Math.pow(60, idx)), 0);
    }

    // convert 71 to '00:01:11'
    function formatTime(seconds) {
      return new Date(seconds * 1000)
              .toISOString()
              .substr(11, 8);
    }
  }
})();
