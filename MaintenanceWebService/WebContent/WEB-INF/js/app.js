var module=angular.module("appMain",['ngRoute']);
module.config(function($routeProvider) {
    $routeProvider
        .when('/view1', {
            templateUrl: 'crate.html',
            controller: 'CrateController'
        })
        .when('/view2', {
            templateUrl: 'view2.html',
            controller: 'SecondController'
        })
        .otherwise({
            redirectTo: '/view1'
        });
});
module.controller("FirstController",function($scope){
	
});
module.controller("FirstController",function($scope){
	
});