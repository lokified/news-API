CREATE DATABASE mynews;
\c mynews;

CREATE TABLE users(id SERIAL PRIMARY KEY, name VARCHAR, gender VARCHAR, positions VARCHAR, role VARCHAR, departmentId INT);
CREATE TABLE departments(id SERIAL PRIMARY KEY, name VARCHAR, description VARCHAR, employees INT);
CREATE TABLE news(id SERIAL PRIMARY KEY, title VARCHAR, content VARCHAR);
CREATE TABLE departments_news(id SERIAL PRIMARY KEY, departmentId INT, newsId INT);

CREATE DATABASE mynews_test WITH TEMPLATE mynews;
