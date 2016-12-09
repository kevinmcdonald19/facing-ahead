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
            next: 'confirmation',
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