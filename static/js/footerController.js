mainModule.controller('footerController', function ($rootScope, $scope, $http, userService, $state) {
    var self = this;

    $scope.state = $state;
    
    $scope.showPublishers = false;
    $scope.togglePublishers = function () {
        $scope.showPublishers = !$scope.showPublishers;
    };

});