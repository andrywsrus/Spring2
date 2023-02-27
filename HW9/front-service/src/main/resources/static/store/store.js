angular.module('market').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const coreContextPath = 'http://localhost:5555/core/';
    const cartContextPath = 'http://localhost:5555/cart/';

    $scope.loadProducts = function (pageIndex = 1) {
         $http({
            url: coreContextPath + 'api/v1/products',
            method: 'GET',
            params: {
                page: pageIndex,
                part_title: $scope.filter ? $scope.filter.part_title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
            $scope.currentPage =  $scope.productsPage.number+1;
            $scope.productsList = response.data.content;
            $scope.filter.min_price = null,
            $scope.filter.max_price = null,
            $scope.filter.part_title = null;

        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.deleteProduct = function (productId) {
        $http({
            url: coreContextPath + 'api/v1/products',
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
            url: coreContextPath + 'api/v1/products/change_cost',
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
        $http.post(coreContextPath + 'api/v1/products', product).then(function (response) {
            $scope.product = null;
            $scope.loadProducts();
        });
    }

    $scope.addToCart = function (productId) {
        $http({
            url: cartContextPath + 'api/v1/cart/add',
            method: 'GET',
            params: {
                productId: productId,
            }
        }).then(function (response) {
        });
    }

    $scope.loadProducts();

});