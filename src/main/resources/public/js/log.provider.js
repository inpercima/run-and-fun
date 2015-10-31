(function() {
  'use strict';
  angular.module('app').config(logProvider);

  logProvider.$inject = [ '$logProvider', '$provide' ];

  function logProvider($logProvider, $provide) {
    $logProvider.debugEnabled(true);

    $provide.decorator('$log', decorator);

    decorator.$inject = [ '$delegate' ];

    function decorator($delegate) {
      var originalDebug = $delegate.debug;
      // intercept the call to $log.debug() and add our extension
      $delegate.debug = function () {
        if ($logProvider.debugEnabled()) {
          var args = [].slice.call(arguments);
          args[0] = ['[DEBUG] ', new Date().toString(), ': ', args[0]].join('');
          // add new message to the original debug method
          originalDebug.apply(null, args);
        }
      };
      return $delegate;
    }
  }
})();
