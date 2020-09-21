# OC : Parcours Java / Project 6 ( PayMyBuddy )
##### Author : **_MorganCpn_**

## Description
Money Exchange app

## Configuration

	- Java 11
	- Maven 4.0.0
	- Spring Boot 2.3.1.RELEASE
	
## Install Project

1. Import JAVA project on your computer and your IDE
2. Import PRODUCTION Database : /src/main/resources/static/database/PayMyBuddyDatabase_prod.sql
3. Change PRODUCTION DB settings (host / port / user / password) in src/main/resources/static/database/databaseConfiguration_prod.properties
**WARNING : Don't change database name if you don't change databse name in your database software**
4. Import TEST Database : /src/main/resources/static/database/PayMyBuddyDatabase_test.sql
5. Change TEST DB settings (host / port / user / password) in src/main/resources/static/database/databaseConfiguration_prod.properties
**WARNING : Don't change database name if you don't change databse name in your database software**
6. Run `mvn clean site` to generate tests, coverage, and report site
**WARNING : if you run test without import databases, tests will fail**
7. Change server port in /src/main/resources/application.properties (Default : `server.port=9090`)
8. Install mvn wrapper : `mvn -N io.takari:maven:wrapper` 
9. Run app : `./mvnw spring-boot:run`
10. Application start on `http://localhost:[your port]`

## Running App

`./mvnw spring-boot:run`

## Connect to App

Go to : `http://localhost:[your port]`
Create you an account or connect you with the demo account `user : juanita.emard@gemail.com / pwd : juanita` 

## Testing

`mvn test`

## Logs File

`/logs/PayMyBuddy.log`

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

## Database structure (UML)
![alt text](https://github.com/MrgnCpn/OC-Java-Project-6-PayMyBuddy/blob/master/Diagrams%20Database%20UML.jpg)
