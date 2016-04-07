(function() {
  'use strict';
  angular.module('app', [ 'ngRoute', 'chart.js' ]).constant('CONST', {
    DEFAULT_ACTIVITY_TYPE: 'Running',
    DATE: 'date',
    DATE_PATTERN: 'yyyy-MM-dd',
    KEY_ALL: 'all',
  });
})();
