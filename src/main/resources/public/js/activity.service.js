(function() {
  'use strict';
  angular.module('runAndFun').service('ActivityService', ActivityService);

  ActivityService.$inject = [ '$http', 'DateTimeUtils' ];

  function ActivityService($http, DateTimeUtils) {
    // public methods
    this.list = list;
    this.recalculateTotals = recalculateTotals;

    function list(size, type, minDate, maxDate, minDistance, maxDistance, query) {
      var url = '/listActivities?size=' + size;
      if (type) {
        url += '&type=' + type;
      }
      if (minDate) {
        url += '&minDate=' + minDate;
      }
      if (maxDate) {
        url += '&maxDate=' + maxDate;
      }
      if (minDistance) {
        url += '&minDistance=' + minDistance;
      }
      if (maxDistance) {
        url += '&maxDistance=' + maxDistance;
      }
      if (query) {
        url += '&query=' + query;
      }
      console.debug('ActivityService ' + url);
      return $http.get(url).then(function(result) {
        var activities = result.data;
        enrichWithTimePerKm(activities.content);
        enrichWithStatistics(activities.content);
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

    var distanceHalfMarathon = 21.097;

    function enrichWithTimePerKm(content) {
      // console.debug('enrichWithTimePerKm');
      angular.forEach(content, function(activity) {
        var seconds = DateTimeUtils.formattedTimeToSeconds(activity.duration);
        activity.timePerKmInSeconds = calcTimePerKm(seconds, activity.distance);
        activity.timePerKm = DateTimeUtils.formatTime(activity.timePerKmInSeconds);
        activity.timePer5Km = DateTimeUtils.formatTime(calcTimePerKm(5 * seconds, activity.distance));
        activity.timePer10Km = DateTimeUtils.formatTime(calcTimePerKm(10 * seconds, activity.distance));

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
      // console.debug('enrichWithStatistics');
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
      activity.additionalInfo += DateTimeUtils.formatTime(calcTimePerKm(distance * totalSeconds, activity.distance)) + 'min/' + distance + 'km';
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
