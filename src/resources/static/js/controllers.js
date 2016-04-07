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
	
	angular.module("videoManager.controllers").controller("uploadVideosController", function($http, $scope, Upload, updateService, $rootScope, $log, $location) {
		
		// upload later on form submit or something similar
	    $scope.submit = function() {
	      if ($scope.form.file.$valid && $scope.file) {
	        $scope.upload($scope.file);
	      }
	    };

	    // upload on file select or drop
	    $scope.upload = function (file) {
	    	previewImage(file);
	        Upload.upload({
	            url: '/api/uploadvideos/',
	            data: {file: file}
	        }).then(function (resp) {
	            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
	        }, function (resp) {
	            console.log('Error status: ' + resp.status);
	        }, function (evt) {
	            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
	        });
	    };
	    
	    function previewImage(file) {
	        var galleryId = "gallery";

	        var gallery = document.getElementById(galleryId);
	        var videoType = /video.*/;

	        if (!file.type.match(videoType)) {
	            throw "File Type must be a video";
	        }

	        var thumb = document.createElement("div");
	        thumb.classList.add('thumbnail'); // Add the class thumbnail to the created div

	        var img = document.createElement("video");
	        img.file = file;
	        thumb.appendChild(img);
	        gallery.appendChild(thumb);

	        // Using FileReader to display the image content
	        var reader = new FileReader();
	        reader.onload = (function(aImg) { 
	        	return function(e) { 
	        		aImg.src = e.target.result; 
	        		aImg.controls=true
	        	}; 
	        })(img);
	        reader.readAsDataURL(file);
	    }
	});
	
	angular.module("videoManager.controllers").controller("headerController", function($scope, updateService, $rootScope, $log, $location) {

		  $scope.isActive = function (viewLocation) { 
			  return $location.path().includes(viewLocation);
		  };
	});
})(angular, _);