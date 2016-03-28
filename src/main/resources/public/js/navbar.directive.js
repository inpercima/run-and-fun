(function() {
  'use strict';
  angular.module('app').directive('navbar', navbar);

  navbar.$inject = [];

  function navbar() {
    return {
      controller: 'NavbarController',
      controllerAs: 'vm',
      restrict: 'E',
      templateUrl: '../partials/navbar.html'
    };
  }
})();
