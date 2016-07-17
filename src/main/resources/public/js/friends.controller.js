(function() {
  'use strict';
  angular.module('app').controller('FriendsController', FriendsController);

  FriendsController.$inject = [ '$log', 'friendsService', 'utilService' ];

  function FriendsController($log, friendsService, utilService) {
    var logger = $log.getInstance('FriendsController');
    var vm = this;

    // public methods
    vm.list = list;

    // init
    list();

    function list() {
      logger.debug('list');
      friendsService.state(vm);
      vm.page = utilService.getCurrentPage();
      friendsService.list().then(function(promise) {
        vm.friends = promise;
      });
    }
  }
})();
