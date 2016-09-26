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
            templateUrl: "partials/settings.html"
        })
        .state('quiz', {
            url: "/home/quiz",
            templateUrl: "partials/quiz.html",
            controller: "QuizController"
        })
        .state('quiz.families', {
            url: "/families",
            templateUrl: "partials/families.html"
        })
        .state('quiz.roles', {
            url: "/roles",
            templateUrl: "partials/roles.html"
        })
        .state('quiz.finances', {
            url: "/finances",
            templateUrl: "partials/finances.html"
        })
        .state('quiz.values', {
            url: "/values",
            templateUrl: "partials/values.html"
        })
        .state('quiz.habits', {
            url: "/habits",
            templateUrl: "partials/habits.html"
        })
        .state('quiz.work', {
            url: "/work",
            templateUrl: "partials/work.html"
        })
        .state('quiz.leisure', {
            url: "/leisure",
            templateUrl: "partials/leisure.html"
        })
        .state('quiz.intimacy', {
            url: "/intimacy",
            templateUrl: "partials/intimacy.html"
        })
        .state('quiz.community', {
            url: "/community",
            templateUrl: "partials/community.html"
        })
        .state('quiz.communication', {
            url: "/communication",
            templateUrl: "partials/communication.html"
        })
        .state('quiz.parenting', {
            url: "/parenting",
            templateUrl: "partials/parenting.html"
        })
        .state('quiz.speaking', {
            url: "/speaking",
            templateUrl: "partials/speaking.html"
        })
        .state('quiz.life', {
            url: "/life",
            templateUrl: "partials/life.html"
        })
        .state('quiz.confirmation', {
            url: "/confirmation",
            templateUrl: "partials/confirmation.html"
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

    function saveAnswers(answers) {
        // todo: save this to the server rather than just in cache
        this.answers = answers;
    }

    function getAnswers() {
        // todo: return these answers from the server

        return this.answers;
    }

    // service calls to get quiz questions
    var local = "http://localhost:8080";

    var base = local;

    function getQuizQuestions() {
        return $http.get(base + '/questions');
    }

    return {
        getQuizStates: getQuizStates,
        getQuizState: getQuizState,
        getNextState: getNextState,

        // service calls to get quiz questions
        getQuizQuestions: getQuizQuestions,

        saveAnswers: saveAnswers,
        getAnswers: getAnswers
    }
});

/* Controllers */
mainModule.controller('HomeController', function ($rootScope, $scope, $http) {
    var self = this;
    $scope.showPublishers = false;
    $scope.togglePublishers = function () {
        $scope.showPublishers = !$scope.showPublishers;
    };

    $http.get('http://localhost:8080/resource').then(function (data) {
        self.greeting = data;
    });

    var local = "http://localhost:8080";

    $scope.logout = function () {
        $http.post(local + '/logout', {}).finally(function () {
            $rootScope.authenticated = false;
            $location.path("/");
        });
    };
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

        $http.get(local + '/user', {
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

mainModule.controller('QuizController', function ($scope, $state, $rootScope, $stateParams, quizService) {

    if (!$rootScope.authenticated) {
        $state.go('login', {
            location: $state.current.name
        });
    } else {
        /* Navigation */
        getQuizState();

        // get questions
        quizService.getQuizQuestions().then(function (data) {
            console.log(data.data);
            $scope.questions = data.data;
        });

        $scope.goToPreviousState = function () {
            quizService.saveAnswers($scope.answers);
            $state.go($scope.quizState.previous).then(getQuizState);
        };

        $scope.goToNextState = function () {
            console.log('going to: ' + $scope.quizState.next);
            quizService.saveAnswers($scope.answers);
            $state.go($scope.quizState.next).then(getQuizState);
        };

        $scope.showLearnMorePanel = false;
        $scope.togglePanel = function () {
            $scope.showLearnMorePanel = !$scope.showLearnMorePanel;
        }

        $scope.navigateToSection = function (section) {
            $state.go(section).then(getQuizState);
        }

        function getQuizState() {
            var urlName = $state.current.name;
            $scope.quizState = quizService.getQuizState(urlName);
            console.log($scope.quizState);
            $scope.showLearnMorePanel = false;
        }

        /* General */
        var answers = {};

        /* Storing the answers */
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

mainModule.controller('ResultsController', function ($scope, quizService) {
    console.log('results controller');

    $scope.answers = quizService.getAnswers();
});