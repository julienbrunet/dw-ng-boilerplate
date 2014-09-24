'use strict';
var srv = angular.module('app.services', ['ngCookies']);

srv.factory('authSrv', ['$rootScope', '$http', '$cookies', '$q', function ($rootScope, $http, $cookies, $q) {
    return {
        init: function (token) {
            /*
             * note : the cookie token begins and ends with " char.
             * It must be removed to work correctly with backend
             */
             if( $cookies.token ) {
                var cookieToken=$cookies.token.replace(/"/g, '');
            }
            $http.defaults.headers.common['Auth'] = token || cookieToken;


        },
        getToken: function () {
            if ($http.defaults.headers.common['Auth'])
                return $http.defaults.headers.common['Auth'];
        },
        getUserId: function () {
            return this.getToken().split("#")[0];
        },
        isAuthenticated: function () {
            if($http.defaults.headers.common['Auth']) return true;
            return false;
        },
        register: function(user) {
            var deferred = $q.defer();
                $http.post('api/auth/register', user)
            .then(function(response){
                deferred.resolve(response.data);
            }, function (response){
                deferred.reject(response);
            });
            return deferred.promise;
        },
        login: function(credentials) {
            var deferred = $q.defer();
            $http.post('api/auth/login', credentials)
            .then(function(response){
                deferred.resolve(response.data);
            }, function (response){
                deferred.reject(response);
            });
            return deferred.promise;
        },
        logout: function(userId) {
            var deferred = $q.defer();
            $http.post('api/auth/logout', userId)
            .then(function(response){
                deferred.resolve(response);
            }, function (response){
                deferred.reject(response);
            });
            return deferred.promise;
        },
        reset: function(email) {
            var deferred = $q.defer();
            $http.post('api/auth/reset', email)
            .then(function(response){
                deferred.resolve(response);
            }, function (response){
                deferred.reject(response);
            });
            return deferred.promise;
        }
    };
}]);