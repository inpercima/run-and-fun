(function() {
  'use strict';
  angular.module('runAndFun').config([ '$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {
      templateUrl : 'partials/home.html',
      controller : 'LoginController',
      controllerAs : 'vm'
    }).when('/activities', {
      templateUrl : 'partials/activities.html',
      controller : 'ActivityController',
      controllerAs : 'vm'
    }).otherwise({
      redirectTo : '/'
    });
  } ]);
})();
