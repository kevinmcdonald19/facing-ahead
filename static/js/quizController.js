mainModule.controller('QuizController', function ($scope, $http, $state, $rootScope, $stateParams,  quizService) {

    $scope.saveingContent = true;
    initializeToggles();

    // $rootScope.$state = $state;
    // $rootScope.$stateParams = $stateParams; 
    // $scope.$route = $route;

    function testAuthentication() {
        // test authentication
        $http.get('/user').then(function (response) {
            if (response.data.name) {
                $rootScope.authenticated = true;
                $rootScope.authenticationResponse = response;
                $rootScope.userInfo = response.data.principal;
            } else {
                $rootScope.authenticated = false;
                console.log('user not authenticated');
                $state.go('login');
            }

            initPageRoute();

        }, function (response) {
            console.log(response);
            $rootScope.authenticated = false;
            $state.go('login');
            //callback && callback();
        });
    }

    function initPageRoute(targetStateName) {
        targetStateName = targetStateName || getCurrentState();
        if (targetStateName == 'quiz.families') {
            familiesPageSetup();
        } else if (targetStateName == 'quiz.roles') {
            rolesPageSetup();
        } else if (targetStateName == 'quiz.finances') {
            financesPage();
        } else if (targetStateName == 'quiz.values') {
            valuesPage();
        } else if (targetStateName == 'quiz.habits') {
            habitsPage();
        } else if (targetStateName == 'quiz.work') {
            workPage();
        } else if (targetStateName == 'quiz.leisure') {
            leisurePage();
        } else if (targetStateName == 'quiz.intimacy') {
            intimacyPage();
        } else if (targetStateName == 'quiz.community') {
            communityPage();
        } else if (targetStateName == 'quiz.communication') {
            communicationPage();
        } else if (targetStateName == 'quiz.parenting') {
            parentingPage();
        } else if (targetStateName == 'quiz.speaking') {
            speakingPage();
        } else if (targetStateName == 'quiz.life') {
            lifePage();
        }
        getQuizState();
    }

    testAuthentication();

    // ROLES
    function initializeQuestionAnswers(category) {
        //        // obtain the users's answers
        //        $scope[category] = {};
        //        $scope[category].answers = {};
        //        $scope[category].answers.radio = {};
    }

    function syncData(category, response) {
        $scope[category].answers = {};

        for (var i = 0; i < response.data.length; i++) {
            var questionAnswer = response.data[i];
            $scope[category].answers[questionAnswer.question.id] = {};
            $scope[category].answers[questionAnswer.question.id].radio = questionAnswer.answer;
            console.log('saved answer: ' + questionAnswer.answer);
            $scope.savingContent = false;
        }
    }

    /* GET THE QUESTIONS */
    function getQuestions(category) {
        quizService.getUserQuestionAnswersByCategory($rootScope.userInfo.username, category).then(function (response) {
            $scope[category] = {};
            $scope[category].questionAnswers = {};
            $scope[category].questionAnswers = response.data;
            syncData(category, response);
        });
    }

    /* SAVE THE ANSWERS */
    /* refactoring as the button groups are firing twice for some reason when the buttons are being clicked */
    $scope.saveAnswers = function (category, questionID, answer) {
        console.log('saving answers for category: ' + category + 'to: ' + answer);

        var questionAnswers = {};
        questionAnswers.updateQuestionAnswerDTOList = [];

        //        var temp = {};
        //        temp.category = category;
        //        temp.questionID = questionID;
        //        temp.answer = answer;
        //        questionAnswers.updateQuestionAnswerDTOList.push(temp);
        //
        //        quizService.saveAnswers($rootScope.userInfo.username, category, questionAnswers).then(function (response) {
        //            syncData(category, response);
        //        });

        // pick out just the values with question ID's as the key
        if ($scope[category]) {
            var answersKeys = Object.keys($scope[category].answers);
            for (var i = 0; i < answersKeys.length; i++) {
                var key = answersKeys[i];
                if (key != 'radio' && key != 'toggle') {
                    var temp = {};
                    temp.questionID = key;
                    temp.answer = $scope[category].answers[answersKeys[i]].radio;
                    questionAnswers.updateQuestionAnswerDTOList.push(temp);
                }
            }

            // submit to the server!
            if (questionAnswers.updateQuestionAnswerDTOList.length > 0) {
                $scope.savingContent = true;
                quizService.saveAnswers($rootScope.userInfo.username, category, questionAnswers).then(function (response) {
                    syncData(category, response);
                });
            } else {
                console.log('no questions to save, initializing');
            }

            console.log('done saving');
        }
    }

    // FAMILIES 
    function familiesPageSetup() {
        console.log('families page setup');
        var category = "families";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function rolesPageSetup() {
        console.log('roles page setup');
        var category = "roles";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function financesPage() {
        console.log('FINANCES page setup');
        var category = "finances";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function valuesPage() {
        console.log('Values page setup');
        var category = "values";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function habitsPage() {
        console.log('Habits page setup');
        var category = "habits";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function workPage() {
        console.log('Work page setup');
        var category = "work";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function leisurePage() {
        console.log('Leisure page setup');
        var category = "leisure";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function intimacyPage() {
        console.log('Intimacy page setup');
        var category = "intimacy";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function communityPage() {
        console.log('Community page setup');
        var category = "community";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function communicationPage() {
        console.log('Communication page setup');
        var category = "communication";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function parentingPage() {
        console.log('Parenting page setup');
        var category = "parenting";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function speakingPage() {
        console.log('Speaking page setup');
        var category = "speaking";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function lifePage() {
        console.log('Life page setup');
        var category = "life";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    $scope.goToPreviousState = function () {
        var quizState = getQuizState();
        $state.go($scope.quizState.previous).then(getQuizState);
        initPageRoute($scope.quizState.previous);
    };

    $scope.goToNextState = function () {
        var quizState = getQuizState();
        $state.go($scope.quizState.next).then(getQuizState);
        initPageRoute($scope.quizState.next);
    };

    $scope.showLearnMorePanel = false;
    $scope.togglePanel = function () {
        $scope.showLearnMorePanel = !$scope.showLearnMorePanel;
    }


    function getQuizState() {
        var urlName = $state.current.name;
        $scope.quizState = quizService.getQuizState(urlName);
        $scope.showLearnMorePanel = false;
        return $scope.quizState;
    }

    function getCurrentStateName() {
        var state = $state.current.name;
        var currentStateName = state.substring(5, state.length);
        return currentStateName;
    }

    function getCurrentState() {
        return $state.current.name;
    }
    $scope.whyContentShown = true;

    /* why is this important */
    $scope.toggleWhyContent = function (targetIndex){
        $scope.whyContentShown = !$scope.whyContentShown;
        console.log('toggle: '+ $scope.whyContentShown);
        // var section = $('target'+targetIndex);
        // section.hide();
        // // $("#kevin").show();
    }

    // $scope.panels = [
    //     {
    //       "title": "Collapsible Group Item #1",
    //       "body": "Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. 3 wolf moon officia aute, non cupidatat skateboard dolor brunch."
    //     },
    //     {
    //       "title": "Collapsible Group Item #2",
    //       "body": "Food truck fixie locavore, accusamus mcsweeney's marfa nulla single-origin coffee squid. Exercitation +1 labore velit, blog sartorial PBR leggings next level wes anderson artisan four loko farm-to-table craft beer twee."
    //     },
    //     {
    //       "title": "Collapsible Group Item #3",
    //       "body": "Etsy mixtape wayfarers, ethical wes anderson tofu before they sold out mcsweeney's organic lomo retro fanny pack lo-fi farm-to-table readymade."
    //     }
    //   ];
    //   $scope.panels.activePanel = 1;

    /* General */
    var answers = {};

    /* Storing the answers */
    function initializeToggles() {
        $scope.answers = {};
        $scope.answers.families = {
            toggle: true
        };

        $scope.answers.roles = {
            toggle: true
        };

        $scope.answers.finances = {
            toggle: true
        };

        $scope.answers.values = {
            toggle: true
        };

        $scope.answers.habits = {
            toggle: true
        };

        $scope.answers.work = {
            toggle: true
        };

        $scope.answers.leisure = {
            toggle: true
        };

        $scope.answers.intimacy = {
            toggle: true
        };

        $scope.answers.community = {
            toggle: true
        };

        $scope.answers.communication = {
            toggle: true
        };

        $scope.answers.parenting = {
            toggle: true
        };

        $scope.answers.speaking = {
            toggle: true
        };

        $scope.answers.life = {
            toggle: true
        };
    }

    $scope.savingContent = false;

});