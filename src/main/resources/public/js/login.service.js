(function() {
  'use strict';
  angular.module('app').service('loginService', loginService);

  loginService.$inject = ['$http', '$log', '$rootScope'];

  function loginService($http, $log, $rootScope) {
    const logger = $log.getInstance('loginService');
    // public methods
    this.state = state;

    function state(vm) {
      logger.debug('state');
      $http.get('/state').success((data) => {
        vm.appState = data;
        $rootScope.loggedIn = vm.appState.username !== '';
      });
    }
  }
})();
