(function() {
  'use strict';
  angular.module('runAndFun').service('ActivityService', ActivityService);

  ActivityService.$inject = [ '$http', 'DateTimeUtils' ];

  function ActivityService($http, DateTimeUtils) {
    // public methods
    this.list = list;
    this.recalculateTotals = recalculateTotals;

    function list(size, query, minDistance, maxDistance) {
      var url = '/listActivities?size=' + size;
      if (query) {
        url += '&query=' + query;
      }
      if (minDistance) {
        url += '&minDistance=' + minDistance;
      }
      if (maxDistance) {
        url += '&maxDistance=' + maxDistance;
      }
      console.debug('ActivityService ' + url);
      return $http.get(url).then(function(result) {
        var activities = result.data;
        enrichWithTimePerKm(activities.content);
        return activities;
      });
    }

    function recalculateTotals(vm) {
      // console.debug('recalculateTotals');
      vm.totalActivities = vm.activities.content.length;
      vm.totalDistance = getTotalDistance(vm.activities.content);
      var totalTime = getTotalTime(vm.activities.content);
      vm.totalDuration = DateTimeUtils.formatTime(totalTime);
      vm.totalTimePerKm = DateTimeUtils.formatTime(calcTimePerKm(totalTime, vm.totalDistance));
      vm.totalTimePer5Km = DateTimeUtils.formatTime(5 * calcTimePerKm(totalTime, vm.totalDistance));
      vm.totalTimePer10Km = DateTimeUtils.formatTime(10 * calcTimePerKm(totalTime, vm.totalDistance));
    }

    function enrichWithTimePerKm(content) {
      // console.debug('enrichWithTimePerKm');
      angular.forEach(content, function(activity) {
        var seconds = DateTimeUtils.formattedTimeToSeconds(activity.duration);
        activity.timePerKm = DateTimeUtils.formatTime(calcTimePerKm(seconds, activity.distance));
        activity.timePer5Km = DateTimeUtils.formatTime(calcTimePerKm(5 * seconds, activity.distance));
        activity.timePer10Km = DateTimeUtils.formatTime(calcTimePerKm(10 * seconds, activity.distance));
      });
    }

    function calcTimePerKm(time, distance) {
      return distance !== 0 ? time / distance : 0;
    }

    function getTotalDistance(activities) {
      // console.debug('getTotalDistance');
      var sum = 0;
      for (var i = 0; i < activities.length; i++) {
        sum += activities[i].distance;
      }
      return sum.toFixed(2);
    }

    function getTotalTime(activities) {
      // console.debug('getTotalTime');
      var sum = 0;
      for (var i = 0; i < activities.length; i++) {
        sum += DateTimeUtils.formattedTimeToSeconds(activities[i].duration);
      }
      return sum;
    }
  }
})();