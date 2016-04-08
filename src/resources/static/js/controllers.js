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
	
	angular.module("videoManager.controllers").controller("showVideosController", function($http, $scope, updateService, $rootScope, $log, $location) {
		
		$scope.user = "rupkumar";
		$scope.count = "";
		$scope.responseTime = "";
		$scope.model = {
				videos: [],
		}

		$scope.submit = function() {
			console.log("user =" + $scope.user);
			$http.get("/api/showvideos/" + $scope.user).then(function(response) {
				$scope.responseTime = response.data.responseTime;
				var len = response.data.videos.length;
				if (len < 1) {
					return;
				}
				var showvideos = document.getElementById("showVideos");
				var videoCount = 4;
				for(i=0; i<len; i++) {
					videoCount++;
					var rowDiv;
					if (videoCount > 4) {
						var parentDiv = document.createElement("div");
						parentDiv.setAttribute("class", "container-fluid bg-3 text-center");
						rowDiv = document.createElement("div");
						rowDiv.setAttribute("class", "row");
						parentDiv.appendChild(rowDiv);
						showvideos.appendChild(parentDiv);
						videoCount=1;
					}
					showVideo(response.data.videos[i], rowDiv);
				}
			});
		}
	});
	
	function showVideo(videoData, rowDiv) {
        var div = document.createElement("div");
        div.setAttribute("class", "col-sm-3");
        var para = document.createElement("p");
        var node = document.createTextNode(videoData.fileName);
        para.appendChild(node);
        div.appendChild(para);
        var para2 = document.createElement("p");
        var node2 = document.createTextNode(videoData.lastUpdated);
        para2.appendChild(node2);
        div.appendChild(para2);
        var video = document.createElement("video");
        video.setAttribute("class", "img-responsive");
        video.style="width:100%";
        video.controls=true;        	
        video.type = videoData.contenType;
        video.src ="http://localhost:8090/api/video/" + videoData.localVideoFileName;
        video.preload="metadata";
        div.appendChild(video);
        rowDiv.appendChild(div);
    }
	
	angular.module("videoManager.controllers").controller("uploadVideosController", function($http, $scope, Upload, updateService, $rootScope, $log, $location) {
		
		// upload later on form submit or something similar
	    $scope.submit = function() {
	      if ($scope.form.file.$valid && $scope.file) {
	        $scope.upload($scope.file);
	      }
	    };
	    
	    $scope.uploadFiles = function (files) {
	    	if (files && files.length) {
	    		for (var i = 0; i < files.length; i++) {
	    			previewImage(files[i]);
	    	        Upload.upload({
	    	            url: '/api/uploadvideos/',
	    	            data: {file: files[i]}
	    	        }).then(function (resp) {
	    	            console.log('Success ' + resp.config.data.file.name + 'uploaded. Response: ' + resp.data);
	    	        }, function (resp) {
	    	            console.log('Error status: ' + resp.status);
	    	        }, function (evt) {
	    	            var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
	    	            console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
	    	        });
	    		}
	    	}
	    }

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
	        var gallery = document.getElementById("preview");
	        var videoType = /video.*/;
	        if (!file.type.match(videoType)) {
	            throw "File Type must be a video";
	        }

	        var thumb = document.createElement("div");
	        thumb.classList.add('thumbnail');
	        var h2 = document.createElement("h2");
	        var node = document.createTextNode(file.name);
	        h2.appendChild(node);
	        thumb.appendChild(h2);
	        var para2 = document.createElement("p");
	        var node2 = document.createTextNode(file.type);
	        para2.appendChild(node2);
	        thumb.appendChild(para2);
	        
	        var para3 = document.createElement("p");
	        var node3 = document.createTextNode(humanFileSize(file.size, "MB"));
	        para3.appendChild(node3);
	        thumb.appendChild(para3);
	        
	        var video = document.createElement("video");
	        video.file = file;
	        video.type = file.type;
	        video.style="width:100%"
	        thumb.appendChild(video);
	        gallery.appendChild(thumb);

	        // Using FileReader to display the image content
	        var reader = new FileReader();
	        reader.onload = (function(aImg) { 
	        	return function(e) { 
	        		aImg.src = e.target.result; 
	        		aImg.controls=true;
	        		aImg.preload="metadata";
	        	}; 
	        })(video);
	        reader.readAsDataURL(file);
	    }
	    
	    function humanFileSize(bytes, si) {
	        var thresh = si ? 1000 : 1024;
	        if(bytes < thresh) return bytes + ' B';
	        var units = si ? ['kB','MB','GB','TB','PB','EB','ZB','YB'] : ['KiB','MiB','GiB','TiB','PiB','EiB','ZiB','YiB'];
	        var u = -1;
	        do {
	            bytes /= thresh;
	            ++u;
	        } while(bytes >= thresh);
	        return bytes.toFixed(1)+' '+units[u];
	    }
	});
	
	angular.module("videoManager.controllers").controller("headerController", function($scope, updateService, $rootScope, $log, $location) {

		  $scope.isActive = function (viewLocation) { 
			  return $location.path().includes(viewLocation);
		  };
	});
})(angular, _);