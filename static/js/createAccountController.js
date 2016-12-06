mainModule.controller('CreateAccountController', function ($rootScope, $scope, $http, userService) {

    initCreateAccountController();

    function initCreateAccountController() {
        console.log('loading create account controller');

        $scope.createAccount = function () {
            var creds = {};
            creds.username = $scope.credentials.username;
            creds.password1 = $scope.credentials.password1;
            creds.password2 = $scope.credentials.password2;

            if (creds.password1 === creds.password2) {
                creds.password = creds.password1;

                // passwords match and user can be saved
                userService.createUser(creds).then(function (response) {
                    if (response.data != '') {
                        console.log(response.data);
                        $scope.success = true;
                        $scope.error = false;
                    } else {
                        //show error message
                        $scope.error = true;
                        $scope.usernameAlreadyExists = true;
                    }
                });
            } else {
                $scope.error = true;
                $scope.passwordMismatch = true;
            }

        }

    }
});