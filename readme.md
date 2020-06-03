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

## Building and running the store service (maven and docker necessary)
You will need maven and docker installed locally on your computer to build and run the service.
Run the following script to build and run the service:

`run-script.bat` or `run-script.sh`
- will run Postgres SQL database in docker on 15432 port 
- will build the application using maven
- will run the application on a port 8080

