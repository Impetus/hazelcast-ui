// Karma configuration
// http://karma-runner.github.io/0.12/config/configuration-file.html
// Generated on 2016-05-15 using
// generator-karma-require 1.1.0

module.exports = function(config) {
  'use strict';

  config.set({
    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,

    // base path, that will be used to resolve files and exclude
    basePath: '',

    // testing framework to use (jasmine/mocha/qunit/...)
    // as well as any additional frameworks (requirejs/chai/sinon/...)
    // requirejs added automatically
    frameworks: [
      &#34;jasmine&#34;,
      &#34;requirejs&#34;
    ],

    // list of files / patterns to load in the browser
    files: [
        
    ,
        // http://karma-runner.github.io/0.10/plus/requirejs.html
        '/test/test-main.js'
    ],

    // list of files / patterns to exclude
    exclude: [
    ],

    // web server port
    port: 8080,

    // Start these browsers, currently available:
    // - Chrome
    // - ChromeCanary
    // - Firefox
    // - Opera
    // - Safari (only Mac)
    // - PhantomJS
    // - IE (only Windows)
    browsers: [
      &#34;PhantomJS&#34;
    ],

    // Which plugins to enable
    plugins: [
      &#34;karma-phantomjs-launcher&#34;,
      &#34;karma-jasmine&#34;,
      &#34;karma-requirejs&#34;
    ],

    // Continuous Integration mode
    // if true, it capture browsers, run tests and exit
    singleRun: false,

    colors: true,

    // level of logging
    // possible values: LOG_DISABLE || LOG_ERROR || LOG_WARN || LOG_INFO || LOG_DEBUG
    logLevel: config.LOG_INFO,

    // Uncomment the following lines if you are using grunt's server to run the tests
    // proxies: {
    //   '/': 'http://localhost:9000/'
    // },
    // URL root prevent conflicts with the site root
    // urlRoot: '_karma_'
  });
};
