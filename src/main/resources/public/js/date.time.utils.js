(function() {
  'use strict';
  angular.module('app').service('dateTimeUtils', dateTimeUtils);

  dateTimeUtils.$inject = [ '$log' ];

  function dateTimeUtils($log) {
    var logger = $log.getInstance('dateTimeUtils');
    /* jshint validthis: true */
    var vm = this;

    // public methods
    vm.formattedTimeToSeconds = formattedTimeToSeconds;
    vm.formatTime = formatTime;

    // convert '00:01:11' to 71
    function formattedTimeToSeconds(formattedTime) {
      logger.debug('formattedTimeToSeconds: ' + formattedTime);
      var tt = formattedTime.split(':');
      return tt[0] * 3600 + tt[1] * 60 + tt[2] * 1;
    }

    // convert 71 to '00:01:11'
    function formatTime(seconds) {
      var hr = Math.floor(seconds / 3600);
      var min = Math.floor((seconds - (hr * 3600)) / 60);
      var sec = Math.floor(seconds - (hr * 3600) - (min * 60));
      return getTimePart(hr) + ':' + getTimePart(min) + ':' + getTimePart(sec);
    }

    function getTimePart(part) {
      return part < 1 ? '00' : (part < 10 ? '0' + part : part);
    }
  }
})();
