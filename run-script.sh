docker run -d -p 15432:5432 postgres:9.6.9-alpine
mvn clean install -DskipTests
java -jar /target/demo-0.0.1-SNAPSHOT.jar