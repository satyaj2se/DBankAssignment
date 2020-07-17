- Satya Prakash Assingment 
===================================================================

Spring Boot RESTful application which can serve the following Account Support:

 * Creating a new account
 * Reading an account
 * Making a transfer between two accounts

How to run
----------
Use Eclipse oxygen that in built Gradle plugins 

Build -- from the Gradle Task - see the execution in the console if any error is not coming
BootRun - From Gradle Task ..

Code Coverage with JUnit - Has been tested with the Mockito Framework.

java -jar build/libs/Java\ Challenge-0.0.1-SNAPSHOT.jar if on the production Integration to ready with.

How to Access web service the End Point : 

http://localhost:18080/accounts/fundtransfer/v1.0/transfer

Production ready environment improvements for 
-----------------------------------------------------
1. Instead of having a concurrent hashmap, in production we could have a database for storing the accounts as well as for scalability.
2. Another advantage of using a database is that we can handle transactional atomic account updates.
3. Use of Money APIs from Java that could be added advantage to deal with transaction loss.