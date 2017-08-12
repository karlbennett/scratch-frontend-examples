const path = require('path');

const srcTestDir = 'src/test/javascript';
const srcTestPath = path.join(__dirname, './' + srcTestDir);
const srcMainPath = path.join(__dirname, './src/main/javascript');

// Karma configuration
module.exports = function (config) {
  config.set({

    // Allow console logs to be output during test runs.
    client: {
      captureConsole: true
    },

    // Base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',

    // Frameworks to use
    // Available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine', 'jasmine-matchers'],

    // List of files / patterns to load in the browser
    files: [
      // We load the fetch and babel-polyfill scripts on startup because some browsers don't support all the ES6
      // functions.
      'node_modules/whatwg-fetch/fetch.js',
      'node_modules/babel-polyfill/dist/polyfill.js',
      // Here we give a pattern for finding the actual tests that we wish to load.
      srcTestDir + '/**/*.spec.js'
    ],

    // List of files to exclude
    exclude: [],

    // Compile the ES6 files before the tests are run.
    // Available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
      'src/test/javascript/**/*.spec.js': ['webpack']
    },

    // The configuration for the karma-webpack plugin.
    // This should be very similar to the main webpack.config.js.
    webpack: {
      node: {
        fs: 'empty'
      },
      resolve: {
        alias: {
          'vue$': 'vue/dist/vue.esm.js'
        },
        extensions: ['*', '.js', '.jsx']
      },
      module: {
        rules: [
          // ## Pre-loaders
          {
            // This loader will instrument the JavaScript to enable coverage reporting.
            enforce: 'pre',
            loader: 'isparta-loader',
            test: /\.js$/,
            include: srcMainPath
          },
          // ## Build Loaders
          {
            loader: 'babel-loader',
            test: /\.js?$/,
            include: [
              srcMainPath,
              srcTestPath
            ]
          },
          {
            loader: 'vue-loader',
            test: /\.vue$/,
            include: srcMainPath
          },
          {
            test: /\.scss$/,
            loaders: ['style-loader', 'css-loader', 'sass-loader'],
            include: srcMainPath
          },
          {
            loader: 'file-loader',
            test: /\.png|\.jpg|\.jpeg|\.gif/,
            include: srcMainPath
          },
          {
            // This loader is added so that we can handle the JSON file with the 'package.json' file within the cheerio
            // library. Unfortunately we need to compile the cheerio library into our test code to enable the Enzyme
            // test library to work.
            loader: 'json-loader',
            test: /\.json$/,
            include: srcMainPath
          }
        ]
      }
    },

    // Turn off verbose logging of webpack compilation.
    webpackMiddleware: {
      noInfo: true
    },

    // Test results reporter to use
    // Available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress', 'coverage'],

    // Set the path for the Sonar test report.
    sonarQubeUnitReporter: {
      outputFile: 'target/karma/test/ut_report.xml',
      useBrowserName: false
    },

    // Coverage reports.
    // Available reports: https://github.com/karma-runner/karma-coverage/blob/master/docs/configuration.md
    coverageReporter: {
      dir: 'target/karma/coverage/',
      reporters: [
        { type: 'text' },
        { type: 'html', subdir: 'html' },
        { type: 'lcovonly', subdir: 'lcov' },
        { type: 'cobertura', subdir: 'cobertura', file: 'cobertura.xml' }
      ]
    },

    // Web server port
    port: 9876,

    // Enable / disable colors in the output (reporters and logs)
    colors: true,

    // Level of logging
    // Possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,

    // Enable / disable watching file and executing tests whenever any file changes
    autoWatch: false,

    // Start these browsers
    // Available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['PhantomJS'],

    // Continuous Integration mode
    // If true, Karma captures browsers, runs the tests and exits
    singleRun: true
  });
};