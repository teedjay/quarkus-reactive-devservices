# quarkus-reactive-devservices

This project uses Quarkus and when starting up dev-mode you will have Kafka and PostgreSQL provided
for you using Quarkus dev-services.

- https://quarkus.io/guides/kafka-dev-services
- https://quarkus.io/guides/datasource#dev-services

## Dev Services

Quarkus dev-services uses TestContainers to start Kafka and PostgreSQL 
instances both for development and testing.

You can expect the following if you check what's running in Docker in dev-mode :

```shell script
❯ docker ps
CONTAINER ID   IMAGE                         
fcfe29a7c0ab   vectorized/redpanda:v21.5.5   Kafka
04ac9431e72c   postgres:13.2                 PostgreSQL
707d6b214905   testcontainers/ryuk:0.3.1     TestContainers WatchDog 
```

If you enable continuous testing you can expect a separate set of containers
for running the actual tests - like this : 

```shell script
❯ docker ps
CONTAINER ID   IMAGE
# these are for development (hot-reload)                         
fcfe29a7c0ab   vectorized/redpanda:v21.5.5   Kafka
04ac9431e72c   postgres:13.2                 PostgreSQL
707d6b214905   testcontainers/ryuk:0.3.1     TestContainers WatchDog
# these are used by tests only
973a81dd633a   vectorized/redpanda:v21.5.5   Kafka
f860bc34b461   postgres:13.2                 PostgreSQL
147ff62772d4   testcontainers/ryuk:0.3.1     TestContainers WatchDog
```

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

## Using CURL to test application

```shell script
# push new message to Kafka
curl http://localhost:8080/kafka

# messages are processed from the topic avove and the result posted to another Kafka topic

# the result is streamed from final Kafka topic using SSE
curl -N http://localhost:8080/sse
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-reactive-devservices-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.
