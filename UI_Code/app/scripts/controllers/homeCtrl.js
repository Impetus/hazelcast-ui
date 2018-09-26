hzApp.controller('homeCtrl', function ($scope, $routeParams, listmembers, fetchNodeMemory, listMaps, $rootScope) {
    $rootScope.$broadcast("destroyMember");
    $rootScope.$broadcast("destroyMap");
    $rootScope.$broadcast("destroyHome");

    $rootScope.clearMemberInfo;
    $rootScope.clearMapOnMembersInfo;

    // load memory stats for each of the node
    $scope.loadMemoryStats = function () {
        listmembers.members().then(function (response) {
            $scope.hzMembers = response.data;
            $rootScope.nodes = response.data;

            $scope.nodeMemory = [];
            for (var i = 0; i < $scope.hzMembers.length; i++) {

                var host = $scope.hzMembers[i];

                fetchNodeMemory.nodeMemory(host.split(":")[0]).then(function (response) {

                    $scope.nodeMemory.push(response.data);
                });

            }
        });
    }

    // load names of all the maps
    $scope.loadMaps = function () {
        listMaps.maps().then(function (response) {
            $rootScope.maps = response.data;
        });
    }
    $scope.loadMemoryStats();
    memoryTimer = setInterval($scope.loadMemoryStats, 30000);

    $scope.loadMaps();
    mapTimer = setInterval($scope.loadMaps, 30000);

    $rootScope.$on('destroyHome', function(event, data) {
        clearInterval(mapTimer); clearInterval(memoryTimer);
    });
});