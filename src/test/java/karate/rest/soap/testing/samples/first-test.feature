Feature: Trial

# Scenario: Hit Api
#     Given url 'http://blazedemo.com'
#     When method get
#     Then status 200

Scenario: Auth
    * def authUtility = Java.type('karate.rest.soap.testing.AuthUtility')
    * def commandLineUtility = Java.type('karate.rest.soap.testing.CommandLineUtility')
    * header Authorization = authUtility.basicAuthEncoding('username', 'password')
    * print commandLineUtility.getArg('rally')
    Given url 'http://blazedemo.com'
    When method get
    Then status 200