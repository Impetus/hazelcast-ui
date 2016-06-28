define(['angular'], function (angular) {
  'use strict';

  /**
   * @ngdoc function
   * @name hazelcastUiApp.controller:MainCtrl
   * @description
   * # MainCtrl
   * Controller of the hazelcastUiApp
   */
  angular.module('hazelcastUiApp.controllers.MainCtrl', [])
    .controller('MainCtrl', function () {
      this.awesomeThings = [
        'HTML5 Boilerplate',
        'AngularJS',
        'Karma'
      ];
    });
});
