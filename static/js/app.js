'use strict';
/* Controllers */
var mainModule = angular.module('mainModule', ['ui.router', 'mgcrea.ngStrap']);
mainModule.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

    $httpProvider.defaults.headers.common = {};
    $httpProvider.defaults.headers.post = {};
    $httpProvider.defaults.headers.put = {};
    $httpProvider.defaults.headers.get = {};
    $httpProvider.defaults.headers.patch = {};

    // For any unmatched url, send to /route1
    $urlRouterProvider.otherwise("/home");
    $stateProvider
        .state('home', {
            url: "/home",
            templateUrl: "partials/home.html",
            controller: "HomeController"
        })
        .state('howItWorks', {
            url: "/home/howItWorks",
            templateUrl: "partials/howItWorks.html"
        })
        .state('topics', {
            url: "/home/topics",
            templateUrl: "partials/topics.html"
        })
        .state('results', {
            url: "/quiz/results",
            templateUrl: "partials/results.html",
            controller: "ResultsController"
        })
        .state('about', {
            url: "/home/about",
            templateUrl: "partials/about.html",
            controller: "HomeController"
        })
        .state('settings', {
            url: "/home/settings",
            templateUrl: "partials/settings.html",
            controller: "HomeController"
        })
        .state('quiz', {
            url: "/home/quiz",
            templateUrl: "partials/quiz.html",
            controller: "QuizController"
        })
        .state('quiz.families', {
            url: "/families",
            templateUrl: "partials/families.html",
            controller: "QuizController"

        })
        .state('quiz.roles', {
            url: "/roles",
            templateUrl: "partials/roles.html",
            controller: "QuizController"

        })
        .state('quiz.finances', {
            url: "/finances",
            templateUrl: "partials/finances.html",
            controller: "QuizController"

        })
        .state('quiz.values', {
            url: "/values",
            templateUrl: "partials/values.html",
            controller: "QuizController"

        })
        .state('quiz.habits', {
            url: "/habits",
            templateUrl: "partials/habits.html",
            controller: "QuizController"

        })
        .state('quiz.work', {
            url: "/work",
            templateUrl: "partials/work.html",
            controller: "QuizController"

        })
        .state('quiz.leisure', {
            url: "/leisure",
            templateUrl: "partials/leisure.html",
            controller: "QuizController"

        })
        .state('quiz.intimacy', {
            url: "/intimacy",
            templateUrl: "partials/intimacy.html",
            controller: "QuizController"

        })
        .state('quiz.community', {
            url: "/community",
            templateUrl: "partials/community.html",
            controller: "QuizController"

        })
        .state('quiz.communication', {
            url: "/communication",
            templateUrl: "partials/communication.html",
            controller: "QuizController"

        })
        .state('quiz.parenting', {
            url: "/parenting",
            templateUrl: "partials/parenting.html",
            controller: "QuizController"

        })
        .state('quiz.speaking', {
            url: "/speaking",
            templateUrl: "partials/speaking.html",
            controller: "QuizController"

        })
        .state('quiz.life', {
            url: "/life",
            templateUrl: "partials/life.html",
            controller: "QuizController"

        })
        .state('quiz.confirmation', {
            url: "/confirmation",
            templateUrl: "partials/confirmation.html",
            controller: "ConfirmationController",

        })
        .state('login', {
            url: '/login',
            templateUrl: 'partials/login.html',
            controller: 'Login',
            params: {
                location: null
            }
        });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});
/* Services */
mainModule.service('userService', function ($http) {
    function getUser(username) {
        return $http.get('/users/' + username);
    }

    function updatePartnerUsername(currentUsername, partnerUsername) {
        return $http.put('/users/' + currentUsername + '/' + partnerUsername);
    }

    function getResults(currentUsername) {
        return $http.get('/users/' + currentUsername + '/results');
    }

    return {
        getUser: getUser,
        updatePartnerUsername: updatePartnerUsername,
        getResults: getResults
    }
});

/* Controllers */
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

mainModule.controller('ConfirmationController', function ($rootScope, $scope, $http, userService) {

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


});

