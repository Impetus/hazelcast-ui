hzApp.factory('fetchNodeMemory',  function($http,webServiceHost){
     return {
     nodeMemory: function(host) {

                var url =  webServiceHost+'/nodememory/'+host+'/1010';
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