# Karate API Testing Framework

Karate is a BDD-based API Testing framework that allows for the testing of SOAP and REST requests. It allows for differing levels of comparison of the various response components along with the ability to forward header information between subsequent requests and construct more requests from response data. It also allows for call sequences to be abstracted into reusable feature files that can be called from other feature files. You can run tests using gradle commands or using the JUnit test runner within your IDE.

## Contents
* [Setting Up Framework](#markdown-header-setting-up-framework)
    * [Gradle](#markdown-header-gradle)
        * [Sample Build Gradle](#markdown-header-sample-build-gradle)
        * [Adding To Existing Build Gradle](#markdown-header-add-to-existing-build-gradle)
    * [Maven](#markdown-header-maven)
        * [Sample Pom Xml](#markdown-header-sample-pom-xml)
        * [Add To Existing Pom Xml](#markdown-header-add-to-existing-pom-xml)
* [Running Tests](#markdown-header-running-tests)
* [Reports](#markdown-header-reports)
* [Framework Utility Classes](#markdown-header-framework-utility-classes)
    * [Auth Utility](#markdown-header-auth-utility)
    * [Command Line Utility](#markdown-header-command-line-utility)
    * [Database Utility](#markdown-header-database-utility)
    * [Date Time Utility](#markdown-header-date-time-utility)
    * [Random Generator Utility](#markdown-header-random-generator-utility)
    * [Report Utility](#markdown-header-report-utility)
* [Karate Knowledge](#markdown-header-karate-knowledge)
    * [Syntax](#markdown-header-syntax)
    * [Reading Files](#markdown-header-reading-files)
    * [Reusable Features](#markdown-header-reusable-features)
    * [Database Testing](#markdown-header-database-testing)
    * [Hooks](#markdown-header-hooks)
    * [Request](#markdown-header-request)
    * [Response](#markdown-header-response)
    * [Assertion Types](#markdown-header-assertion-types)
    * [Karate Documentation](#markdown-header-karate-documentation)
* [Contribution](#markdown-header-contribution)

## Setting Up Framework

### Gradle

#### Sample Build Gradle
1. Ensure Gradle is installed
2. Download the framework locally to extract the framework .jar file from the framework-jar folder
3. Go into folder where code will live
4. Run ```gradle init --type java-library :wrapper :init``` to generate a gradle project
5. Place the ```karate-rest-soap-testing-1.1.1.jar``` file into the src/main/resources directory
6. Replace the build.gradle file with the following contents
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
    compile files("src/main/resources/karate-rest-soap-testing-1.1.1.jar")

    // This dependency is exported to consumers, that is to say found on their compile classpath.
    api "org.apache.commons:commons-math3:3.6.1"

    // This dependency is used internally, and not exposed to consumers on their own compile classpath.
    implementation "com.google.guava:guava:27.0.1-jre"
}
```
7. Run ```gradle build``` command
8. Create a karate-config.js file in the src/test/java folder
    * [Karate Config](https://github.com/intuit/karate#karate-configjs)
9. Read [Recommended File Structure](https://github.com/intuit/karate#naming-conventions)
10. Start creating tests

#### Adding To Existing Build Gradle
1. Download the framework locally to extract the framework .jar file from the framework-jar folder
2. Place the ```karate-rest-soap-testing-1.1.1.jar``` file into the src/main/resources directory
3. Add the following line to the dependencies section of the build.gradle file
    * ```compile files("src/main/resources/karate-rest-soap-testing-1.1.1.jar")```
4. Add the following lines to the sourceSets/test/resources section of the build.gradle file
    * ```srcDir file("src/test/java")```
    * ```exclude "**/*.java"```
        * See example in sample build.gradle file above
5. Run ```gradle build``` command
6. Create a karate-config.js file in the src/test/java folder
    * [Karate Config](https://github.com/intuit/karate#karate-configjs)
7. Read [Recommended File Structure](https://github.com/intuit/karate#naming-conventions)
8. Start creating tests

### Maven

#### Sample Pom Xml
1. Ensure Maven is installed
2. Go into folder where code will live
3. Run ```mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false``` to generate a maven project
    * Change groupId and artifactId to desired names
4. Create a directory called local-repo in the src/test/java directory of your new project
5. Create a directory called temp in the src/test/java directory
6. Download the framework locally to extract the framework .jar file from the framework-jar folder
7. Place the ```karate-rest-soap-testing-1.1.1.jar``` file into the src/test/java/temp directory
8. Run the command ```mvn install:install-file -Dfile=src/test/java/temp/karate-rest-soap-testing-1.1.1.jar -DgroupId=karate.rest.soap.testing -DartifactId=karate-testing  -Dversion=1.1.1 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=src/test/java/local-repo```
9. Delete temp directory
10. Replace the pom.xml file with the following contents
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>com.mycompany.app</groupId>
  <artifactId>my-app</artifactId>
  <version>1.0-SNAPSHOT</version>

  <name>my-app</name>
  <!-- FIXME change it to the project's website -->
  <url>http://www.example.com</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.7</maven.compiler.source>
    <maven.compiler.target>1.7</maven.compiler.target>
  </properties>

  <repositories>
    <repository>
      <id>localrepository</id>
      <url>file://${project.basedir}/src/test/java/local-repo</url>
    </repository>
  </repositories>

  <dependencies>
    <dependency>
        <groupId>karate.rest.soap.testing</groupId>
        <artifactId>karate-testing</artifactId>
        <version>1.1.1</version>
        <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
  </dependencies>

  <build>
    <testResources>
      <testResource>
        <directory>src/test/java</directory>
        <excludes>
            <exclude>**/*.java</exclude>
        </excludes>
      </testResource>
    </testResources>
    <pluginManagement><!-- lock down plugins versions to avoid using Maven defaults (may be moved to parent pom) -->
      <plugins>
        <!-- clean lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#clean_Lifecycle -->
        <plugin>
          <artifactId>maven-clean-plugin</artifactId>
          <version>3.1.0</version>
        </plugin>
        <!-- default lifecycle, jar packaging: see https://maven.apache.org/ref/current/maven-core/default-bindings.html#Plugin_bindings_for_jar_packaging -->
        <plugin>
          <artifactId>maven-resources-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-compiler-plugin</artifactId>
          <version>3.8.0</version>
        </plugin>
        <plugin>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>2.22.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-jar-plugin</artifactId>
          <version>3.0.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-install-plugin</artifactId>
          <version>2.5.2</version>
        </plugin>
        <plugin>
          <artifactId>maven-deploy-plugin</artifactId>
          <version>2.8.2</version>
        </plugin>
        <!-- site lifecycle, see https://maven.apache.org/ref/current/maven-core/lifecycles.html#site_Lifecycle -->
        <plugin>
          <artifactId>maven-site-plugin</artifactId>
          <version>3.7.1</version>
        </plugin>
        <plugin>
          <artifactId>maven-project-info-reports-plugin</artifactId>
          <version>3.0.0</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
</project>
```
11. Create a karate-config.js file in the src/test/java folder
    * [Karate Config](https://github.com/intuit/karate#karate-configjs)
12. Read [Recommended File Structure](https://github.com/intuit/karate#naming-conventions)
13. Start creating tests

#### Add To Existing Pom Xml
1. Create a directory called local-repo in the src/test/java directory
2. Create a directory called temp in the src/test/java directory
3. Download the framework locally to extract the framework .jar file from the framework-jar folder
4. Place the ```karate-rest-soap-testing-1.1.1.jar``` file into the src/test/java/temp directory
5. Run the command ```mvn install:install-file -Dfile=src/test/java/temp/karate-rest-soap-testing-1.1.1.jar -DgroupId=karate.rest.soap.testing -DartifactId=karate-testing  -Dversion=1.1.1 -Dpackaging=jar -DgeneratePom=true -DlocalRepositoryPath=src/test/java/local-repo```
6. Delete temp directory
7. Make the following changes to the pom.xml
    * Add the following repository to to the repositories section of the pom.xml
        ```
        <repository>
            <id>localrepository</id>
            <url>file://${project.basedir}/src/test/java/local-repo</url>
        </repository>
        ```
    * Add the following testResource to to the build/testResources section of the pom.xml
        ```
        <testResource>
            <directory>src/test/java</directory>
            <excludes>
                <exclude>**/*.java</exclude>
            </excludes>
        </testResource>
        ```
    * Add the following dependency to to the dependencies section of the pom.xml
        ```
        <dependency>
            <groupId>karate.rest.soap.testing</groupId>
            <artifactId>karate-testing</artifactId>
            <version>1.1.1</version>
            <scope>test</scope>
        </dependency>
        ```
    * See example in sample pom.xml file above
8. Run ```mvn clean install -U``` command
9. Create a karate-config.js file in the src/test/java folder
    * [Karate Config](https://github.com/intuit/karate#karate-configjs)
10. Read [Recommended File Structure](https://github.com/intuit/karate#naming-conventions)
11. Start creating tests

## Running Tests
* If you have not yet done so, read [Recommended File Structure](https://github.com/intuit/karate#naming-conventions)
* There needs to be one top level java file that will serve as the overall test runner for the project. The syntax of that class can be seen below.
    * The test parallel method takes 2 optional parameters as seen in [Framework Utility Classes](#Framework-Utility-Classes)
```Java
public class RunnerTest {
    @Test
    public void test() {
        ReportUtility.generateReport(ReportUtility.testParallel());
    }
}
```
* Once that class exists, test can be run using ```gradle test -Dtest=RunnerTest```
    * Subsets can be run using tags as such ```gradle test -Dtest=RunnerTest -Dkarate.options="--tags @include"```
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

### Database Utility
* DatabaseUtility(Map<String, Object> config): initializes the database utility class with username, password, url and driverClassName
```javascript
* def config = { username: 'qa', password: 'password', url: 'jdbc:h2:mem:testdb', driverClassName: 'org.h2.Driver' }
* def databaseUtility = Java.type('karate.rest.soap.testing.DatabaseUtility')
* def db = new DatabaseUtility(config)
```
* readValue(String query): returns a single object from a database i.e. get ID of certain row
* readRow(String query): returns a row from a database
* readRows(String query): returns multiple rows from database
* executeChangeStatement(String query): executes database updates such as INSERT, UPDATE, DELETE

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

### Database Testing
* Database queries can be executed using the [Database Utility](##markdown-header-database-utility)
* The results of SELECT queries are turned into a JSON mapping that can be asserted on using Karate's [Match](#markdown-header-assertion-types) Keyword
    * Examples: [Database Assertions](#https://github.com/intuit/karate/blob/master/karate-demo/src/test/java/demo/dogs/dogs.feature)

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