(function(angular, SockJS, Stomp, _, undefined) {
	angular.module("videoManager.services").service("updateService", function($q, $timeout, $http, $log, $rootScope) {

				var service = {}, listener = $q.defer(), socket = {
					client : null,
					stomp : null
				}, messageIds = [];

				service.RECONNECT_TIMEOUT = 30000;
				service.SOCKET_URL = window.location.href.substring(0, window.location.href.indexOf("#")) + "/notify";
				service.myvideos = [];

				service.receive = function() {
					return listener.promise;
				};

				var reconnect = function() {
					console.log("reconnecting")
				};

				var getMessage = function(data) {
					return JSON.parse(data)
				};

				var startListener = function() {
				};

				var initialize = function() {
					socket.client = new SockJS(service.SOCKET_URL);
					socket.stomp = Stomp.over(socket.client);
					socket.stomp.connect({}, startListener, reconnect);
				};
				
				console.log("test")
				initialize();
				return service;
			});
	
	angular.module("videoManager.services").service("notificationService", function($q, $http, $log, $rootScope, updateService) {
		
		var service = {};
		
		var init = function() {
			var Notification = window.Notification || window.mozNotification || window.webkitNotification;

			Notification.requestPermission(function (permission) {
				$log.info("Permission " + permission)
			});
		}
		
		service.notify = function(title, body, icon, tag) {
			var notification = new Notification(
					title, {
						body: body,
						icon : icon,
						tag: tag
					}
				)
				notification.onclick = function() {
					window.focus();
				}
			}
		
		init();
		
		return service;
		
	});
	
})(angular, SockJS, Stomp, _);