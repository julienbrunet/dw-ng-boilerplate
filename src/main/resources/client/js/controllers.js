'use strict';
var ctrl = angular.module('app.controllers', ['ngCookies']);

ctrl.controller('SessionCtrl',
                ['$http','$rootScope','$scope','$log','$location','$cookieStore','authSrv',
                function ($http, $rootScope, $scope, $log, $location, $cookieStore, authSrv) {

    $log.debug("SessionCtrl");

    $scope.login = function() {
        $rootScope.data.error = null;
        $rootScope.data.errorLogin = null;

        var credentials = {
            //username: $scope.email,
            //password: $scope.password?MD5_hexhash($scope.password):null
        }

        var success = function(data) {
            var token = data;
            authSrv.init(token); //Init app
            $cookieStore.put('token', token);

            $rootScope.data.isAuth = true;

            //retrieve user info
            $http.get('api/user/' + authSrv.getUserId())
            .success(function(data){
                $rootScope.data.user = data;
                if(data.lastUsedBoardId !== null){
                    $rootScope.data.currentBoardId = data.lastUsedBoardId;
                }
            }).error(function (data) {
                $rootScope.data.error("Cannot retrieve user information : " + data);
            });

            $log.info('logged in');

            $location.path('/');


        }

        var error = function(data) {
            $log.info('login error');
            $rootScope.data.errorLogin = data;
        }

        authSrv.login(credentials).then(success,error);
    };

 }]);



/* This Controller must be instanciated only one time (from the app.js) because it contains global data initialisation */
ctrl.controller('MainCtrl',
                ['$rootScope', '$scope', '$http', '$location', '$log', '$cookieStore', 'authSrv', 'boardSrv', 'userSrv',
                function ($rootScope, $scope, $http, $location, $log, $cookieStore, authSrv, boardSrv, userSrv) {
    $log.debug("MainCtrl");



}]);




