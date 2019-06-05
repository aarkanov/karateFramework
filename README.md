# Karate API Testing Framework

Karate is a BDD-based API Testing framework that allows for the testing of SOAP and REST requests. It allows for differing levels of comparison of the various response components along with the ability to forward header information between subsequent requests and construct more requests from response data. It also allows for call sequences to be abstracted into reusable feature files that can be called from other feature files. You can run tests using gradle commands or using the JUnit test runner within your IDE.

## Contents
* [Setting Up Framework](#markdown-header-setting-up-framework)
* [Running Tests](#markdown-header-running-tests)
* [Reports](#markdown-header-reports)
* [Framework Utility Classes](#markdown-header-framework-utility-classes)
    * [Auth Utility](#markdown-header-auth-utility)
    * [Command Line Utility](#markdown-header-command-line-utility)
    * [Date Time Utility](#markdown-header-date-time-utility)
    * [Random Generator Utility](#markdown-header-random-generator-utility)
    * [Report Utility](#markdown-header-report-utility)
* [Karate Knowledge](#markdown-header-karate-knowledge)
    * [Syntax](#markdown-header-syntax)
    * [Reading Files](#markdown-header-reading-files)
    * [Reusable Features](#markdown-header-reusable-features)
    * [Hooks](#markdown-header-hooks)
    * [Request](#markdown-header-request)
    * [Response](#markdown-header-response)
    * [Assertion Types](#markdown-header-assertion-types)
    * [Karate Documentation](#markdown-header-karate-documentation)
* [Contribution](#markdown-header-contribution)

## Setting Up Framework
1. Ensure Gradle is installed
2. Download the framework locally to extract the framework .jar file from the framework-jar folder
3. Go into folder where code will live
4. Run ```gradle init --type java-library :wrapper :init``` to generate a gradle project
5. Place the ```slalom-karate-framework-1.0.0.jar``` file into the src/main/resources directory
6. Update the build.gradle file to match the following structure
```Java
plugins {
    // Apply the java-library plugin to add support for Java Library
    id "java-library"
}

sourceCompatibility = 1.8
targetCompatibility = 1.8

repositories {
    mavenLocal()
    mavenCentral()
    maven { url "http://repo.spring.io/release" }
    maven { url "http://repo.spring.io/milestone" }
    maven { url "http://repo.spring.io/snapshot" }
}

sourceSets {
    test {
        java {
            srcDir file("src/test/java")
            exclude "**/*UiRunner*.java"
        }
        resources {
            // Using recommended karate project layout where karate feature files
            // and associated javascript resources sit in same /test/java folders
            // as their java counterparts.
            srcDir file("src/test/java")
            exclude "**/*.java"
        }
    }
}

test {
    // Enables pulling in command line arguments at runtime
    systemProperties = System.properties
    // Ensure tests are always run
    outputs.upToDateWhen { false }
}

dependencies {
    compile files("src/main/resources/slalom-karate-framework-1.0.0.jar")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api "org.apache.commons:commons-math3:3.6.1"

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation "com.google.guava:guava:27.0.1-jre"
}
```
7. Create a karate-config.js file in the src/test/java folder
    * [Karate Config](https://github.com/intuit/karate#karate-configjs)
8. Read [Recommended File Structure](https://github.com/intuit/karate#naming-conventions)
9. Start creating tests

## Running Tests
* If you have not yet done so, read [Recommended File Structure](https://github.com/intuit/karate#naming-conventions)
* There needs to be one top level java file that will serve as the overall test runner for the project. The syntax of that class can be seen below.
    * The test parallel method takes 2 optional parameters as seen in [Framework Utility Classes](#Framework-Utility-Classes)
```java
public class RunnerTest {
    @Test
    public void test() {
        ReportUtility.generateReport(ReportUtility.testParallel());
    }
}
```
* Once that class exists, test can be run using ```gradle test```
    * Subsets can be run using tags as such ```gradle test -Dkarate.options="--tags @include"```
* If you want to run test subsets using the JUnit runner in your IDE or have differing Java run configuration for test subsets, Java runner files can be created at the child level
    * Refer back to [Recommended File Structure](https://github.com/intuit/karate#naming-conventions) for more information on this approach
* It is recommended to delete src/test/reports/karate-output between runs to clean up any cached report data

## Reports
* Reports are generated automatically into src/test/reports
    * Each report will be contained in a time stamped directory with yyyy-MM-dd_HH:mm:ss format
    * It is recommended to place the src/test/reports into the .gitignore
* Open the overview-feature.html file to view the main page of the report
* Reports give feature, scenario, step and tag specific data for the test case run

## Framework Utility Classes
* All Utility classes can be imported and used as below
```java
Scenario: Auth
* def authUtility = Java.type("karate.rest.soap.testing.AuthUtility")
* def commandLineUtility = Java.type("karate.rest.soap.testing.CommandLineUtility")
* header Authorization = authUtility.basicAuthEncoding("username", "password")
* print commandLineUtility.getArg("rally")
Given url "http://blazedemo.com"
When method get
Then status 200
```

### Auth Utility
* basicAuthEncoding(): provides basic base64 encoding of credentials

### Command Line Utility
* getArg(): allows any argument passed  via the command line with -D at run time to be retrieved and used in a script

### Date Time Utility
* getFormattedDate(): returns date in yyyy-MM-dd format
* getFormattedTime(): returns time in HH:mm:ss format
* getFormattedDateTime(): returns date and time in yyyy-MM-dd_HH:mm:ss format
* getCurrentTimeInMilliseconds(): returns time since midnight January 1, 1970 UTC in milliseconds
* threadSleep(long millis): pauses the current thread x milliseconds (use with caution)

### Random Generator Utility
* generateRandomNumber(int max): generates a random number between 0 and max number (max number can be negative as well)
* generateUUID(): generates random type 4 UUID

### Report Utility
* testParallel(int? threads, String? reportDirectory): runs the tests and generates a Results object
* generateReport(Results results): consumes the Results object generated by the testParallel method to create a report

## Karate Knowledge

### Syntax
* There is a set of core keywords used within a scenario that drives the operations of Karate and allows for no step definitions to be written
    * Those keywords are url, path, request, method and status
* [Keyword Documentation](https://github.com/intuit/karate#core-keywords)

### Reading Files
* Files can be read using the read() method
    * Default expectation is that file to be read is in same directory as the calling file. If not, use classpath: to give path to file
* Read function supports .json, .xml, .yaml, .js, .csv, .txt
    * If a file is input without one of those extensions, it is treated as a stream (ex. pdf)
    * The .graphql and .gql extensions are also recognized (for GraphQL) but are handled the same way as .txt and treated as a string
* [Reading Files](https://github.com/intuit/karate#reading-files)

### Reusable Features
* The ```call``` keyword can be used in conjunction with the ```read``` keyword to call another feature file and pass parameters to it
    * Example:  ```* call read("classpath:update-results.feature") { user: "slalom" }```
* [Code Reuse](https://github.com/intuit/karate#code-reuse--common-routines)

### Hooks
* Karate is not based on Cucumber but does allow support for various hooks leveraging the karate-config, background and after keywords
    * Before All: Use karate.callSingle() in karate-config.js. Run once even when tests are run in parallel
    * Before Feature: Use callonce method in the Background
    * Before Scenario: Use the Background keyword or use karate.configure() in karate-config for global config (ex. setting connection timeout)
    * After Scenario: Use configure afterScenario method [Example](https://github.com/intuit/karate/blob/master/karate-demo/src/test/java/demo/hooks/hooks.feature)
    * After Feature: Use configure afterFeature method [Example](https://github.com/intuit/karate/blob/master/karate-demo/src/test/java/demo/hooks/hooks.feature)
* [Hooks Documentation](https://github.com/intuit/karate#hooks)

### Request
* Karate allows for many parameters withing the request to be set
    * [Single Param Keywords](https://github.com/intuit/karate#keywords-that-set-key-value-pairs)
        * | param | header | cookie | form field | multipart file | multipart field | multipart entity
    * [Multi-Param Keywords](https://github.com/intuit/karate#multi-param-keywords)
        * | params | headers | cookies | form fields | multipart files | multipart fields

### Response
* Karate allows for various parts of the response to be retrieved using built-in variables
* [Response Variables](https://github.com/intuit/karate#special-variables)
    * response | responseBytes | responseStatus | responseHeaders | responseCookies | responseTime | requestTimeStamp

### Assertion Types
* The match keyword can be used in various formats to allow for part or all of the response to be validated
    * match == | match != | match contains | match contains only | match contains any | match !contains | match each | match header
    * [Match Documentation](https://github.com/intuit/karate#match)
    * When using match contains, there is a shorthand available to make it easier to embed in expressions
        * [Match Contains Shorthand](https://github.com/intuit/karate#contains-short-cuts)
* Fuzzy matching can be used to validate values in certain parts of the response while just matching the type of other fields
    * [Fuzzy Matching](https://github.com/intuit/karate#fuzzy-matching)
* Schema validation is also available and serves as a good base test to develop against
    * [Schema Validation](https://github.com/intuit/karate#schema-validation)

### Karate Documentation
* [Table of Contents](https://github.com/intuit/karate#index)
* [Working Examples](https://github.com/intuit/karate/tree/master/karate-demo)

## Contribution
* In order to contribute to the framwork
    * Write class code
    * Write unit tests (ensure at least 85% code coverage)
        * Coverage report output to build/jacocoHtml 
        * Exceptions can be made to this rule if explanation given in pull request
    * Update framework version in build.gradle
    * Run ```gradle fatJar :compileJava :processResources :classes :fatJar``` command to compile new jar
    * Update documentation
    * Submit pull request