(function() {
  'use strict';
  angular.module('app').config(routeConfig);

  routeConfig.$inject = ['$locationProvider', '$routeProvider'];

  function routeConfig($locationProvider, $routeProvider) {
    // use html5Mode to use navigation without hash (#)
    $locationProvider.html5Mode(true);
    // all with attribute 'name' will be shown in navigation
    $routeProvider
    .when('/dash', {
      controller: 'DashController',
      controllerAs: 'vm',
      name: 'Dash',
      text: 'overview of all your stuff',
      templateUrl: 'partials/dash.html',
    })
    .when('/activities', {
      controller: 'ActivityController',
      controllerAs: 'vm',
      text: 'check and search your activities',
      name: 'Activities',
      templateUrl: 'partials/activities.html',
    })
    .when('/graphs', {
      controller: 'GraphsController',
      controllerAs: 'vm',
      name: 'Graphs',
      text: 'control your activities with graphs',
      templateUrl: 'partials/graphs.html',
    })
    .when('/friends', {
      controller: 'FriendsController',
      controllerAs: 'vm',
      name: 'Friends',
      text: 'see what friends do',
      templateUrl: 'partials/friends.html',
    })
    .when('/login', {
      controller: 'LoginController',
      controllerAs: 'vm',
      templateUrl: 'partials/login.html',
    })
    .otherwise({
      redirectTo: '/dash',
    });
  }
})();
