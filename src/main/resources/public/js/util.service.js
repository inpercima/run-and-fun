(function() {
  'use strict';
  angular.module('app').service('utilService', utilService);

  utilService.$inject = ['$filter', '$route', 'CONST'];

  function utilService($filter, $route, CONST) {
    const service = this;

    const LABEL_ALL_YEARS = 'All years';

    const YEAR_BEGIN = '-01-01';
    const YEAR_END = '-12-31';

    service.simpleKeyYear = (key) => ({
      key,
      year: key === CONST.KEY_ALL ? LABEL_ALL_YEARS : key,
    });

    service.simpleKeyType = (key) => ({
      key,
      type: key,
    });

    service.dateFilter = (value) => $filter(CONST.DATE)(value, CONST.DATE_PATTERN);

    service.listYears = (firstEntry) => {
      const result = [firstEntry];
      _.range(2010, new Date().getFullYear() + 1).forEach((entry) => result.push(service.simpleKeyYear(entry)));
      return result;
    };

    service.getMinMaxDate = (filterYear, filterMinDate, filterMaxDate) => ({
      minDate: filterYear.key !== CONST.KEY_ALL ? filterYear.year + YEAR_BEGIN : service.dateFilter(filterMinDate),
      maxDate: filterYear.key !== CONST.KEY_ALL ? filterYear.year + YEAR_END : service.dateFilter(filterMaxDate),
    });

    service.getCurrentPage = () => ({
      name: $route.current.name,
      subtext: $route.current.text,
    });
  }
})();
