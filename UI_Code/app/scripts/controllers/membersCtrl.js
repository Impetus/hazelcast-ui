hzApp.controller('membersCtrl', function ($scope, $routeParams, $http, fetchNodeMemory, $rootScope, listmembers, listMaps) {
    $scope.membersData = [];

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

    if($routeParams.Id) {
    	memberInfo();
    	// destroy the existing interval set for fetching member info
    	clearInterval($rootScope.clearMemberInfo);
    	$rootScope.clearMemberInfo = setInterval(function() { memberInfo(); }, 30000);
    }

	// fetch memory status for each of the node
    function memberInfo() {
    	// destroy any interval set for home controller
    	$rootScope.$broadcast("destroyHome");
        // destroy any interval set for map controller
        $rootScope.$broadcast("destroyMap");

    	var id = $routeParams;
    	fetchNodeMemory.nodeMemory(id.Id.split(":")[0]).then(function (response) {
    		$scope.membersData = [];
         	$scope.membersData.push(response.data);
      	});
    }

    $rootScope.$on('destroyMember', function(event, data) {
        clearInterval($rootScope.clearMemberInfo);
	});
});