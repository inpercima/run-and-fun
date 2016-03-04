(function() {
  'use strict';
  angular.module('app').config(routeProvider);

  routeProvider.$inject = [ '$routeProvider' ];

  function routeProvider($routeProvider) {
    $routeProvider.when('/', {
      templateUrl: 'partials/home.html',
      controller: 'LoginController',
      controllerAs: 'vm'
    }).when('/activities', {
      templateUrl: 'partials/activities.html',
      controller: 'ActivityController',
      controllerAs: 'vm'
    }).when('/graphs', {
      templateUrl: 'partials/graphs.html',
      controller: 'GraphsController',
      controllerAs: 'vm'
    }).otherwise({
      redirectTo: '/'
    });
  }
})();
