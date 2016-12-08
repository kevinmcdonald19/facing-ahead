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
        .state('createAccount', {
            url: "/quiz/createAccount",
            templateUrl: "partials/createAccount.html",
            controller: "CreateAccountController"
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
        .state('confirmation', {
            url: "/confirmation",
            templateUrl: "partials/confirmation.html",
            controller: "ConfirmationController",

        })
        .state('login', {
            url: '/login',
            templateUrl: 'partials/login.html',
            controller: 'LoginController',
            params: {
                location: null
            }
        });

    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';

});