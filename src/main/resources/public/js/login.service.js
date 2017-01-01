(function() {
  'use strict';
  angular.module('app').service('loginService', loginService);

  loginService.$inject = ['$http', '$log'];

  function loginService($http, $log) {
    const logger = $log.getInstance('loginService');
    // public methods
    this.state = state;

    function state() {
      logger.debug('state');
      return $http.get('/state').then((response) => {
        return response.data;
      });
    }
  }
})();
