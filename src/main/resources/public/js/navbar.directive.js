(function() {
  'use strict';
  angular.module('runAndFun').directive('navbar', function() {
    return {
      controller : [ '$location', function($location) {
        this.navClass = function(tabName) {
          var currentRoute = $location.path().substring(1) || 'home';
          return currentRoute === tabName ? 'active' : '';
        };
      } ],
      controllerAs : 'tab',
      restrict : 'E',
      templateUrl : '../partials/navbar.html',
      transclude : true
    };
  });
})();
