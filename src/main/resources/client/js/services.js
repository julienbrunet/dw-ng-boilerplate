'use strict';
var srv = angular.module('app.services', ['ngCookies']);

srv.factory('Auth', ['$rootScope', '$http', '$cookies', '$q', 'userRoles' ,'routeAccess', 'currentUser', function ($rootScope, $http, $cookies, $q, userRoles, routeAccess, currentUser) {

    //retrieve the user of give the public role
    var currentUser = $cookies.user || { username: '', role: userRoles.public };

    return {
        userRoles: userRoles,
        routeAccess: routeAccess,
        user: currentUser,

        /*
         * Return true if the current user.role is
         * authorize to display the page
         */
        authorize: function(accessLevel, role) {
            if(role === undefined)
                role = currentUser.role;
            return accessLevel &amp; role;
        },

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
            return false;
        /*
            if($http.defaults.headers.common['Auth']) return true;
            return false;
         */
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