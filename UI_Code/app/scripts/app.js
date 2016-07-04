var hzApp = angular.module('hzApp', ['ngRoute']);
hzApp.constant('webServiceHost','http://localhost:8080/HazelcastWebServices');
hzApp.constant('jmxPort','10000');
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




 





