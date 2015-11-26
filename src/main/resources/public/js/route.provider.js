(function() {
  'use strict';
  angular.module('app').constant('navEntries', [
    {
      controller: 'LoginController',
      display: 'Home',
      templateUrl: 'partials/home.html',
      url: '',
    },
    {
      controller: 'ActivityController',
      display: 'Activities',
      templateUrl: 'partials/activities.html',
      url: 'activities',
    },
    {
      controller: 'GraphsController',
      controllerAs: 'vm',
      display: 'Graphs',
      templateUrl: 'partials/graphs.html',
      url: 'graphs',
    },
  ]);

  angular.module('app').config(routeProvider);

  routeProvider.$inject = [ 'navEntries', '$routeProvider' ];

  function routeProvider(navEntries, $routeProvider) {
    $routeProvider
    .when(getUrl('LoginController', navEntries), getRouteConfig('LoginController', navEntries))
    .when(getUrl('ActivityController', navEntries), getRouteConfig('ActivityController', navEntries))
    .when(getUrl('GraphsController', navEntries), getRouteConfig('GraphsController', navEntries))
    .otherwise({
      redirectTo: '/'
    });
  }

  function getUrl(ctrl, entries) {
    return '/' + entries[getIndex(ctrl, entries)].url;
  }

  function getRouteConfig(ctrl, entries) {
    var i = getIndex(ctrl, entries);
    return {
      templateUrl: entries[i].templateUrl,
      controller: entries[i].controller,
      controllerAs: entries[i].controllerAs || 'vm',
    };
  }

  function getIndex(ctrl, entries) {
    return _.findIndex(entries, function(entry) {
      return entry.controller === ctrl;
    });
  }
})();
