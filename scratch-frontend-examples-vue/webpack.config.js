const applyProfile = require('webpack-profiles');
const path = require('path');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

const srcDir = path.join(__dirname, './src');
const srcMainDir = path.join(srcDir, './main');
const mainJavaScriptDir = path.join(srcMainDir, './javascript');
const outputDir = path.join(__dirname, './target/classes');

const webpack = {
  // This is the path to the core JavaScript file that will initialise the entire application.
  entry: [
    path.join(mainJavaScriptDir, './main.js')
  ],
  output: {
    // Webpack will build the code into this directory.
    path: outputDir,
    publicPath: '/'
  },

  // These are the file extensions that will be assumed for import names
  // e.g. import HelloWorld from './HelloWorld';
  // Will be assumed to have either the extension '.js' or '.jsx'.
  resolve: {
    // This alias is required so tha the Vue templating works.
    // https://forum.vuejs.org/t/vue-2-0-warn-you-are-using-the-runtime-only-build-of-vue-where-the-template-compiler-is-not-available/9429/3
    alias: {
      'vue$': 'vue/dist/vue.esm.js'
    },
    extensions: ['*', '.js']
  },

  module: {
    rules: [
      // ## Pre-loaders
      {
        // Run this loader before building the actual source code.
        enforce: 'pre',
        // This is the ESLint Webpack loader. It will check the ES6 code for any code style errors.
        loader: 'eslint-loader',
        // We will check all the JavaScript files.
        test: /\.js?$/,
        // Make sure to only lint our own source files.
        include: mainJavaScriptDir
      },
      // ## Build Loaders
      {
        // This is the ES6 Webpack loader. It will pre-process any ES6 files.
        loader: 'babel-loader',
        // We assume that all the JavaScript files contain ES6 code.
        test: /\.js?$/,
        include: mainJavaScriptDir
      },
      {
        loader: 'vue-loader',
        test: /\.vue$/,
        include: mainJavaScriptDir
      },
      {
        // This is the file loader. It is used to copy the images into the final build location. The build image paths
        // produces by this loader will then be used by the 'css' and 'sass' loaders below to set the correct values in
        // the 'url()' function calls.
        loader: 'file-loader',
        test: /\.(jpg|jpeg|png|gif|eot|otf|webp|svg|ttf|woff|woff2)/,
        query: {
          name: 'images/[name].[ext]'
        },
        include: mainJavaScriptDir
      },
      {
        // This file-loader is used specifically to copy the favicon.ico file into the root build directory.
        loader: 'file-loader',
        test: /\.ico/,
        query: {
          name: '[name].[ext]'
        },
        include: mainJavaScriptDir
      },
      {
        // This is the SASS loader. It will compile the SASS files. We have used the
        // 'extract-text-webpack-plugin' loader so that the CSS is compiled into an external file instead of
        // placed inline within the index.html.
        loader: ExtractTextPlugin.extract(['css-loader', 'sass-loader']),
        // We assume that all the SASS files end with '.scss'.
        test: /\.(scss|css)/,
        include: mainJavaScriptDir
      }
    ]
  },

  plugins: [
    // This plugin will output all the transformed CSS into a file called 'bundle.css'.
    new ExtractTextPlugin({
      filename: '/META-INF/resources/css/bundle.css',
      allChunks: true
    })
  ],
};

// It is not possible to use custom arguments in Webpack 2 so the "--profiles" webpack-profiles argument will no longer
// work. We can get around this by using the Webpack 2 "env" argument e.g. webpack --env.profiles=prod
module.exports = function (env) {
  return applyProfile(webpack, {
    profilesFilename: 'webpack.profiles.js',
    profiles: env === undefined ? '' : env.profiles
  });
};