hzApp.controller('mapsBrowserCtrl', function ($scope, $routeParams, $http, webServiceHost, fetchMapMemoryStats, listmembers, $rootScope, listMaps) {
    $scope.mapId = $routeParams.mapId;
    $scope.mapKey;
    $scope.mapType = "String";
    $scope.getMapOutput = false;
    $scope.mapMemory = [];

    if(!$rootScope.nodes) {
        listmembers.members().then(function (response) {
            $rootScope.nodes = response.data;
        });
    }

    if(!$rootScope.maps) {
        listMaps.maps().then(function (response) {
            $rootScope.maps = response.data;
        });
    }

    if($scope.mapId) {
        loadMapMemoryStats();
        // destroy the existing interval set for fetching member info
        clearInterval($rootScope.clearMapOnMembersInfo);
        $rootScope.clearMapOnMembersInfo = setInterval(function() { loadMapMemoryStats(); }, 30000);
    }

    function loadMapMemoryStats() {
        // destroy any interval set for home controller
        $rootScope.$broadcast("destroyHome");
        // destroy any interval set for member controller
        $rootScope.$broadcast("destroyMember");

        $scope.hzMembers = $rootScope.nodes;
        $scope.mapMemory = [];
        for (var i = 0; i < $scope.hzMembers.length; i++) {
            var host = $scope.hzMembers[i];
            fetchMapMemoryStats.nodeMemory(host.split(":")[0],$scope.mapId).then(function (response) {
                $scope.mapMemory.push(response.data);
            });
        }
    }

    $rootScope.$on('destroyMap', function(event, data) {
        clearInterval($rootScope.clearMapOnMembersInfo);
    });

    $scope.value = function () {
        
        $scope.webout = '';
        $scope.weboutsize = '';
        $scope.getMapSize = false;
        
        var callURL = webServiceHost+'/getValue/' + $routeParams.mapId + '/' + $scope.mapKey + '/' + $scope.mapType;
        $http({
            method: "GET",
            url: callURL

        }).then(function mySucces(response) {
            $scope.webout = response.data;
            console.log($scope.webout);
            $scope.getMapOutput = true;
        }, function myError(response) {
            $scope.webout = response.statusText;
        }); 
    };

   
    $scope.clear = function () {
        $scope.webout = '';
        $scope.weboutsize = '';
        $scope.getMapSize = false;
        $scope.mapKey = '';
        
    };

});