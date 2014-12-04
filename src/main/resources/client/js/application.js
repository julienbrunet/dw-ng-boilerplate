'use strict';
var app = angular.module('app', [   'ui.router',
                                    'ngMaterial',
                                    'app.controllers',
                                    'app.services',
                                    'app.directives'
                                 ]);

app.constant(
    "userRoles", {
        anonymous:  1, // 001
        user:       2, // 010
        admin:      4  // 100
});

app.provider('routeAccess', ['userRoles', function(userRoles) {
    this.data = {
        public:         userRoles.anonymous | userRoles.user | userRoles.admin, // 111
        anonymous:      userRoles.anonymous, // 001
        user:           userRoles.user | userRoles.admin, // 110
        admin:          userRoles.admin // 100
    };

    this.$get = function() {
        return this.data;
    };
}]);
app.constant("currentUser", {});

app.factory('httpInterceptor', function ($q, $rootScope, $log) {
    var numLoadings = 0;
    return {
        request: function (config) {
            numLoadings++;
            // Show loader
            $rootScope.$broadcast("loader_show");
            return config || $q.when(config)
        },
        response: function (response) {
            if ((--numLoadings) === 0) {
                // Hide loader
                $rootScope.$broadcast("loader_hide");
            }
            return response || $q.when(response);
        },
        responseError: function (response) {
            if (!(--numLoadings)) {
                // Hide loader
                $rootScope.$broadcast("loader_hide");
            }
            return $q.reject(response);
        }
    };
});

//configure routes
app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', 'routeAccessProvider', function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider, routeAccessProvider) {
    $httpProvider.interceptors.push('httpInterceptor');

    var access = routeAccessProvider.data;

    // For any unmatched url, redirect to /404
    $urlRouterProvider.otherwise("/404");

    // Public routes
    $stateProvider
        .state('public', {
            abstract: true,
            template: "<ui-view/>"
        })
        .state("public.404", {
            url:         "/404",
            templateUrl: "partials/public/404.html",
            access:      access.public

        })
        .state("public.login", {
            url: "/login",
            templateUrl: "partials/public/login.html",
            controller: "SessionCtrl",
            access:      access.public
        })
        .state("public.register", {
            url: "/register",
            templateUrl: "partials/public/register.html",
            controller: "SessionCtrl",
            access:      access.public
        });

    // Regular user routes
    $stateProvider
        .state('user', {
            abstract:       true,
            template:       "<ui-view/>",
            controller:     "MainCtrl"
        })
        .state("user.home", {
            url:            "/",
            templateUrl:    "partials/user/home.html",
            controller:     "HomeCtrl",
            access:         access.user
        })
        .state("user.crud", {
            url:            "/crud",
            templateUrl:    "partials/user/crud.html",
            controller:     "CrudCtrl",
            access:         access.user
        })
        .state("user.chat", {
            url:            "/chat",
            templateUrl:    "partials/user/chat.html",
            controller:     "",
            access:         access.user

        })
        .state("user.profile", {
            url:            "/profile",
            templateUrl:    "partials/user/profile.html",
            controller: "",
            access:         access.user
        });


    // Admin routes
    $stateProvider
        .state('admin', {
            abstract: true,
            template: "<ui-view/>"
        })
        .state('admin.admin', {
            url: '/admin',
            templateUrl:    "partials/admin/admin.html",
            controller:     "AdminCtrl",
            access:         access.admin
        });
}]);

app.run(['$rootScope', '$location', '$state', 'Auth', function ($rootScope, $location, $state, Auth) {

    $rootScope.common = {};
    $rootScope.common.screen = {};
    $rootScope.common.loading = false;

    $rootScope.$on("loader_show", function () {
        $rootScope.common.loading = true;
    });
    $rootScope.$on("loader_hide", function () {
        $rootScope.common.loading = false;
    });

    $rootScope.$on("stateChangeStart", function (event, next, current) {

        if(next.title) $rootScope.common.screen.title = next.title;

        if (!Auth.authorize(next.access)) { //If not authorized to see next page
            event.preventDefault();
            if(Auth.isAuthenticated()) {
                $rootScope.error = "Unauthorized access";
                $state.go('user.home');
            }
            else {
                $state.go('public.login');
            }
        }
    });

}]);
