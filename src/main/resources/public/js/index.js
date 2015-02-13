function LoginController ($http, $location) {
	var vm = this;
	$http.get("/state").success(function(data) {
		vm.appState = data;
	});
}

angular.module("runAndFun", []).controller("LoginController", LoginController).factory();
