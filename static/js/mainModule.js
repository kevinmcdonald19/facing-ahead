mainModule.config(function ($stateProvider, $urlRouterProvider, $httpProvider) {

    $httpProvider.defaults.headers.common = {};
    $httpProvider.defaults.headers.post = {};
    $httpProvider.defaults.headers.put = {};
    $httpProvider.defaults.headers.get = {};
    $httpProvider.defaults.headers.patch = {};

    if ('addEventListener' in document) {
        document.addEventListener('DOMContentLoaded', function () {
            FastClick.attach(document.body);
        }, false);
    }

    // preventing cache
    //initialize get if not there
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};
    }

    // Answer edited to include suggestions from comments
    // because previous version of code introduced browser-related errors

    //disable IE ajax request caching
    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
    // extra
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';

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
        .state('createAccount', {
            url: "/quiz/createAccount",
            templateUrl: "partials/createAccount.html",
            controller: "CreateAccountController"
        })
        .state('confirmation.results', {
            url: "/results",
            templateUrl: "partials/results.html",
            controller: "ResultsController"
        })
        .state('confirmation.results.whatsnext', {
            url: "/whatsnext",
            templateUrl: "partials/whatsnext.html"
            // controller: "ResultsController"
        })
        .state('about', {
            url: "/about",
            templateUrl: "partials/about.html"
        })
        .state('settings', {
            url: "/settings",
            templateUrl: "partials/settings.html",
            controller: "HomeController"
        })
        .state('topics', {
            url: "/topics",
            templateUrl: "partials/topics.html",
            controller: "TopicsController"
        })
        .state('quiz', {
            url: "/quiz",
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
        .state('admin', {
            url: "/admin",
            templateUrl: "partials/admin/questions.html",
            controller: "AdminQuestionsController",
            params: {
                location: null
            }
        })
        .state('pocpayment', {
            url: "/pocpayment",
            templateUrl: "partials/pocpayment/pocpayment.html",
            controller: "POCPaymentController",
            params: {
                location: null
            }
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