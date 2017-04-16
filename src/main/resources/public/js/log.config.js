(function() {
  'use strict';
  angular.module('app').config(logConfig);

  logConfig.$inject = ['$logProvider', '$provide', 'xmlRequestProvider'];

  function logConfig($logProvider, $provide, xmlRequestProvider) {
    $logProvider.debugEnabled(xmlRequestProvider.$get().synchronousRequest().debug);
    $provide.decorator('$log', decorator);

    decorator.$inject = ['$delegate'];

    function decorator($delegate) {
      $delegate.getInstance = function(className) {
        return {
          log: enhanceLogging($delegate.log, className, 'STANDARD'),
          info: enhanceLogging($delegate.info, className, 'INFO'),
          warn: enhanceLogging($delegate.warn, className, 'WARN'),
          debug: enhanceLogging($delegate.debug, className, 'DEBUG'),
          error: enhanceLogging($delegate.error, className, 'ERROR'),
        };
      };

      function enhanceLogging(logger, className, logType) {
        return function() {
          // eslint-disable-next-line prefer-rest-params
          let log = arguments[0];
          if (typeof log === 'object') {
            log = Object.keys(log).map((key) => `${key}=${log[key]}`)
            .join(';');
          }
          const logging = `${new Date().toString()} [${logType}] ${className}: ${log}`;
          // add new message to the original method
          logger(logging);
        };
      }
      return $delegate;
    }
  }
})();
