mainModule.controller('ConfirmationController', function ($rootScope, $state, $scope, $http, userService) {

    // test authentication
    $http.get('/user').then(function (response) {
        if (response.data.name) {
            $rootScope.authenticated = true;
            $rootScope.authenticationResponse = response;
            $rootScope.userInfo = response.data.principal;

            userService.getUser($rootScope.userInfo.username).then(function (response) {
                $scope.user = response.data;

                initConfirmationController();
            });
        } else {
            $rootScope.authenticated = false;
            $state.go('login');

        }
        //callback && callback();
    }, function (response) {
        console.log(response);
        $rootScope.authenticated = false;
        $state.go('login');

        //callback && callback();
    });

    if (!$rootScope.authenticated) {
        $state.go('login');
    }

    function initConfirmationController() {
        $scope.navigateToSection = function (section) {
            $state.go('quiz.' + section.toLowerCase());
        }
    }
});