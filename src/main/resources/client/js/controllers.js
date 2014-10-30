'use strict';
var ctrl = angular.module('app.controllers', ['ngCookies']);

ctrl.controller('SessionCtrl',
                ['$http','$rootScope','$scope','$log','$location','$cookieStore','Auth',
                function ($http, $rootScope, $scope, $log, $location, $cookieStore, Auth) {

    $log.debug("SessionCtrl");


 }]);

ctrl.controller('MainCtrl',
                ['$rootScope', '$scope', '$http', '$location', '$log', '$cookieStore', 'Auth',
                function ($rootScope, $scope, $http, $location, $log, $cookieStore, authSrv) {

    $log.debug("MainCtrl");
}]);

ctrl.controller('CrudCtrl',
                ['$rootScope', '$scope', '$http', '$location', '$log', '$cookieStore', 'Auth',
                function ($rootScope, $scope, $http, $location, $log, $cookieStore, authSrv) {
    $log.debug("CrudCtrl");

    $http.get('api/foo')
    .then(function(response){
        $scope.foos=response.data;
        $log.info(response);
    }, function (response){
        $scope.error=response.data;
    });

}]);





