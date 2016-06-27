hzApp.controller('homeCtrl', function ($scope, $routeParams, listmembers, fetchNodeMemory) {

    $scope.loadMemoryStats = function () {
        listmembers.members().then(function (response) {
            $scope.hzMembers = response.data;

            $scope.nodeMemory = [];
            for (var i = 0; i < $scope.hzMembers.length; i++) {

                var host = $scope.hzMembers[i];

                fetchNodeMemory.nodeMemory(host.split(":")[0]).then(function (response) {

                    $scope.nodeMemory.push(response.data);
                });

            }


        });
    }
    $scope.loadMemoryStats();
    setInterval($scope.loadMemoryStats, 10000);
});