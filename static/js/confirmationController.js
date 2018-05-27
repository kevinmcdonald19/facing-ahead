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
        }
        //callback && callback();
    }, function (response) {
        console.log(response);
        $rootScope.authenticated = false;
        //callback && callback();
        alert('you must be logged in for this functionality');
    });

    function initConfirmationController() {
        $scope.navigateToSection = function (section) {
            $state.go('quiz.' + section.toLowerCase());
        }

        // get partner's quizResponse
        userService.getQuizResponse($scope.user.username).then(function (response) {
            $scope.quizResponse = response.data;            
        });
    }
});