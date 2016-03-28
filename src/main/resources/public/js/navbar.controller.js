(function() {
  'use strict';
  angular.module('app').controller('NavbarController', NavbarController);

  NavbarController.$inject = [ '$log', '$route', '$uibModal', 'loginService' ];

  function NavbarController($log, $route, $uibModal, loginService) {
    var logger = $log.getInstance('NavbarController');
    var vm = this;

    // public methods
    vm.activeRoute = activeRoute;
    vm.open = open;

    // public fields
    vm.routes = [];

    // init
    state();
    routes();

    function state() {
      logger.debug('state');
      loginService.state(vm);
    }

    function activeRoute(route) {
      return route.name === $route.current.name;
    }

    function routes() {
      angular.forEach($route.routes, function(route, path) {
        if (route.name) {
          vm.routes.push({
            path: path,
            name: route.name
          });
        }
      });
    }

    function open() {
      var modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'partials/activities.dialog.html',
        controller: 'ActivityDialogController',
        controllerAs: 'vm',
        size: 'sm'
      });
      modalInstance.result.then(function() {}, function() {});
    }
  }
})();
