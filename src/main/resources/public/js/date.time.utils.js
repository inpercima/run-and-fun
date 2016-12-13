(function() {
  'use strict';
  angular.module('app').service('dateTimeUtils', dateTimeUtils);

  dateTimeUtils.$inject = ['$log'];

  function dateTimeUtils($log) {
    const logger = $log.getInstance('dateTimeUtils');
    const vm = this; // jshint ignore:line

    // public methods
    vm.formattedTimeToSeconds = formattedTimeToSeconds;
    vm.formatTime = formatTime;

    // convert '00:01:11' to 71
    function formattedTimeToSeconds(formattedTime) {
      logger.debug(`formattedTimeToSeconds: ${formattedTime}`);
      const tt = formattedTime.split(':');
      return tt[0] * 3600 + tt[1] * 60 + tt[2] * 1;
    }

    // convert 71 to '00:01:11'
    function formatTime(seconds) {
      const hr = Math.floor(seconds / 3600);
      const min = Math.floor((seconds - (hr * 3600)) / 60);
      const sec = Math.floor(seconds - (hr * 3600) - (min * 60));
      return `${getTimePart(hr)}:${getTimePart(min)}:${getTimePart(sec)}`;
    }

    function getTimePart(part) {
      // it is better readable with nested-ternary so it will be disabled from check
      // eslint-disable-next-line no-nested-ternary
      return part < 1 ? '00' : (part < 10 ? `0${part}` : part);
    }
  }
})();
