mainModule.controller('AdminQuestionsController', function ($rootScope, $scope, $http, userService, adminService) {
    var self = this;

    var quizCategories = [
        {
            category: 'families',
            order: 1
        },
        {
            category: 'roles',
            order: 2
        },
        { 
            category: 'finances',
            order: 3
        },
        {
            category: 'values',
            order: 4
        },
        {
            category: 'habits',
            order: 5
        },
        {
            category: 'work',
            order: 6
        },
        {
            category: 'leisure',
            order: 7
        },
        {
            category: 'intimacy',
            order: 8
        },
        { 
            category: 'community',
            order: 9
        },
        {
            category: 'communication',
            order: 10
        },
        {
            category: 'parenting',
            order: 11
        },
        {
            category: 'speaking',
            order: 12
        },
        { 
            category: 'life',
            order: 9
        }
    

    ]

    // test authentication
    $http.get('/user').then(function (response) {
        if (response.data.name) {
            $rootScope.authenticated = true;
            $rootScope.authenticationResponse = response;
            $rootScope.userInfo = response.data.principal;

            userService.getUser($rootScope.userInfo.username).then(function (response) {
                $scope.user = response.data;
                initHomeController();
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

    $scope.partner = {};
    $scope.partner.username;

    function initHomeController() {
        $scope.userFound = true;
        $scope.questions = {};
        $scope.categories = [];

        // get partner's quizResponse
      
            adminService.getCategoriesAndQuestions().then(function (response) {
                $scope.categories = response.data;

                for (var i = 0; $scope.categories.length-1; i++){
                    $scope.categories[i].category = capitalizeFirstLetter($scope.categories[i].category);
                }
            });
        
        
    }

    function capitalizeFirstLetter(string) {
        return string.charAt(0).toUpperCase() + string.slice(1);
    }


});