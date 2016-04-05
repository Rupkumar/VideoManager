(function(angular, _) {
	angular.module("videoManager.controllers").controller("myVideosController", function($http, $scope, updateService, $rootScope, $log, $timeout, $interval) {
		
		$scope.user = "rupkumar"
		$scope.responseTime = "";
		$scope.model = {
			myvideos: [],
		}
		

		$scope.submit = function() {
			console.log($scope.user);
			$http.get("/api/myvideos/" + $scope.user).then(function(response) {
				$scope.model.myvideos = response.data.myvideos;
				$scope.responseTime = response.data.responseTime;
				$scope.count = response.data.myvideos.length;
			});
		}
		
		$rootScope.$on("success", function(event, data) {
			console.log(data);
		})

	});
	
	angular.module("videoManager.controllers").controller("headerController", function($scope, updateService, $rootScope, $log, $location) {

		  $scope.isActive = function (viewLocation) { 
			  return $location.path().includes(viewLocation);
		  };
	});
})(angular, _);