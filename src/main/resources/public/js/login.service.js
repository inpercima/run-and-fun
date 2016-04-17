(function() {
  'use strict';
  angular.module('app').service('loginService', loginService);

  loginService.$inject = [ '$http', '$log' ];

  function loginService($http, $log) {
    var logger = $log.getInstance('loginService');
    // public methods
    this.state = state; // jshint ignore:line

    function state(vm) {
      logger.debug('state');
      $http.get('/state').success(function(data) {
        vm.appState = data;
      });
    }
  }
})();
