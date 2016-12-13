(function() {
  'use strict';
  angular.module('app').service('utilService', utilService);

  utilService.$inject = ['$filter', '$route', 'CONST'];

  function utilService($filter, $route, CONST) {
    const service = this;

    const LABEL_ALL_YEARS = 'All years';

    const YEAR_BEGIN = '-01-01';
    const YEAR_END = '-12-31';

    service.simpleKeyYear = function(key) {
      return {
        key,
        year: key === CONST.KEY_ALL ? LABEL_ALL_YEARS : key,
      };
    };

    service.simpleKeyType = function(key) {
      return {
        key,
        type: key,
      };
    };

    service.dateFilter = function(value) {
      return $filter(CONST.DATE)(value, CONST.DATE_PATTERN);
    };

    service.listYears = function(firstEntry) {
      const result = [firstEntry];
      for (let year = 2010; year <= new Date().getFullYear(); year++) {
        result.push(service.simpleKeyYear(year));
      }
      return result;
    };

    service.getMinMaxDate = function(filterYear, filterMinDate, filterMaxDate) {
      return {
        minDate: filterYear.key !== CONST.KEY_ALL ? filterYear.year + YEAR_BEGIN : service.dateFilter(filterMinDate),
        maxDate: filterYear.key !== CONST.KEY_ALL ? filterYear.year + YEAR_END : service.dateFilter(filterMaxDate),
      };
    };

    service.getCurrentPage = function() {
      return {
        name: $route.current.name,
        subtext: $route.current.text,
      };
    };
  }
})();
