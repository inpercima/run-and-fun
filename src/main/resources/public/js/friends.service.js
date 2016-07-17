(function() {
  'use strict';
  angular.module('app').service('friendsService', friendsService);

  friendsService.$inject = [ '$http', '$log', '$rootScope' ];

  function friendsService($http, $log, $rootScope) {
    var logger = $log.getInstance('friendsService');
    // public methods
    this.list = list; // jshint ignore:line
    this.state = state; // jshint ignore:line

    function list() {
      var url = '/listFriends';
      logger.debug(url);
      var promise = $http.get(url).then(function(result) {
        var friends = result.data;
        return friends;
      });
      return promise;
    }

    function state(vm) {
      logger.debug('state');
      $http.get('/state').success(function(data) {
        vm.appState = data;
        $rootScope.loggedIn = vm.appState.username ? true : false;
      });
    }
  }
})();
