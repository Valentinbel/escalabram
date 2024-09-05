# Escalabram (in progress) 
## Match a rope climber
Find partners to climb with rope on rock or climbing gym.
A match will be done if someone wants to climb in the same place at the same time slot as you.

## Technical details 
SpringBoot 3.2.2, JPA, Swagger, Java 21, Maven 3.9, liquibase 17.0, PostgreSQL 16.1.

## Get Started
### Dependencies
Get sure you have installed the following: 
* Java 21
*	Maven 3.9
*	PostGreSql 16.1
*	Liquibase 17.0
* PgAdmin 4.8.2
* DBeaver or another database administration tool.

### Install
With PgAdmin, create a server group and database with the userName and Password that appears in application.properties. 

### Execute program
Execute the project on Swagger: 
On a terminal:
```
mvn spring-boot:run
```
The Swagger Interface will be avalaible on 

http://localhost:8080/swagger-ui/index.html

## Used and implemented technologies
* Junit & Mockito

## Front-end part (https://github.com/Valentinbel/escalafront)
* Angular 18 (Reactive Forms, RXJs)
* Pure CSS

## To be implemented soon
* Spring Security
* Emails/ Notifications
