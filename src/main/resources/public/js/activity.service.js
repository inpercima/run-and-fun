(function() {
  'use strict';
  angular.module('app').service('activityService', activityService);

  activityService.$inject = ['$http', '$log', 'dateTimeUtils'];

  function activityService($http, $log, dateTimeUtils) {
    const logger = $log.getInstance('activityService');
    // public methods
    this.indexActivities = indexActivities;
    this.list = list;
    this.recalculateTotals = recalculateTotals;

    function list(params) {
      let url = '/listActivities';
      if (params.size === -1) {
        url += 'ByType';
      }
      url += addParams(params);
      logger.debug(url);
      return $http.get(url).then((result) => {
        const activities = result.data;
        enrichWithTimePerKm(activities.content);
        enrichWithStatistics(activities.content);
        return activities;
      });
    }

    function recalculateTotals(vm) {
      logger.debug('recalculateTotals');
      vm.totalActivities = vm.activities.content.length;
      vm.totalDistance = getTotalDistance(vm.activities.content);
      const totalTime = getTotalTime(vm.activities.content);
      vm.totalDuration = dateTimeUtils.formatTime(totalTime);
      vm.totalTimePerKm = dateTimeUtils.formatTime(calcTimePerKm(totalTime, vm.totalDistance));
      vm.totalTimePer5Km = dateTimeUtils.formatTime(5 * calcTimePerKm(totalTime, vm.totalDistance));
      vm.totalTimePer10Km = dateTimeUtils.formatTime(10 * calcTimePerKm(totalTime, vm.totalDistance));
    }

    const distanceHalfMarathon = 21.097;

    function enrichWithTimePerKm(content) {
      logger.debug('enrichWithTimePerKm');
      angular.forEach(content, (activity) => {
        const seconds = dateTimeUtils.formattedTimeToSeconds(activity.duration);
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
        for (let km = Math.ceil(activity.distance); km >= 1; km--) {
          if (activity.additionalInfo.length > 0) {
            activity.additionalInfo += '\n';
          }
          if (km === 21) {
            addTimeForKm(activity, seconds, distanceHalfMarathon);
            activity.additionalInfo += '\n';
          }
          addTimeForKm(activity, seconds, km);
        }
      });
    }

    function enrichWithStatistics(activities) {
      logger.debug('enrichWithStatistics');
      angular.forEach(activities, (activity) => {
        const distanceRound = Math.round(activity.distance);
        const distanceStep = Math.ceil(Math.sqrt(distanceRound));
        rateByDistance(activity, activities, distanceRound - distanceStep, distanceRound + distanceStep);
      });
    }

    function rateByDistance(activity, activities, minDistance, maxDistance) {
      const matches = activities.filter((current) => 
        current.distance > minDistance && current.distance <= maxDistance)
          .sort((o1, o2) => o1.timePerKmInSeconds - o2.timePerKmInSeconds);
      let item = 0;
      while (item < matches.length) {
        if (activity.timePerKmInSeconds <= matches[item].timePerKmInSeconds) {
          break;
        }
        item += 1;
      }
      activity.intervalStats = `Platz ${item} von ${matches.length} zwischen ${minDistance} und ${maxDistance} km`;
    }

    function addTimeForKm(activity, totalSeconds, distance) {
      const calculatedTimePerKm = calcTimePerKm(distance * totalSeconds, activity.distance);
      activity.additionalInfo += `${dateTimeUtils.formatTime(calculatedTimePerKm)}min/${distance}km`;
    }

    function calcTimePerKm(time, distance) {
      return distance !== 0 ? time / distance : 0;
    }

    function getTotalDistance(activities) {
      logger.debug('getTotalDistance');
      return _.sum(activities.map((entry) => entry.distance)).toFixed(2);
    }

    function getTotalTime(activities) {
      logger.debug('getTotalTime');
      return _.sum(activities.map((entry) => dateTimeUtils.formattedTimeToSeconds(entry.duration)));
    }

    function addParams(params) {
      logger.debug(params);
      // undefined have to be filtered
      const keys = Object.keys(params).filter((key) => typeof params[key] !== 'undefined');
      return `?${keys.map((key) => `${key}=${params[key]}`).join('&')}`;
    }

    function indexActivities() {
      $log.debug('indexActivities');
      return $http.get('/indexActivities').then((response) => response.data);
    }
  }
})();
