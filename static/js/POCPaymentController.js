mainModule.controller('POCPaymentController', function ($rootScope, $scope, $http, userService, adminService) {
    var self = this;

    // // test authentication
    // $http.get('/user').then(function (response) {
    //     if (response.data.name) {
    //         $rootScope.authenticated = true;
    //         $rootScope.authenticationResponse = response;
    //         $rootScope.userInfo = response.data.principal;

    //         userService.getUser($rootScope.userInfo.username).then(function (response) {
    //             $scope.user = response.data;
    //             initHomeController();
    //         });
    //     } else {
    //         $rootScope.authenticated = false;
    //     }
    //     //callback && callback();
    // }, function (response) {
    //     console.log(responsÍÍe);
    //     $rootScope.authenticated = false;
    //     //callback && callback();
    //     alert('you must be logged in for this functionality');
    // });

    $scope.partner = {};
    $scope.partner.username;

    function initHomeController() {
        $scope.userFound = true;
        $scope.questions = {};
        $scope.categories = [];

        // get partner's quizResponse

        // adminService.getCategoriesAndQuestions().then(function (response) {
        //     $scope.categories = response.data;

        //     for (var i = 0; $scope.categories.length - 1; i++) {
        //         // if (category != null) {
        //             $scope.categories[i].category = capitalizeFirstLetter($scope.categories[i].category);
        //         // }
        //     }
        // });
    }

    function getToken(){
        adminService.getToken().then(function(response){
            
        });
    }

    // $scope.updateQuestion = function (question) {
    //     console.log('question: ' + question);
    //     adminService.updateQuestion(question).then(function (response, data) {
    //         console.log('updated: ' + response.data.text);
    //         // $scope.question.text = response.data.text;
    //         // initHomeController();

    //         adminService.getCategoriesAndQuestions().then(function (response) {
    //             $scope.categories = response.data;

    //             for (var i = 0; $scope.categories.length - 1; i++) {
    //                 // if (category != null) {
    //                     $scope.categories[i].category = capitalizeFirstLetter($scope.categories[i].category);
    //                 // }
    //             }
    //         });
    //     });
    // }



    // function capitalizeFirstLetter(string) {
    //     return string.charAt(0).toUpperCase() + string.slice(1);
    // }


});