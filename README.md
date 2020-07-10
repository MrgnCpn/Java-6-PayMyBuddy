# OC : Parcours Java / Project 6 ( PayMyBuddy )
##### Author : **_MorganCpn_**

## Description
Backend REST API server for SafetyNet Alert apps

## Configuration

	- Java 11
	- Maven 4.0.0
	- Spring Boot 2.3.0
	
## Install Project

1. Import JAVA project on your computer and your IDE
2. Run `mvn clean site` to generate tests, coverage, and report site
3. Change server port in /src/main/resources/application.properties (Default : `server.port=8080`)
**WARNING : if you change server port and use the POSTMAN import file you must change every API urls**
4. Import in POSTMAN the import file : /PostmanImportFile
5. Install mvn wrapper : `mvn -N io.takari:maven:wrapper` 
5. Run app : `./mvnw spring-boot:run`

## Running App

`./mvnw spring-boot:run`

## Testing

`mvn test`

## Logs File

`/logs/SafetyNetAlert.log`

## Project documentation and Reports

Open in your browser : `/target/site/index.html

You may access to :
- Project informations
- Dependencies
- Licences
- Plugins 
- JaCoCo test coverage report
- Surefire  test report
- ...