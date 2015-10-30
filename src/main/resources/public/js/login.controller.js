(function() {
  'use strict';
  angular.module('app').controller('LoginController', LoginController);

  LoginController.$inject = [ '$log', 'loginService' ];

  function LoginController($log, loginService) {
    var vm = this;

    // public methods
    vm.state = state;

    // init
    state();

    function state() {
      $log.debug('LoginController.state');
      loginService.state(vm);
    }
  }
})();
