hzApp.controller('membersCtrl', function ($scope, $http, listmembers) {

    $scope.loadMembers = function () {
        listmembers.members().then(function (response) {
            $scope.nodes = response.data;
        });
    }

    setInterval($scope.loadMembers, 5000);

});