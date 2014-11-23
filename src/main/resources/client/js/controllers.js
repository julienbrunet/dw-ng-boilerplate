'use strict';
var ctrl = angular.module('app.controllers', ['ngMaterial']);

ctrl.controller('SessionCtrl',
                ['$http','$rootScope','$scope','$log','$location','$cookieStore','Auth',
                function ($http, $rootScope, $scope, $log, $location, $cookieStore, Auth) {

    $log.debug("SessionCtrl");


 }]);

ctrl.controller('MainCtrl',
                ['$rootScope', '$scope', '$http', '$location', '$log', '$cookieStore', '$timeout', '$mdSidenav', 'Auth',
                function ($rootScope, $scope, $http, $location, $log, $cookieStore, $timeout, $mdSidenav, authSrv) {

    $log.debug("MainCtrl");

    $scope.toggleLeft = function() {
        $log.debug("open left");
        $mdSidenav('left').toggle();
    };
    $scope.toggleRight = function() {
        $log.debug("open right");
        $mdSidenav('right').toggle();
    };
}]);

ctrl.controller('LeftNavCtrl', function($scope, $timeout, $mdSidenav) {
  $scope.close = function() {
    $mdSidenav('left').close();
  };
})

ctrl.controller('RightNavCtrl', function($scope, $timeout, $mdSidenav) {
    $scope.close = function() {
        $mdSidenav('right').close();
    };
});

ctrl.controller('CrudCtrl',
                ['$rootScope', '$scope', '$http', '$location', '$log', '$cookieStore', 'Auth',
                function ($rootScope, $scope, $http, $location, $log, $cookieStore, authSrv) {
    $log.debug("CrudCtrl");

    $http.get('api/foo?pageSize=50')
    .then(function(response){
        $scope.foos=response.data;
        $scope.selectedFoo=$scope.foos[0];
    }, function (response){
        $scope.error=response.data;
    });

    $scope.displayDetails = function(foo) {
        $scope.selectedFoo = foo;
    };

}]);





