(function() {
  'use strict';
  angular.module('app', ['chart.js', 'ngRoute', 'ui.bootstrap'])
  .constant('CONST', {
    DEFAULT_ACTIVITY_TYPE: 'Running',
    DATE: 'date',
    DATE_PATTERN: 'yyyy-MM-dd',
    KEY_ALL: 'all',
  })
  .config(logConfig);

  logConfig.$inject = ['$logProvider', '$provide', 'xmlRequestProvider'];

  function logConfig($logProvider, $provide, xmlRequestProvider) {
    // $logProvider.debugEnabled(xmlRequestProvider.$get().synchronousRequest().debug);
    $logProvider.debugEnabled(true);
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

      function enhanceLogging(logger, className, logText) {
        return function() {
          // the next line cannot be simply refactored so it will be disabled from check
          // eslint-disable-next-line prefer-rest-params
          const args = [].slice.call(arguments);
          const logging = `${new Date().toString()} [${logText}] ${className}: ${args[0]}`;
          // add new message to the original debug method
          logger(logging);
        };
      }
      return $delegate;
    }
  };
})();
