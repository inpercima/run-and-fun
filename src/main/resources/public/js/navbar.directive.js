(function() {
  'use strict';
  angular.module('app').directive('navbar', function() {
    return {
      controller : [ '$location', 'navEntries', function($location, navEntries) {
        this.navClass = function(tabName) {
          var currentRoute = $location.path().substring(1);
          return currentRoute === tabName ? 'active' : '';
        };

        this.navEntries = navEntries;
      } ],
      controllerAs : 'tab',
      restrict : 'E',
      templateUrl : '../partials/navbar.html',
      transclude : true
    };
  });
})();
