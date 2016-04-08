(function() {
  'use strict';
  angular.module('app').controller('LoginController', LoginController);

  LoginController.$inject = [ '$log', 'loginService' ];

  function LoginController($log, loginService) {
    var logger = $log.getInstance('LoginController');
    var vm = this;

    // public methods
    vm.state = state;

    // init
    state();

    function state() {
      logger.debug('state');
      loginService.state(vm);
    }
  }
})();
