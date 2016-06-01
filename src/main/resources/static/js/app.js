'use strict';


// Declare app level module which depends on filters, and services
angular.module('myApp', [
  'ngRoute',
  'myApp.filters',
  'myApp.services',
  'myApp.controllers',
  'myApp.directives',
  'ui.bootstrap'
]).

// Configure Paths
config(['$routeProvider', function($routeProvider) {
	$routeProvider.when('/say', {templateUrl: 'partials/home.html', controller: 'PostCtrl'});
	$routeProvider.when('/subscription', {templateUrl: 'partials/subscription.html', controller: 'SubscriptionCtrl'});
	$routeProvider.when('/about', {templateUrl: 'partials/about.html', controller: 'AboutCtrl'});
	$routeProvider.when('/login', {templateUrl: 'partials/login.html', controller: 'LoginCtrl'});
    $routeProvider.otherwise({redirectTo: '/say'});
}]).

// Configure Permission Intersector
config(['$httpProvider', function($httpProvider) {

	$httpProvider.interceptors.push(function($q, $cookies) {
	    return {
	     request: function(config) {
	    	 config.headers['authorization'] = "USERLESS-PASSPH " + $cookies.passphrase;
	    	 return config || $q.when(config);
	      }
	    };
	  });

}]);
