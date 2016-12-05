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