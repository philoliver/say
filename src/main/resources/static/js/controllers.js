'use strict';

/* Controllers */

angular.module('myApp.controllers', ['ngCookies']).
controller('PostCtrl', function($scope, $http, PostService) {
	PostService.query().$promise.then(function(data) {
      $scope.posts = data;
    });

    $scope.newPost = "";

    $scope.post = function(){
    	PostService.save($scope.newPost).$promise.then(function(data) {
    		data.isNew = true;
            $scope.posts.unshift(data);
    	 });

    };
}).
controller('SubscriptionCtrl', function($scope, SubscriptionService) {
	$scope.email = "";
	$scope.hideSuccess = true;
	$scope.successEmail = "";

	$scope.subscribe = function(){
		$scope.hideSuccess = true;
		$scope.successEmail = "";

		SubscriptionService.subscribe($scope.email).then(
				function(ret){
					if(ret.data == $scope.email){
						$scope.hideSuccess = false;
						$scope.successEmail = ret.data;
					}
				}
		);
	}

}).
controller('AboutCtrl', function($scope) {

}).
controller('LoginCtrl', function($scope, $location, $cookieStore, PassphraseService) {
	$scope.passphraseWrong = false;
	$cookieStore.remove("passphrase");

	$scope.checkPassphrase = function(){
		PassphraseService.check($scope.passphrase).then(
				function( message ) {
					if( message.data == 'false' ){
						$scope.passphraseWrong = true;
					}else{
						$cookieStore.put("passphrase", $scope.passphrase);
						$location.path('/say');
					}
				}
        )
	}
}).
controller('MainCtrl', function($scope, $location, $cookies) {
	$scope.$on('$routeChangeSuccess', function () {
		if( $cookies.passphrase == undefined ){
			$location.path('/login');
		}
    });
});
