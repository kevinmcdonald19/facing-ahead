mainModule.controller('ResultsController', function ($scope, $state, $http, $rootScope, userService) {
    console.log('results controller');

    // test authentication
    $http.get('/user').then(function (response) {
        if (response.data.name) {
            $rootScope.authenticated = true;
            $rootScope.authenticationResponse = response;
            $rootScope.userInfo = response.data.principal;

            userService.getUser($rootScope.userInfo.username).then(function (response) {
                $scope.user = response.data;

                initResultsController();
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

    function initResultsController() {
        // get partner's quizResponse
        userService.getResults($scope.user.username).then(function (response) {
            $scope.results = response.data;
        });
    }

    $scope.navigateToSection = function (section) {
        $state.go('quiz.' + section.toLowerCase());
    }
});