var phonecatApp = angular.module('phonecatApp', []);

phonecatApp.controller('PhoneListCtrl', function ($scope, $http) {
    $http.get('http://localhost:8080/items').
        success(function(data) {
            $scope.items = data;
        });
});