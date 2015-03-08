(function() {
  'use strict';
  angular.module('runAndFun').service('DateTimeUtils', DateTimeUtils);

  function DateTimeUtils() {
    var vm = this;

    // public methods
    vm.formattedTimeToSeconds = formattedTimeToSeconds;
    vm.formatTime = formatTime;

    // convert '00:01:11' to 71
    function formattedTimeToSeconds(formattedTime) {
      // console.debug('formattedTimeToSeconds: ' + formattedTime);
      var tt = formattedTime.split(':');
      return tt[0] * 3600 + tt[1] * 60 + tt[2] * 1;
    }

    // convert 71 to '00:01:11'
    function formatTime(secs) {
      var hr = Math.floor(secs / 3600);
      var min = Math.floor((secs - (hr * 3600)) / 60);
      var sec = Math.floor(secs - (hr * 3600) - (min * 60));
      if (hr < 10) {
        hr = '0' + hr;
      }
      if (min < 10) {
        min = '0' + min;
      }
      if (sec < 10) {
        sec = '0' + sec;
      }
      if (hr < 1) {
        hr = '00';
      }
      return hr + ':' + min + ':' + sec;
    }
  }
})();