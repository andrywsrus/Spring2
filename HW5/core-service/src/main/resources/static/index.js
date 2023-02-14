angular.module('app', ['ngStorage']).controller('productController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/winter/api/v1';

    if ($localStorage.webMarketUser) {
        try {
            let jwt = $localStorage.webMarketUser.token;
            let payload = JSON.parse(atob(jwt.split('.')[1]));
            let currentTime = parseInt(new Date().getTime() / 1000);
            if (currentTime > payload.exp) {
                console.log("Token is expired!");
                delete $localStorage.webMarketUser;
                $http.defaults.headers.common.Authorization = '';
            }
        } catch (e) {
        }
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.webMarketUser.token;
    }

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                part_title: $scope.filter ? $scope.filter.part_title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.productsList = response.data.content;
            $scope.filter.min_price = null;
            $scope.filter.max_price = null;
            $scope.filter.part_title = null;
        });
    };

    $scope.deleteProduct = function (productId) {
        $http({
            url: contextPath + '/products',
            method: 'DELETE',
            params: {
                id: productId,
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.changePrice = function (productId, delta) {
        $http({
            url: contextPath + '/products/change_cost',
            method: 'GET',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    }

    $scope.addProduct = function (product) {
        $http.post(contextPath + '/products', product).then(function (response) {
            $scope.product = null;
            $scope.loadProducts();
        });
    }

    $scope.loadCart = function () {
        $http.get('http://localhost:8190/winter-carts/api/v1/cart')
            .then(function (response) {
                $scope.cart = response.data;
            });
    };

    $scope.addToCart = function (productId) {
        $http({
            url: 'http://localhost:8190/winter-carts/api/v1/cart/add',
            method: 'GET',
            params: {
                productId: productId,
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.deleteProductFromCart = function (productId) {
        $http({
            url: 'http://localhost:8190/winter-carts/api/v1/cart/remove/' + productId,
            method: 'GET',
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.tryToAuth = function () {
        $http.post('http://localhost:8189/winter/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.webMarketUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.webMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.webMarketUser) {
            return true;
        } else {
            return false;
        }
    };

    $scope.clearCart = function () {
        $http({
            url: 'http://localhost:8190/winter-carts/api/v1/cart/clear',
            method: 'GET',
        }).then(function (response) {
            $scope.loadCart();
        });
    }
    $scope.changeQuantity = function (productId, delta) {
        $http({
            url: 'http://localhost:8190/winter-carts/api/v1/cart/change_quantity',
            method: 'GET',
            params: {
                productId: productId,
                delta: delta
            }
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.loadOrders = function () {
        $http.get(contextPath + '/orders')
            .then(function (response) {
                $scope.myOrders = response.data;
            });
    };

    $scope.createOrder = function (productId) {
        $http({
            url: contextPath + '/orders',
            method: 'POST',
            data: $scope.orderDetails
        }).then(function (response) {
            $scope.loadOrders();
            $scope.loadCart();

            $scope.orderDetails = null
        });
    }


    $scope.loadProducts();
    $scope.loadCart();
    $scope.loadOrders();
});