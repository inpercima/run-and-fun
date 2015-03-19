(function() {
  'use strict';
  angular.module('runAndFun').config([ '$routeProvider', function($routeProvider) {
    $routeProvider.when('/', {
      templateUrl : 'home.html',
      controller : 'LoginController',
      controllerAs : 'vm'
    }).when('/activities', {
      templateUrl : 'activities.html',
      controller : 'ActivityController',
      controllerAs : 'vm'
    }).otherwise({
      redirectTo : '/'
    });
  } ]);
})();
