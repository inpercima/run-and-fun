(function() {
  'use strict';
  angular.module('app').run(routeRun);

  routeRun.$inject = ['$log', '$location', '$rootScope'];

  function routeRun($log, $location, $rootScope) {
    $rootScope.$on('$routeChangeStart', routeChangeStart);

    routeChangeStart.$inject = ['event', 'next'];

    function routeChangeStart(event, next) {
      const logger = $log.getInstance('routeRun');
      logger.debug('loggedIn:', $rootScope.loggedIn);
      // not logged in, call login
      if (!$rootScope.loggedIn) {
        if (next.templateUrl !== 'partials/login.html') {
          $location.path('/login');
        }
      }
    }
  }
})();
