(function() {
  angular.module('runAndFun', []);

  function LoginController($http) {
    var vm = this;
    $http.get('/state').success(function(data) {
      vm.appState = data;
    });
  }

  angular.module('runAndFun').controller('LoginController', LoginController);

  function ActivityController(ActivityService) {
    var vm = this;

    // public methods
    vm.list = list;
    vm.remove = remove;

    // public fields
    vm.activities = [];
    vm.query = '';
    vm.size = 10;
    vm.totalActivities = 0;
    vm.totalDistance = 0;
    vm.totalDuration = 0;
    vm.totalTimePerKm = 0;
    vm.totalTimePer5Km = 0;
    vm.totalTimePer10Km = 0;

    // init
    list();

    function list() {
      console.debug('ActivityController.list');
      ActivityService.list(vm.size, vm.query).then(function(data) {
        vm.activities = data;
        ActivityService.recalculateTotals(vm);
      });
    }

    function remove(activity) {
      console.debug('ActivityController.remove');
      var index = vm.activities.content.indexOf(activity);
      vm.activities.content.splice(index, 1);
      ActivityService.recalculateTotals(vm);
    }
  }

  function ActivityService($http, DateTimeUtils) {
    // public methods
    this.list = list;
    this.recalculateTotals = recalculateTotals;

    function list(size, query) {
      console.debug('ActivityService.list with query: ' + query + ', size: ' + size);
      return $http.get('/listActivities?query=' + query + '&size=' + size).then(function(result) {
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

  function DateTimeUtils() {
    var vm = this;

    // public methods
    vm.formattedTimeToSeconds = formattedTimeToSeconds;
    vm.formatTime = formatTime;

    // convert '00:01:11' to 71
    function formattedTimeToSeconds(formattedTime) {
      // console.debug('formattedTimeToSeconds: ' + formattedTime);
      var tt = formattedTime.split(':');
      return tt[0] * 3600 + tt[1] * 60 + tt[2] * 1;
    }

    // convert 71 to '00:01:11'
    function formatTime(secs) {
      var hr = Math.floor(secs / 3600);
      var min = Math.floor((secs - (hr * 3600)) / 60);
      var sec = Math.floor(secs - (hr * 3600) - (min * 60));
      if (hr < 10) {
        hr = '0' + hr;
      }
      if (min < 10) {
        min = '0' + min;
      }
      if (sec < 10) {
        sec = '0' + sec;
      }
      if (hr < 1) {
        hr = '00';
      }
      return hr + ':' + min + ':' + sec;
    }
  }

  angular.module('runAndFun').controller('ActivityController', ActivityController).service('ActivityService',
      ActivityService).service('DateTimeUtils', DateTimeUtils);
})();