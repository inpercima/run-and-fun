(function() {
  'use strict';
  angular.module('app').service('friendsService', friendsService);

  friendsService.$inject = ['$http', '$log', '$rootScope'];

  function friendsService($http, $log, $rootScope) {
    const logger = $log.getInstance('friendsService');
    // public methods
    this.list = list; // jshint ignore:line
    this.state = state; // jshint ignore:line

    function list() {
      const url = '/listFriends';
      logger.debug(url);
      const promise = $http.get(url).then((result) => {
        const friends = result.data;
        return friends;
      });
      return promise;
    }

    function state(vm) {
      logger.debug('state');
      $http.get('/state').success((data) => {
        vm.appState = data;
        $rootScope.loggedIn = vm.appState.username !== '';
      });
    }
  }
})();
