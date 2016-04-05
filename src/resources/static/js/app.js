(function(angular) {

	angular.module('videoManager',["videoManager.controllers","videoManager.services", "ngRoute", "ngHandsontable"]);
	angular.module("videoManager.controllers", []);
	angular.module("videoManager.services", []);

	angular.module("videoManager").config(function($routeProvider) {
		$routeProvider.when('/', {     
			redirectTo: function () {
				return "/myvideos";
			}
        }).when('/myvideos', {
            templateUrl : 'partials/myvideos.html',
            controller  : 'myVideosController'
        });
	});
})(angular);
