hzApp.factory('fetchMapMemoryStats',  function($http,webServiceHost){
     return {
     nodeMemory: function(host,mapName) {

                var url =  webServiceHost+'/mapmemory/'+host+'/1010/'+mapName;
                return $http({
                    method: 'GET',
                    url : url
                }).
				success(function (data, status, headers, config) {
					return;
				}).
				error(function (data, status, headers, config) {
				});
			}
		}
         }); 