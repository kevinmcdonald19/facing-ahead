/* Services */
mainModule.service('adminService', function ($http) {



    function getQuestions() {
        return $http.get('/questions/');
    }

    function getQuestionsByCategory(category){
        return $http.get('/questions/' + category);
    }

    function getCategoriesAndQuestions(){
        return $http.get('/categoriesAndQuestions');
    }

    // function updatePartnerUsername(currentUsername, partnerUsername) {
    //     return $http.put('/users/' + currentUsername + '/' + partnerUsername);
    // }

    // function getResults(currentUsername) {
    //     return $http.get('/users/' + currentUsername + '/results');
    // }

    // function createUser(creds) {
    //     return $http({
    //         method: 'POST',
    //         url: 'create/user',
    //         data: creds,
    //         headers: {
    //             'Content-Type': 'application/json'
    //         }
    //     });
    // }

    return {
        getQuestions: getQuestions,
        getQuestionsByCategory: getQuestionsByCategory,
        getCategoriesAndQuestions: getCategoriesAndQuestions
        // updatePartnerUsername: updatePartnerUsername,
        // getResults: getResults,
        // createUser: createUser
    }
});