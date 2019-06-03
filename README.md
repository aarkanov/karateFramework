# Karate API Testing Framework

Karate is a BDD-based API Testing framework that allows for the testing of SOAP and REST requests. It allows for differing levels of comparison of the various response components along with the ability to forward header information between subsequent requests and construct more requests from response data. It also allows for call sequences to be abstracted into reusable feature files that can be called from other feature files. You can run tests using gradle commands or using the JUnit test runner within your IDE.

## Setting Up Framework
* karate config
* build gradle with dependency

## Running Tests
* Runner classes can be set up at various levels of the project to run the files below them. A sample file structure can be seen here:
* If your tests end in Test.java, the gradle test command can be used to 

## Karate Knowledge

### Background

### Reusable Features

### Hooks

### Request

### Response

### Assertion Types - Include shorthand notes

### Utilities

### Karate Documentation
* [Table of Contents](https://github.com/intuit/karate#index)
* [Working Examples](https://github.com/intuit/karate/tree/master/karate-demo)

### Contribution
* In order to contribute to the framwork
    * Write class code
    * Write tests (ensure at least 85% code coverage)
        * Coverage report output to build/jacocoHtml 
        * Exceptions can be made to this rule if explanation given in pull request
    * Update framework version in build.gradle
    * Run ```gradle fatJar :compileJava :processResources :classes :fatJar``` command to compile new jar
    * Update documentation
    * Submit pull request