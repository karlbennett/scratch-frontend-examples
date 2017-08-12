scratch-frontend-examples-vue
==============

## Building

This project can be built with either [Maven](https://maven.apache.org/) or 
[npm](https://www.npmjs.com/)/[Webpack](https://webpack.github.io/).

[Webpack profiles](https://github.com/barteksc/webpack-profiles) have been used to separate the `dev` and `prod` builds.
The profiles can be found in the [`webpack.profiles.js`](webpack.profiles.js) file.

#### Maven

To build the project with Maven just run the following command.

```bash
mvn clean verify
mvn clean verify -Pprod # Production build.
```

This will download and install [nodejs](https://nodejs.org/en/) and npm into the `target` directory, then run through 
all the commands required to build the project and run the JavaScript unit tests. So there is no need to manually 
install nodejs, npm, or any of the other JavaScript build tools.

It is also possible to run each step individually.
```
mvn "frontend:install-node-and-npm@install node and npm"
mvn "frontend:npm@npm install"
mvn "frontend:webpack@webpack build"
mvn "frontend:webpack@webpack build" -Pprod
```
  
#### npm/Webpack

If you already have nodejs, npm, Webpack, and [Karma](https://karma-runner.github.io/1.0/index.html) installed globally 
you can build the project with the following commands in the order specified.

```bash
npm install # Install all the JavaScript dependencies.
webpack     # Build the project into deployable files. These will be placed in the 'target/build' direcory.
webpack --env.profiles=prod # Production build.
karma start # Run the JavaScript unit tests.
```

You will only have to run the `npm install` command once, unless you update any of the dependencies in the 
[`package.json`](package.json) file. The `webpack` and `karma start` commands can be run independently.

## Running

You can run this project by serving the built files in the `target/build` directory. You can serve these files how ever 
you wish. One way to do this is with the [`webpack-dev-server`](https://webpack.js.org/configuration/dev-server/).

The following commands will run the `dev` Webpack build profile, which contains the webpack-dev-server configuration. 
They will start the server at [http://localhost:8081](http://localhost:8081)
 
#### Maven

If you are using Maven to build the project and haven't installed nodejs then you can run the webpack-dev-server with 
the following.

```bash
mvn frontend:npm@webpack-dev-server
```

#### nodejs

If you have nodjs installed locally then you can run the webpack-dev-server with the following.

```bash
./node_modules/.bin/webpack-dev-server --hot --inline
```

#### nodejs/webpack/webpack-dev-server

If you have all three of the tools installed locally then you can run the webpack-dev-server with the following.

```bash
webpack-dev-server --hot --inline
```

## Watching (Build)

If you're hosting the built files with something other than the webpack-dev-server and you just want to rebuild the 
source when it changes you can start a Webpack watcher.

#### Maven

```bash
mvn frontend:webpack@watch
```

#### nodjs

```bash
./node_modules/.bin/webpack --output-path ./target/classes/public --progress --colors --watch
```

#### nodejs/webpack

```bash
webpack --output-path ./target/classes/public --progress --colors --watch
```

## Watching (Test)

It is possible to have the JavaScript unit tests run whenever a file is changed using Karma.

#### Maven

```bash
mvn frontend:npm@karma-auto-watch
```

#### nodjs

```bash
./node_modules/.bin/karma start --auto-watch --no-single-run
```

#### nodejs/karma

```bash
karma start --auto-watch --no-single-run
```

## Intellij/Webstorm

For rapid development it is possible to run the 
[JavaScript unit tests with Intellij](https://www.jetbrains.com/help/idea/2017.1/preparing-to-use-karma-test-runner.html). 
To do this first install the Node.js and Karma Intellij plugins. Once that's done right click on the 
[karma.conf.js](karma.conf.js) file and select "Create 'karma.conf.js'...". This will bring up the Karma run 
configuration window, click "OK" and you're done. You can now just run the JavaScript unit tests. You do not need to 
have nodjs or Karma installed for this to work, Intellij will find them in you `node_modules` and `target` directories 
if you have already run a Maven build for this project.