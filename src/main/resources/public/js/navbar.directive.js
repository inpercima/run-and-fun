(function() {
  'use strict';
  angular.module('app').directive('navbar', function() {
    return {
      controller: [ '$location', function($location) {
        this.navClass = function(tabName) {
          var currentRoute = $location.path().substring(1);
          return currentRoute === tabName ? 'active' : '';
        };

        this.navEntries = [
          { display: 'Home', url: '' },
          { display: 'Activities', url: 'activities' },
          { display: 'Graphs', url: 'graphs' },
        ];
      } ],
      controllerAs: 'tab',
      restrict: 'E',
      templateUrl: '../partials/navbar.html',
      transclude: true
    };
  });
})();
