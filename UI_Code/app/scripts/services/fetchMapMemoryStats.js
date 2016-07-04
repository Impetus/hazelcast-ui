hzApp.factory('fetchMapMemoryStats',  function($http,webServiceHost,jmxPort){
     return {
     nodeMemory: function(host,mapName) {

                var url =  webServiceHost+'/mapmemory/'+host+'/'+jmxPort+'/'+mapName;
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