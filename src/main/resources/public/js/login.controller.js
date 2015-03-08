(function() {
  'use strict';
  angular.module('runAndFun').controller('LoginController', LoginController);

  LoginController.$inject = [ '$http' ];

  function LoginController($http) {
    var vm = this;
    $http.get('/state').success(function(data) {
      vm.appState = data;
    });
  }
})();