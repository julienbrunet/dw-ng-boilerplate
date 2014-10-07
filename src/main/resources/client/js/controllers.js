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




