hzApp.controller('mapsBrowserCtrl', function ($scope, $routeParams, $http,webServiceHost,fetchMapMemoryStats,listmembers) {
    $scope.mapId = $routeParams.mapId;
    $scope.mapKey;
    $scope.mapType = "String";
    $scope.getMapOutput = false;
   

    $scope.loadMapMemoryStats = function () {
        listmembers.members().then(function (response) {
            $scope.hzMembers = response.data;

            $scope.mapMemory = [];
            for (var i = 0; i < $scope.hzMembers.length; i++) {

                var host = $scope.hzMembers[i];

                fetchMapMemoryStats.nodeMemory(host.split(":")[0],$scope.mapId).then(function (response) {

                    $scope.mapMemory.push(response.data);
                });

            }


        });
    }
    $scope.loadMapMemoryStats();
    setInterval($scope.loadMapMemoryStats, 100000);

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