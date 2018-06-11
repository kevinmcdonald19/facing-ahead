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

    function updateQuestion(question){
        // return $http.post('/questions/' + id, {
        //     text: text
        // });     
        
        return $http({
            method: 'POST',
            url: '/questions/' + question.id,
            data: {
                text: question.text,
                why: question.why
            },
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    function deleteQuestion(question){

        return $http({
            method: 'DELETE',
            url: '/questions/'+ question.id
        })
    }

    function makePayment(){
        return $http({
            method: 'POST',
            url: '/checkout',
            data: {
                paymentMethod: paymentMethod
            },
            headers: {
                'Content-Type': 'application/json'
            }
        });
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
        getCategoriesAndQuestions: getCategoriesAndQuestions,
        updateQuestion: updateQuestion,
        deleteQuestion: deleteQuestion
        // updatePartnerUsername: updatePartnerUsername,
        // getResults: getResults,
        // createUser: createUser
    }
});