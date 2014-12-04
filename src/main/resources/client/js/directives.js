'use strict';
var srv = angular.module('app.directives', []);

srv.directive("show-on-http-load", function ($rootScope) {
    return function ($scope, element, attrs) {
        $scope.$on("loader_show", function () {
            return element.show();
        });
        return $scope.$on("loader_hide", function () {
            return element.hide();
        });
    };
}
);

srv.directive("hide-on-http-load", function ($rootScope) {
    return function ($scope, element, attrs) {
        $scope.$on("loader_show", function () {
            return element.hide();
        });
        return $scope.$on("loader_hide", function () {
            return element.show();
        });
    };
}
);