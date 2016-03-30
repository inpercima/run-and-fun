(function() {
  'use strict';
  angular.module('app').config(logProvider);

  logProvider.$inject = [ '$logProvider', '$provide' ];

  function logProvider($logProvider, $provide) {
    $logProvider.debugEnabled(true);

    $provide.decorator('$log', decorator);

    decorator.$inject = [ '$delegate' ];

    function decorator($delegate) {
      var logClassName;
      var originalDebug = $delegate.debug;
      // intercept the call to $log.debug() and add our extension
      $delegate.debug = function() {
        if ($logProvider.debugEnabled()) {
          var args = [].slice.call(arguments);
          args[0] = [new Date().toString(), ' [DEBUG] ', logClassName, ': ', args[0]].join('');
          // add new message to the original debug method
          originalDebug.apply(null, args);
        }
      };
      $delegate.getInstance = function(className) {
        logClassName = className;
      };
      return $delegate;
    }
  }
})();
