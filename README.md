## Overview 
Main aim of the project was to create successful connection with a database to enable data input and output. Project focuses mostly on creating connection with database, proper usage of SQL syntax,  deployment of DAO and DTO patterns to enable fetching and sending data into database.

## To Run This Project
- It's necessary to deploy a mysql server locally and restore the database from the dump.sql
- Clone this repo and Install all maven plugins and dependencies.
- Change user and password from Config.json(src/test/resources/Config.json) file according to your Database Username & password.
- Run testng.xml file


## Technologies
Project is created with:
-	Java 12
-	TestNG 7.6.1
-	MySQL 8.0.31

**TC-1. Adding a new entry**

|№|Step|Expected result|
|---|---|---|
|1|Run any test (or several).|The test has completed.|
|**Postconditions**|   |   |
|1|Add a result of each completed test in the database as a new record in the corresponding table.|Information added.|

**TC-2. Processing of test data**

|   |   |   |
|---|---|---|
|**Preconditions**|   |   |
|1|Select tests from the database where ID contains two random repeating digits, for example "11" or "77". But no more than 10 tests.|   |
|2|**Copy** these tests with an indication of the **current** project and the author.|   |
|№|Step|Expected result|
|1|Simulate the launch of each tests. Update information about them in the database.|Tests are completed, information is updated.|
|**Postconditions**|   |   |
|1|Delete the records created in **Preconditions** from the database.|The records have been deleted.|












