(function() {
  'use strict';
  angular.module('app').service('loginService', loginService);

  loginService.$inject = [ '$http', '$log', '$rootScope' ];

  function loginService($http, $log, $rootScope) {
    var logger = $log.getInstance('loginService');
    // public methods
    this.state = state; // jshint ignore:line

    function state(vm) {
      logger.debug('state');
      $http.get('/state').success(function(data) {
        vm.appState = data;
        $rootScope.loggedIn = vm.appState.username ? true : false;
      });
    }
  }
})();
