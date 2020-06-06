#Demo service
##Introduction
Demo service is a Spring boot application:
- listening on a port 8080 (REST API)
- uses PostgreSQL database on a port 15432
- has swagger-ui exposed on http://localhost:8080/swagger-ui.html#

Demo application is fetching the bitcoin prices in USD from the https://blockchain.info/ticker

You can control how often the application asks for the bitcoin price with the following parameters
```
scheduler.fixed.delay.milliseconds=10000
scheduler.init.delay.milliseconds=10000
```

## Project planing
The project requirements were:
- bitcoin price importing must be configurable
- latest bitcoin price should be fetched through REST API
- historical bitcoin price should be fetched through REST API

First thing I did I searched the Internet for some web service from which I could get the data about the bitcoin price.
Requirements for the external API
- it must be a REST API: because it is the easiest way to get the data from one service to another 
- REST API should not require any authentication: we don't want to add unnecessary complexity to the solution
- bitcoin price should be in dollars
I chose https://blockchain.info/ticker because it fulfills all the requirements above

The next step was the database. We need the database because:
- none of the API I searched had more than 1 month of data exposed through API
- we should not be completely dependant to an external service (if other service stops working, our service should keep working)

I chose a relational database Postgres.

REST API 
Historical data should be fetched in pages. 
Why? We do not trust clients will know how to use our service.
Someone will implement client-side paging in such a way that he is going to fetch all the data from the specified range and then do the paging. 
This would greatly downgrade our performance and would cause OutOfMemoryException.


## Building and running the demo service (maven and docker necessary)
You will need maven and docker installed locally on your computer to build and run the service.
Run the following script to build and run the service:

`run-script.bat` or `run-script.sh`
- will run Postgres SQL database in docker on 15432 port 
- will build the application using maven
- will run the application on a port 8080




