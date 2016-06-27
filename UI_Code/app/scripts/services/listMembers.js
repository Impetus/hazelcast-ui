  
  hzApp.factory('listmembers',  function($http,webServiceHost){
     return {
     members: function() {

                var url =  webServiceHost+'/clusterinfo';
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