(function() {
  'use strict';
  angular.module('app').service('loginService', loginService);

  loginService.$inject = [ '$http', '$log' ];

  function loginService($http, $log) {
    // public methods
    /* jshint validthis: true */
    this.state = state;

    function state(vm) {
      $log.debug('loginService.state');
      $http.get('/state').success(function(data) {
        vm.appState = data;
      });
    }
  }
})();
