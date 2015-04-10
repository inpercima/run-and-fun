(function() {
  'use strict';
  angular.module('runAndFun').controller('LoginController', LoginController);

  LoginController.$inject = [ 'LoginService' ];

  function LoginController(LoginService) {
    var vm = this;

    // public methods
    vm.state = state;

    // init
    state();

    function state() {
      console.debug('LoginController.state');
      LoginService.state(vm);
    }
  }
})();
