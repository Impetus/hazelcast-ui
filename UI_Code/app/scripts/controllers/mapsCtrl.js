hzApp.controller('mapsCtrl', function($scope, $http,listMaps) {
  $scope.loadMaps = function () {
        listMaps.maps().then(function (response) {
            $scope.nodes = response.data;
        });
    }

    setInterval($scope.loadMaps, 5000);

});