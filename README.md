# user-administration
User Administration

## Version 1.0.1-SNAPSHOT
### Technical description
-	Spring boot v2.1.9.RELEASE
-	Java 1.8
-	Maven
-   Postgresql
-	Hibernate 5.3.12 (Spring data) 
-	Mockito and JUnit for unit tests
-   H2 database for memory integration tests
- 	API RESTfull
- 	Swagger

### General description
Management bank accounts and users.
Exist two type of users, Administrator with all privileges about users and accounts; and Basic user with only privileges about theirs accounts.
The application run in console and exits an options for to finish and exist for application

### Documentation
API documentation in line: http://localhost:8085/swagger-ui.html

### Run application
The application required java version 1.8 and postgres database.
The connection configuration is in 'application.properties' file.
For run application, only if necessary use command: java -jar ".../user-administration.jar"