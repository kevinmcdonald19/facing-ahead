mainModule.controller('TopicsController', function ($rootScope, $scope, $location, $anchorScroll) {

    console.log('loading topics controller');
    $scope.scrollTo = function (id) {
        $location.hash(id);
        $anchorScroll.yOffset = 100;
        $anchorScroll();
    }

    $scope.hi = function () {
        console.log('hi');
    }

});