(function() {
  'use strict';
  angular.module('runAndFun').service('LoginService', LoginService);

  LoginService.$inject = [ '$http' ];

  function LoginService($http) {
    // public methods
    this.state = state;

    function state(vm) {
      console.debug('LoginService.state');
      $http.get('/state').success(function(data) {
        vm.appState = data;
      });
    }
  }
})();
