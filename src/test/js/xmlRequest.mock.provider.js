(function() {
  'use strict';
  angular.module('app').provider('xmlRequest', xmlRequest);
  xmlRequest.$inject = [];
  function xmlRequest() {
    function synchronousRequest() {
      return false;
    }
    this.$get = function() {
      return {
        synchronousRequest,
      };
    };
  }
})();
