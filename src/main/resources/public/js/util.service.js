(function() {
  'use strict';
  angular.module('app').service('utilService', utilService);

  utilService.$inject = [ ];

  function utilService() {
    var service = this; // jshint ignore:line

    const KEY_ALL = 'all';

    const LABEL_ALL_YEARS = 'All years';

    service.simpleKeyYear = function(key) {
      return {
        'key': key,
        'year': key === KEY_ALL ? LABEL_ALL_YEARS : key
      };
    };

    service.simpleKeyType = function(key) {
      return {
        'key': key,
        'type': key
      };
    };
  }
})();
