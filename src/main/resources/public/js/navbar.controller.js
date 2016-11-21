(function() {
  'use strict';
  angular.module('app').controller('NavbarController', NavbarController);

  NavbarController.$inject = ['$log', '$route', '$uibModal', 'loginService'];

  function NavbarController($log, $route, $uibModal, loginService) {
    const logger = $log.getInstance('NavbarController');
    const vm = this;

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
      angular.forEach($route.routes, (route, path) => {
        if (route.name) {
          vm.routes.push({
            path,
            name: route.name,
          });
        }
      });
    }

    function open() {
      const modalInstance = $uibModal.open({
        animation: true,
        templateUrl: 'partials/activities.dialog.html',
        controller: 'ActivityDialogController',
        controllerAs: 'vm',
        size: 'sm',
      });
      modalInstance.result.then(() => {}, () => {});
    }
  }
})();
