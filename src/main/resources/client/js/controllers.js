'use strict';
var ctrl = angular.module('app.controllers', ['ngMaterial']);

ctrl.controller('SessionCtrl',
                ['$http','$rootScope','$scope','$log','$location','$cookieStore','Auth',
                function ($http, $rootScope, $scope, $log, $location, $cookieStore, Auth) {

    $log.debug("SessionCtrl");


 }]);

ctrl.controller('MainCtrl',
                ['$rootScope', '$scope', '$http', '$location', '$log', '$cookieStore', '$timeout', '$mdSidenav', '$state', 'Auth',
                function ($rootScope, $scope, $http, $location, $log, $cookieStore, $timeout, $mdSidenav, $state, authSrv) {

    $scope.toggleLeft = function() {
        $mdSidenav('left').toggle();
    };
}]);

ctrl.controller('LeftNavCtrl', function($scope, $timeout, $mdSidenav) {
    $scope.close = function() {
        $mdSidenav('left').close();
    };
})

ctrl.controller('HomeCtrl',
                ['$rootScope', '$scope', '$http', '$location', '$log', '$cookieStore', 'Auth',
                function ($rootScope, $scope, $http, $location, $log, $cookieStore, authSrv) {
    $rootScope.common.screen.title = "Home";

}]);

ctrl.controller('CrudCtrl',
                ['$rootScope', '$scope', '$http', '$location', '$log', '$cookieStore', 'Auth',
                function ($rootScope, $scope, $http, $location, $log, $cookieStore, authSrv) {
    $rootScope.common.screen.title = "CRUD Example";

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





