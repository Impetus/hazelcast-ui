var hzApp = angular.module('hzApp', ['ngRoute']);
hzApp.constant('webServiceHost','http://10.3.8.169:8080/HazelcastWebServices');
hzApp.config(['$routeProvider',
  function($routeProvider) {
    $routeProvider.
      when('/hzMaps/:mapId', {
        templateUrl: 'views/map_browser.html',
        controller: 'mapsBrowserCtrl'
        }).
      	when('/hazelcast', {
        templateUrl: 'views/home.html',
        controller: 'homeCtrl'
        });
      
      $routeProvider.otherwise("/hazelcast");
          
  }]);




 





