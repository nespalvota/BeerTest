# Beer Test app #

Prerequisites:
*Have Docker installed

1) Open cmd and start Mysql container:
docker run --name mysql57 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -d mysql/mysql-server:5.7

2) Create database:

docker exec -it mysql57 bash

mysql -h localhost -uroot -p1234 

CREATE USER 'demo_java' IDENTIFIED BY 'java';

grant all on *.* to 'demo_java'@'%' identified by '1234';

FLUSH PRIVILEGES;

CREATE DATABASE hello_java CHARACTER SET utf8 COLLATE utf8_general_ci;

3) (IntelliJ) Open and run project 'BeerTestApp' -> will create database tables 

4) Open Database window -> add Data Source -> MySql -> user: demo_java pass: 1234 database: hello_java

5) Upload data to the tables: drag the files from src/main/resources/data + check "First row is header" + for geocodes.csv file check that brewery_id is Unique

6) Open http://localhost:8080/greeting and insert home location.

*Might take a while for calculations to finish.*

Example (from 51.355468; 11.100790):

Visited breweries: 47.
Beers collected: 190.
Distance travelled: 1995 km.
Program took 354406 ms.
