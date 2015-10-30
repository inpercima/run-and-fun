(function() {
  'use strict';
  angular.module('app').service('activityService', activityService);

  activityService.$inject = [ '$http', '$log', 'dateTimeUtils' ];

  function activityService($http, $log, dateTimeUtils) {
    // public methods
    /* jshint validthis: true */
    this.list = list;
    /* jshint validthis: true */
    this.recalculateTotals = recalculateTotals;

    function list(params) {
      var url = '/listActivities?size=' + params.size;
      url += addParams(params);
      $log.debug('activityService ' + url);
      return $http.get(url).then(function(result) {
        var activities = result.data;
        enrichWithTimePerKm(activities.content);
        enrichWithStatistics(activities.content);
        return activities;
      });
    }

    function recalculateTotals(vm) {
      $log.debug('recalculateTotals');
      vm.totalActivities = vm.activities.content.length;
      vm.totalDistance = getTotalDistance(vm.activities.content);
      var totalTime = getTotalTime(vm.activities.content);
      vm.totalDuration = dateTimeUtils.formatTime(totalTime);
      vm.totalTimePerKm = dateTimeUtils.formatTime(calcTimePerKm(totalTime, vm.totalDistance));
      vm.totalTimePer5Km = dateTimeUtils.formatTime(5 * calcTimePerKm(totalTime, vm.totalDistance));
      vm.totalTimePer10Km = dateTimeUtils.formatTime(10 * calcTimePerKm(totalTime, vm.totalDistance));
    }

    var distanceHalfMarathon = 21.097;

    function enrichWithTimePerKm(content) {
      $log.debug('enrichWithTimePerKm');
      angular.forEach(content, function(activity) {
        var seconds = dateTimeUtils.formattedTimeToSeconds(activity.duration);
        activity.timePerKmInSeconds = calcTimePerKm(seconds, activity.distance);
        activity.timePerKm = dateTimeUtils.formatTime(activity.timePerKmInSeconds);
        activity.timePer5Km = dateTimeUtils.formatTime(calcTimePerKm(5 * seconds, activity.distance));
        activity.timePer10Km = dateTimeUtils.formatTime(calcTimePerKm(10 * seconds, activity.distance));

        activity.additionalInfo = '';
        // prepend half marathon time if distance is in interval [17, 20]
        if (activity.distance >= 17 && activity.distance <= 20) {
          addTimeForKm(activity, seconds, distanceHalfMarathon);
        }
        // calculate time for every distance starting with nearest km (round ceil)
        for (var i = Math.ceil(activity.distance); i >= 1; i--) {
          if (activity.additionalInfo.length > 0) {
            activity.additionalInfo += '\n';
          }
          if (i === 21) {
            addTimeForKm(activity, seconds, distanceHalfMarathon);
            activity.additionalInfo += '\n';
          }
          addTimeForKm(activity, seconds, i);
        }
      });
    }

    function enrichWithStatistics(activities) {
      $log.debug('enrichWithStatistics');
      angular.forEach(activities, function(activity) {
        var distanceRound = Math.round(activity.distance);
        var distanceStep = Math.ceil(Math.sqrt(distanceRound));
        rateByDistance(activity, activities, distanceRound - distanceStep, distanceRound + distanceStep);
      });
    }

    function rateByDistance(activity, activities, minDistance, maxDistance) {
      var matches = activities.filter(function (current) {
        return current.distance > minDistance && current.distance <= maxDistance;
      }).sort(function(o1, o2) {
        return o1.timePerKmInSeconds - o2.timePerKmInSeconds;
      });
      var i = 0;
      while (i < matches.length) {
        if (activity.timePerKmInSeconds <= matches[i++].timePerKmInSeconds) {
          break;
        }
      }
      activity.intervalStats = 'Platz ' + i + ' von ' + matches.length + ' zwischen ' + minDistance + ' und ' + maxDistance + 'km';
    }

    function addTimeForKm(activity, totalSeconds, distance) {
      activity.additionalInfo += dateTimeUtils.formatTime(calcTimePerKm(distance * totalSeconds, activity.distance)) + 'min/' + distance + 'km';
    }

    function calcTimePerKm(time, distance) {
      return distance !== 0 ? time / distance : 0;
    }

    function getTotalDistance(activities) {
      $log.debug('getTotalDistance');
      var sum = 0;
      for (var i = 0; i < activities.length; i++) {
        sum += activities[i].distance;
      }
      return sum.toFixed(2);
    }

    function getTotalTime(activities) {
      $log.debug('getTotalTime');
      var sum = 0;
      for (var i = 0; i < activities.length; i++) {
        sum += dateTimeUtils.formattedTimeToSeconds(activities[i].duration);
      }
      return sum;
    }

    function addParams(params) {
      var url = '';
      for (var param in params) {
        if (params[param]) {
          url += '&' + param + '=' + params[param];
        }
      }
      return url;
    }
  }
})();
