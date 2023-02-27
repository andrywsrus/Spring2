angular.module('market').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const cartContextPath = 'http://localhost:5555/cart/';
    const coreContextPath = 'http://localhost:5555/core/';

    $scope.loadCart = function () {
        $http.get( cartContextPath +'api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };


    $scope.deleteProductFromCart = function (productId) {
        $http({
            url: cartContextPath +'api/v1/cart/remove/' + productId,
            method: 'GET',
        }).then(function (response) {
            $scope.loadCart();
        });
    }


    $scope.clearCart = function () {
        $http({
            url: cartContextPath +'api/v1/cart/clear',
            method: 'GET',
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.changeQuantity = function (productId, delta) {
        $http({
            url: cartContextPath + 'api/v1/cart/change_quantity',
            method: 'GET',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.createOrder = function (productId) {
        $http({
            url: coreContextPath + 'api/v1/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            $scope.loadCart();
            $scope.orderDetails = null
        });
    }
    $scope.loadCart();
});