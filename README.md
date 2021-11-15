# news-API
its a news API for querying and retrieving scoped news and information.
## Description
This is a rest API for querying and retrieving scoped news and information. There is news that are available to all employees and others that are  within departments. One can also create department , news and also the users.

## Installation Requirements
* Install Java Software Development (IDE) also known as IntelliJ.
* Install Gradle to run the dependencies.
* Clone the repository in your local machine.
* Open with the IDE and run it using Gradle.

## Database setup
### Type in psql.
Run these commands.
* CREATE DATABASE mynews;
* CREATE TABLE users(id SERIAL PRIMARY KEY, name VARCHAR, gender VARCHAR, positions VARCHAR, role VARCHAR, departmentId INTEGER);
* CREATE TABLE departments(id SERIAL PRIMARY KEY, name VARCHAR, description VARCHAR, employees INTEGER);
* CREATE TABLE news(id SERIAL PRIMARY KEY, title VARCHAR, content VARCHAR);
* CREATE TABLE departments_news(id SERIAL PRIMARY KEY, departmentId INTEGER, newsId INTEGER);
* CREATE DATABASE mynews_test WITH TEMPLATE mynews;

## Known Bugs
* None.

## Technologies Used
* JAVA.

## Support and contact details
Email: lsheldon645@gmail.com


### License
* MIT
Copyright (c) 2021 Sheldon OKware
