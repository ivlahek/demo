call docker run -d -p 15432:5432 postgres:9.6.9-alpine
call mvn clean install -DskipTests
java -jar %cd%/target/demo-0.0.1-SNAPSHOT.jar