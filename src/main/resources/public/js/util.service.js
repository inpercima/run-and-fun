(function() {
  'use strict';
  angular.module('app').service('utilService', utilService);

  utilService.$inject = [ '$filter' ];

  function utilService($filter) {
    var service = this; // jshint ignore:line

    const DATE = 'date';
    const DATE_PATTERN = 'yyyy-MM-dd';

    service.simpleKeyYear = function(key) {
      return {
        'key': key,
        'year': key === 'all' ? 'All years' : key
      };
    };

    service.simpleKeyType = function(key) {
      return {
        'key': key,
        'type': key
      };
    };

    service.listYears = function(firstEntry) {
      var result = [ firstEntry ];
      var startYear = 2010;
      var endYear = new Date().getFullYear();
      for (var i = startYear; i <= endYear; i++) {
        result.push(service.simpleKeyYear(i));
      }
      return result;
    };

    service.getMinMaxDate = function(filterYear, filterMinDate, filterMaxDate) {
      return {
        minDate: filterYear.key !== 'all' ? filterYear.year + '-01-01' : $filter(DATE)(filterMinDate, DATE_PATTERN),
        maxDate: filterYear.key !== 'all' ? filterYear.year + '-12-31' : $filter(DATE)(filterMaxDate, DATE_PATTERN)
      };
    };
  }
})();
