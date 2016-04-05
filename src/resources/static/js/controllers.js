(function(angular, _) {
	angular.module("videoManager.controllers").controller("myVideosController", function($http, $scope, updateService, $rootScope, $log, $location) {
		
		$scope.user = "rupkumar";
		$scope.count = "";
		$scope.responseTime = "";
		$scope.model = {
				videos: [],
		}

		$scope.submit = function() {
			console.log("user =" + $scope.user);
			$http.get("/api/myvideos/" + $scope.user).then(function(response) {
				$scope.model.videos = response.data.videos;
				$scope.count = response.data.videos.length;
				$scope.responseTime = response.data.responseTime;
			});
		}
	});
	
	angular.module("videoManager.controllers").controller("headerController", function($scope, updateService, $rootScope, $log, $location) {

		  $scope.isActive = function (viewLocation) { 
			  return $location.path().includes(viewLocation);
		  };
	});
})(angular, _);