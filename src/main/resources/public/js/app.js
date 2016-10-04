(function() {
  'use strict';
  angular.module('app', ['chart.js', 'ngRoute', 'ui.bootstrap']).constant('CONST', {
    DEFAULT_ACTIVITY_TYPE: 'Running',
    DATE: 'date',
    DATE_PATTERN: 'yyyy-MM-dd',
    KEY_ALL: 'all',
  });
})();
