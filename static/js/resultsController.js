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
            $state.go('login');
        }
        //callback && callback();
    }, function (response) {
        console.log(response);
        $rootScope.authenticated = false;
        $state.go('login');

        //callback && callback();
    });

    function initResultsController() {
        // get partner's quizResponse
        userService.getResults($scope.user.username).then(function (response) {
            $scope.results = response.data;

            if (response.data.partnerOne.ableToSubmitResults == '' || response.data.partnerOne.ableToSubmitResults == null) {
                $scope.ableToCompareResults = false;
            } else {
                if (response.data.partnerOne.ableToSubmitResults == true) {
                    $scope.ableToCompareResults = true;
                }
            }
        });
    }

    $scope.navigateToSection = function (section) {
        $state.go('quiz.' + section.toLowerCase());
    }
});