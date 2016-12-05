mainModule.controller('ConfirmationController', function ($rootScope, $scope, $http, userService) {

    // test authentication
    $http.get('/user').then(function (response) {
        if (response.data.name) {
            $rootScope.authenticated = true;
            $rootScope.authenticationResponse = response;
            $rootScope.userInfo = response.data.principal;

            userService.getUser($rootScope.userInfo.username).then(function (response) {
                $scope.user = response.data;
            });
        } else {
            $rootScope.authenticated = false;
        }
        //callback && callback();
    }, function (response) {
        console.log(response);
        $rootScope.authenticated = false;
        //callback && callback();
    });
});