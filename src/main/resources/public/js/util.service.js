(function() {
  'use strict';
  angular.module('app').service('utilService', utilService);

  utilService.$inject = [ '$filter', 'CONST' ];

  function utilService($filter, CONST) {
    var service = this; // jshint ignore:line

    const LABEL_ALL_YEARS = 'All years';

    const YEAR_BEGIN = '-01-01';
    const YEAR_END = '-12-31';

    service.simpleKeyYear = function(key) {
      return {
        'key': key,
        'year': key === CONST.KEY_ALL ? LABEL_ALL_YEARS : key
      };
    };

    service.simpleKeyType = function(key) {
      return {
        'key': key,
        'type': key
      };
    };

    service.dateFilter = function(value) {
      return $filter(CONST.DATE)(value, CONST.DATE_PATTERN);
    };

    service.listYears = function(firstEntry) {
      var result = [ firstEntry ];
      for (var i = 2010; i <= new Date().getFullYear(); i++) {
        result.push(service.simpleKeyYear(i));
      }
      return result;
    };

    service.getMinMaxDate = function(filterYear, filterMinDate, filterMaxDate) {
      return {
        minDate: filterYear.key !== CONST.KEY_ALL ? filterYear.year + YEAR_BEGIN : service.dateFilter(filterMinDate),
        maxDate: filterYear.key !== CONST.KEY_ALL ? filterYear.year + YEAR_END : service.dateFilter(filterMaxDate)
      };
    };
  }
})();