mainModule.controller('Login', function ($rootScope, $scope, $http, $location, $stateParams) {
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

mainModule.controller('QuizController', function ($scope, $http, $state, $rootScope, $stateParams, quizService) {

    initializeToggles();

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
            }

            initPageRoute();

        }, function (response) {
            console.log(response);
            $rootScope.authenticated = false;
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



    $scope.saveFamilies = function () {
        saveAnswers('families');
    }

    // ROLES
    function initializeQuestionAnswers(category) {
        // obtain the users's answers
        $scope[category] = {};
        $scope[category].answers = {};
        $scope[category].answers.radio = {};
    }

    function syncData(category, response) {
        $scope[category].answers = {};

        for (var i = 0; i < response.data.length; i++) {
            var questionAnswer = response.data[i];
            $scope[category].answers[questionAnswer.question.id] = {};
            $scope[category].answers[questionAnswer.question.id].radio = questionAnswer.answer;
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
    $scope.saveAnswers = function (category) {
        console.log('saving answers for category: ' + category);

        var questionAnswers = {};
        questionAnswers.updateQuestionAnswerDTOList = [];

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
            quizService.saveAnswers($rootScope.userInfo.username, category, questionAnswers).then(function (response) {
                syncData(category, response);
            });
        }
    }

    // FAMILIES 
    function familiesPageSetup() {
        console.log('families page setup');
        var category = "families";
        initializeQuestionAnswers(category);
        getQuestions(category);
    }

    function initAutoSave() {
        $scope.$watch('families.answers', function (scope) {
            $scope.saveAnswers('families');
        }, true);

        $scope.$watch('roles.answers', function (scope) {
            $scope.saveAnswers('roles');
        }, true);

        $scope.$watch('finances.answers', function (scope) {
            $scope.saveAnswers('finances');
        }, true);

        $scope.$watch('values.answers', function (scope) {
            $scope.saveAnswers('values');
        }, true);

        $scope.$watch('habits.answers', function (scope) {
            $scope.saveAnswers('habits');
        }, true);

        $scope.$watch('work.answers', function (scope) {
            $scope.saveAnswers('work');
        }, true);

        $scope.$watch('leisure.answers', function (scope) {
            $scope.saveAnswers('leisure');
        }, true);

        $scope.$watch('intimacy.answers', function (scope) {
            $scope.saveAnswers('intimacy');
        }, true);

        $scope.$watch('community.answers', function (scope) {
            $scope.saveAnswers('community');
        }, true);

        $scope.$watch('communication.answers', function (scope) {
            $scope.saveAnswers('communication');
        }, true);

        $scope.$watch('parenting.answers', function (scope) {
            $scope.saveAnswers('parenting');
        }, true);

        $scope.$watch('speaking.answers', function (scope) {
            $scope.saveAnswers('speaking');
        }, true);

        $scope.$watch('life.answers', function (scope) {
            $scope.saveAnswers('life');
        }, true);
    }

    // creates the watchers to auto-save values when chosen
    initAutoSave();


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

    $scope.navigateToSection = function (section) {
        $state.go('quiz.' + section.toLowerCase()).then(getQuizState);
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


});

mainModule.controller('ResultsController', function ($scope, $state, $http, $rootScope, userService) {
    console.log('results controller');

    // test authentication
    $http.get('/user').then(function (response) {
        if (response.data.name) {
            $rootScope.authenticated = true;
            $rootScope.authenticationResponse = response;
            $rootScope.userInfo = response.data.principal;

            userService.getUser($rootScope.userInfo.username).then(function (response) {
                $scope.user = response.data;

                initResultsController();
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

    function initResultsController() {
        // get partner's quizResponse
        userService.getResults($scope.user.username).then(function (response) {
            $scope.results = response.data;
        });
    }

    $scope.navigateToSection = function (section) {
        $state.go('quiz.' + section.toLowerCase());
    }
});

mainModule.service('quizService', function ($http) {
    var quiz = {};
    quiz.quizStates = [
        {
            state: 'quiz.families',
            next: 'quiz.roles',
            previous: null,
            order: 0,
            question: 'soething'
        },
        {
            state: 'quiz.roles',
            next: 'quiz.finances',
            previous: 'quiz.families',
            order: 1
        },
        {
            state: 'quiz.finances',
            next: 'quiz.values',
            previous: 'quiz.roles',
            order: 1
        },
        {
            state: 'quiz.values',
            next: 'quiz.habits',
            previous: 'quiz.finances',
            order: 1
        },
        {
            state: 'quiz.habits',
            next: 'quiz.work',
            previous: 'quiz.values',
            order: 1
        },
        {
            state: 'quiz.work',
            next: 'quiz.leisure',
            previous: 'quiz.habits',
            order: 1
        },
        {
            state: 'quiz.leisure',
            next: 'quiz.intimacy',
            previous: 'quiz.work',
            order: 1
        },
        {
            state: 'quiz.intimacy',
            next: 'quiz.community',
            previous: 'quiz.leisure',
            order: 1
        },
        {
            state: 'quiz.community',
            next: 'quiz.communication',
            previous: 'quiz.intimacy',
            order: 1
        },
        {
            state: 'quiz.communication',
            next: 'quiz.parenting',
            previous: 'quiz.community',
            order: 1
        },
        {
            state: 'quiz.parenting',
            next: 'quiz.speaking',
            previous: 'quiz.communication',
            order: 1
        },
        {
            state: 'quiz.speaking',
            next: 'quiz.life',
            previous: 'quiz.parenting',
            order: 1
        },
        {
            state: 'quiz.life',
            next: 'quiz.confirmation',
            previous: 'quiz.speaking',
            order: 0
        }
    ];

    function getQuizState(stateName) {
        for (var i = 0; i < quiz.quizStates.length; i++) {
            if (stateName == quiz.quizStates[i].state) {
                return quiz.quizStates[i];
            }
        }
        return null;
    }

    function getQuizStates() {
        return quiz.states;
    }

    function getNextState(state) {
        var stateObject = this.getQuizState(state.state);
        var nextStateObject = this.getQuizState(stateObject.next);
        return nextStateObject;
    }

    var answers = {};

    function saveAnswers(username, category, answers) {
        // todo: save this to the server rather than just in cache
        this.answers = answers;
        //        return $http.post('/users/' + username + '/quizResponse/questionAnswers/' + category, answers);

        return $http({
            method: 'POST',
            url: '/users/' + username + '/quizResponse/questionAnswers/' + category,
            data: answers,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    function getAnswers() {
        // todo: return these answers from the server

        return this.answers;
    }

    // service calls to get quiz questions
    var local = "http://localhost:8080";

    var base = local;

    function getQuizQuestions(category) {
        if (category) {
            return $http.get('/questions' + '/' + category);
        }
    }

    function getUserQuestionAnswersByCategory(username, category) {
        return $http.get('/users/' + username + '/quizResponse/questionAnswers/' + category);
    }

    function getUserQuizResponse(username) {
        return $http.get('/users/' + username + '/quizResponse');
    }


    return {
        getQuizStates: getQuizStates,
        getQuizState: getQuizState,
        getNextState: getNextState,

        // service calls to get quiz questions
        getQuizQuestions: getQuizQuestions,
        getUserQuestionAnswersByCategory: getUserQuestionAnswersByCategory,
        getUserQuizResponse: getUserQuizResponse,

        saveAnswers: saveAnswers,
        getAnswers: getAnswers
    }
});