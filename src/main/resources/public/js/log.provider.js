(function() {
  'use strict';
  angular.module('app').config(logProvider);

  logProvider.$inject = [ '$logProvider', '$provide', 'xmlRequestProvider' ];

  function logProvider($logProvider, $provide, xmlRequestProvider) {
    $logProvider.debugEnabled(xmlRequestProvider.$get().synchronousRequest().debug);
    $provide.decorator('$log', decorator);

    decorator.$inject = [ '$delegate' ];

    function decorator($delegate) {
      $delegate.getInstance = function(className) {
        return {
          log: enhanceLogging($delegate.log, className, 'STANDARD'),
          info: enhanceLogging($delegate.info, className, 'INFO'),
          warn: enhanceLogging($delegate.warn, className, 'WARN'),
          debug: enhanceLogging($delegate.debug, className, 'DEBUG'),
          error: enhanceLogging($delegate.error, className, 'ERROR')
        };
      };

      function enhanceLogging(logger, className, logText) {
        return function() {
          var args = [].slice.call(arguments);
          args[0] = [new Date().toString(), ' [' + logText + '] ', className, ': ', args[0]].join('');
          // add new message to the original debug method
          logger.apply(null, args);
        };
      }
      return $delegate;
    }
  }
})();
