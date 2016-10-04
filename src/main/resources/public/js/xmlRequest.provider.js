/* globals XMLHttpRequest */

(function() {
  'use strict';
  angular.module('app').provider('xmlRequest', xmlRequest);

  xmlRequest.$inject = [];

  function xmlRequest() {
    function synchronousRequest() {
      const ajax = new XMLHttpRequest();
      let config;
      if (ajax !== null) {
        // second parameter 'false' makes the request synchronous
        ajax.open('GET', 'js/config.json', false);
        ajax.onreadystatechange = function() {
          if (this.readyState === 4) {
            if (this.status === 200) {
              config = JSON.parse(this.responseText);
            }
          }
        };
        ajax.send(null);
      }
      return config;
    }

    this.$get = function() { // jshint ignore:line
      return {
        synchronousRequest,
      };
    };
  }
})();
