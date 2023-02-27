angular.module('market').controller('ordersController', function ($scope, $http, $location, $localStorage) {
    const coreContextPath = 'http://localhost:5555/core/';

    $scope.loadOrders = function () {
        $http.get(coreContextPath + 'api/v1/orders')
            .then(function (response) {
                console.log(response.data)
                $scope.myOrders = response.data;
            });
    };
    $scope.loadOrders();
});