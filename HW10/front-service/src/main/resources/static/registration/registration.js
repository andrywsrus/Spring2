angular.module('market').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const authContextPath = 'http://localhost:5555/auth/';

    $scope.registrationUser = function () {
         $http.post(authContextPath+ 'registration', $scope.regUser).then(function (response) {
             if (response.data.token) {
                 $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                 $localStorage.webMarketUser = {username: $scope.regUser.username, token: response.data.token};
                 $localStorage.regUser = null;
                 $location.path("/")
             }
        });
    };
});