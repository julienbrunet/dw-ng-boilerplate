'use strict';
var app = angular.module('app', [ 'app.controllers',
                                  'app.services',
                                  'ui.router'
                                 ]);

//configure routes
app.config(['$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider', function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {

    // For any unmatched url, redirect to /state1
    $urlRouterProvider.otherwise("/404");

    // Public routes
    $stateProvider
        .state('public', {
            abstract: true,
            template: "<ui-view/>"
        })
        .state("public.404", {
            url: "/404",
            templateUrl: "partials/public/404.html"
        })
        .state("public.root", {
            url: "/",
            templateUrl: "partials/public/login.html"
        })
        .state("public.login", {
            url: "/login",
            templateUrl: "partials/public/login.html",
            controller: "SessionCtrl"
        });

    // Regular user routes
    $stateProvider
        .state("home", {
            url: "/app",
            templateUrl: "partials/main.html",
            controller: "MainCtrl"
        });


    // Admin routes
    $stateProvider
        .state('admin', {
            abstract: true,
            template: "<ui-view/>"
        })
        .state('admin.admin', {
            url: '/admin/',
            templateUrl: 'admin',
            controller: 'AdminCtrl'
        });
}]);
