(function() {
  'use strict';
  angular.module('app').run(routeRun);

  routeRun.$inject = ['$log', '$location', '$rootScope', 'loginService'];

  function routeRun($log, $location, $rootScope, loginService) {
    $rootScope.$on('$routeChangeStart', routeChangeStart);

    routeChangeStart.$inject = ['event', 'next'];

    function routeChangeStart(event, next, current) {
      const logger = $log.getInstance('routeRun');
      loginService.state().then((response) => {
        if (!response.username) {
          if (next.templateUrl !== 'partials/login.html') {
            $location.path('/login');
          }
        }
      });
    }
  }
})();
