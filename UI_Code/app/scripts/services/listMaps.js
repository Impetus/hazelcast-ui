hzApp.factory('listMaps',  function($http,webServiceHost){
     return {
     maps: function() {

                var url =  webServiceHost+'/mapsName/';
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