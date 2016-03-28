(function() {
  'use strict';
  angular.module('app').controller('FriendsController', FriendsController);

  FriendsController.$inject = [ '$log', 'loginService', 'utilService' ];

  function FriendsController($log, loginService, utilService) {
    var logger = $log.getInstance('FriendsController');
    var vm = this;

    // public methods
    vm.state = state;

    // init
    state();

    function state() {
      logger.debug('state');
      loginService.state(vm);
      vm.page = utilService.getCurrentPage();
    }
  }
})();
