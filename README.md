# Karate API Testing Framework

Karate is a BDD-based API Testing framework that allows for the testing of SOAP and REST requests. It allows for differing levels of comparison of the various response components along with the ability to forward header information between subsequent requests and construct more requests from response data. It also allows for call sequences to be abstracted into reusable feature files that can be called from other feature files. You can run tests using gradle commands or using the JUnit test runner within your IDE.

## Setting Up Framework
* karate config
* build gradle with dependency

## Running Tests
* Runner classes can be set up at various levels of the project to run the files below them. A sample file structure can be seen here:
* If your tests end in Test.java, the gradle test command can be used to 

## Reports

## Framework Utility Classes

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
    * Example:  ```* call read('classpath:update-results.feature') { user: 'slalom' }```
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

### Assertion Types - Include shorthand notes
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