mainModule.controller('LoginController', function ($rootScope, $scope, $http, $location, $stateParams) {
    var local = "http://localhost:8080";

    var self = $scope;
    self.hello = 'kevin was here';

    var previousLocation = $stateParams.location;

    var authenticate = function (credentials, callback) {

        var headers = credentials ? {
            authorization: "Basic " + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('/user', {
            headers: headers
        }).then(function (response) {
            if (response.data.name) {
                $rootScope.authenticated = true;
                $rootScope.authenticationResponse = response;
                $rootScope.userInfo = response.data.principal;
            } else {
                $rootScope.authenticated = false;
            }
            callback && callback();
        }, function (response) {
            console.log(response);
            $rootScope.authenticated = false;
            callback && callback();
        });
    };

    authenticate();
    self.credentials = {};
    self.login = function () {
        authenticate(self.credentials, function () {
            if ($rootScope.authenticated) {
                if (previousLocation == null) {
                    $location.path("/home");
                } else {
                    $location.path(previousLocation);
                }
                self.error = false;
            } else {
                $location.path("/login");
                self.error = true;
            }
        });
    };

    var headers = self.credentials ? {
        authorization: "Basic " + btoa(self.credentials.username + ":" + self.credentials.password)
    } : {};

});