CREATE DATABASE mynews;
\c mynews;

CREATE TABLE users(id SERIAL PRIMARY KEY, name VARCHAR, gender VARCHAR, positions VARCHAR, role VARCHAR, departmentId INTEGER);
CREATE TABLE departments(id SERIAL PRIMARY KEY, name VARCHAR, description VARCHAR);
CREATE TABLE news(id SERIAL PRIMARY KEY, title VARCHAR, content VARCHAR);
CREATE TABLE departments_news(id SERIAL PRIMARY KEY, departmentId INTEGER, newsId INTEGER);

CREATE DATABASE mynews_test WITH TEMPLATE mynews;
