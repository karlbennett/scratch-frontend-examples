scratch-simple-webapp
==============

This project demonstrates a range of features across multiple frameworks and tools including
[Maven](https://maven.apache.org/), [Spring Boot](http://projects.spring.io/spring-boot/),
[Mustache](https://mustache.github.io/), [Cucumber](https://cucumber.io/), and [Jasmin](http://jasmine.github.io/).

## Building

To build this project you must first install the [JDK](http://openjdk.java.net/install/)
([windows/MacOSX install](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)) and
[Maven](https://maven.apache.org/install.html).

Then clone the project.
```bash
git clone git@github.com:karlbennett/scratch-simple-webapp.git
```

Navigate to the project directory and run the maven build.
```bash
cd scratch-simple-webapp
mvn clean verify
```

This will compile the code, package the classes, and run all the tests.

## Running

You can run the application in three different ways.

##### Maven

```bash
cd scratch-simple-webapp
mvn clean spring-boot:run
```

##### Executable WAR

```bash
cd scratch-simple-webapp
mvn clean verify
java -jar target/scratch-simple-webapp-1.0-SNAPSHOT.war
```

##### Deploy to a Servlet Container

```bash
cd scratch-simple-webapp
mvn clean verify
```

Then deploy the WAR file contained in the `target` directory.

## Demonstrated Features

### Building

##### Spring Boot

This project shows how to use the
[Spring Boot parent pom](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-build-systems.html#using-boot-maven-parent-pom)
and [plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) to
build a
[Spring boot WAR project](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-create-a-deployable-war-file).

##### Jasmine JavaScript

The [jasmine-maven-plugin](http://searls.github.io/jasmine-maven-plugin/) has been used to execute JavaScript unit tests
during the unit test build phase.

##### Coverage

The [cobertura-maven-plugin](http://www.mojohaus.org/cobertura-maven-plugin/) has been used to check the test coverage
of the Java code and will fail the build if it ever drops below 100%.

### View Layer

##### Mustache

The [Mustache](https://mustache.github.io/) templating language has been used to generate dynamic pages. The
configuration for this has been greatly simplified by using the
[Spring Boot Mustache](https://github.com/spring-projects/spring-boot/tree/master/spring-boot-starters/spring-boot-starter-mustache)
starter pom. Adding this dependency is enough to enable and start using the Mustache view. Though the suffix of the
template files has been changed from `.html` to `.mustache` to allow better IDE integration. This configuration is in
the [`application.properties`](src/main/resources/application.properties) file.

##### SASS

The [SASS](http://sass-lang.com/) extension language has been used to simplify the CSS. It is compiled during the build
by the [sass-maven-plugin](http://www.geodienstencentrum.nl/sass-maven-plugin/plugin-info.html).

##### Intellij IDEA HTML/CSS Reloading

To get Intellij to reload modified static while running a Spring Boot app follow the instructions at
[this link](http://garywaddell.com/2015/11/20/spring-boot-intellij-idea-not-reloading-static-content/).

### Spring

##### Spring Boot

Spring Boot is used to simplify the projects dependencies and configuration. The Spring Boot plugin is also utilised to
generate an executable WAR that can be run in isolation or deployed to an existing servlet container.

##### Spring Security

Spring Security is used to add sign in functionality to the application, it is configured in the
[`SecurityConfiguration`](src/main/java/scratch/simple/webapp/SecurityConfiguration.java) class and any other additional
classes can be found under the [scratch.frontend.examples.security.spring](src/main/java/scratch/frontend/examples/services/security/) package.

##### Spring MVC

The [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html) controller,
interceptor, and argument resolver features are used for URL mapping, session management, and also to automatically sign
the user in after registration. The classes demonstrating this can be found in the
[scratch.frontend.examples.services.controller](src/main/java/scratch/frontend/examples/services/controller/) package.

##### Spring Data JPA

[Spring Data JPA](http://projects.spring.io/spring-data-jpa/) is used for the account detail persistence. It is also
integrated with Spring Security to provide the credentials for sign in. The auto-configuration features of Spring have
been used to configure the [H2](http://www.h2database.com/html/main.html) in memory database. So the database has been
completely configured by simple adding the H2DB maven dependency. Also the Spring Data auto-implementation features have
been used to create the [`UserRepository`](src/main/java/scratch/frontend/examples/services/data/UserRepository.java) so that all
that is needed to provided persistence is an interface without any actual data access code.

### Testing

##### Unit Tests

[JUnit](http://junit.org/) has been used to run all the unit tests and [Mockito](http://mockito.org/) is used to mock
class dependencies.

##### JavaScript Unit Test

[Jasmine](http://jasmine.github.io/) has been used to unit test the JavaScript code. These tests can be either run with
the normal maven build.
```bash
mvn clean test
```
Or with [Karma](https://karma-runner.github.io/0.13/index.html) that is configured with the contained
[`karma.conf.js`](karma.conf.js) file.
```bash
sudo npm install -g karma karma-jsmockito-jshamcrest karma-jasmine jasmine-core karma-chrome-launcher
karma start
```
Then open the server url in a browser and run the tests.
```bash
karma run
```
The `karma.conf.js` file can also be used to
[run and debug the tests with Intellij](https://www.jetbrains.com/idea/help/running-unit-tests-on-karma.html).

##### Acceptance Tests

[Cucumber JVM](https://cucumber.io/docs/reference/jvm)  has been used to run the acceptance tests, it has been
integrated with the Spring Boot
[integration test](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html) framework
so that the tests can be easily run by the build or an IDE.