(function() {
  'use strict';
  angular.module('app').controller('LoginController', LoginController);

  LoginController.$inject = ['$log', 'loginService'];

  function LoginController($log, loginService) {
    const logger = $log.getInstance('LoginController');
    const vm = this;

    // init
    state();

    function state() {
      logger.debug('state');
      loginService.state().then((response) => {
        vm.appState = response;
      });
    }
  }
})();
