mainModule.controller('HomeController', function ($rootScope, $scope, $http, userService) {
    var self = this;
    $scope.showPublishers = false;
    $scope.togglePublishers = function () {
        $scope.showPublishers = !$scope.showPublishers;
    };

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

    //    $scope.partner = {};

    $scope.updatePartnerUsername = function () {
        console.log('update partner username');

        var partnerUsername = $scope.partner.username;
        userService.updatePartnerUsername($scope.user.username, partnerUsername).then(function (response) {
            if (response.data != '') {
                $scope.user = response.data;
            } else {
                //show warning message
            }
        });
    }

    $scope.logout = function () {
        $http.post('/logout', {}).finally(function () {
            $rootScope.authenticated = false;
            $location.path("/");
        });
    };
});