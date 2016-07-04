hzApp.factory('fetchNodeMemory',  function($http,webServiceHost,jmxPort){
     return {
     nodeMemory: function(host) {

                var url =  webServiceHost+'/nodememory/'+host+'/'+jmxPort+'/';
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