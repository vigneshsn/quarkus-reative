# Getting started with Quarkus

This is a minimal CRUD service exposing a couple of endpoints over REST.

Under the hood, this demo uses:

- RESTEasy to expose the REST endpoints
- REST-assured and JUnit 5 for endpoint testing

## Requirements

To compile and run this demo you will need:

- JDK 11+
- GraalVM

### Configuring GraalVM and JDK 11+

Make sure that both the `GRAALVM_HOME` and `JAVA_HOME` environment variables have
been set, and that a JDK 11+ `java` command is on the path.

See the [Building a Native Executable guide](https://quarkus.io/guides/building-native-image-guide)
for help setting up your environment.

## Building the application

Launch the Maven build on the checked out sources of this demo:

> ./mvnw package

### Live coding with Quarkus

The Maven Quarkus plugin provides a development mode that supports
live coding. To try this out:

> ./mvnw quarkus:dev

This command will leave Quarkus running in the foreground listening on port 8080.

1. Visit the default endpoint: [http://127.0.0.1:8080](http://127.0.0.1:8080).
    - Make a simple change to [src/main/resources/META-INF/resources/index.html](src/main/resources/META-INF/resources/index.html) file.
    - Refresh the browser to see the updated page.
2. Visit the `/hello` endpoint: [http://127.0.0.1:8080/hello](http://127.0.0.1:8080/hello)
    - Update the response in [src/main/java/org/acme/quickstart/GreetingResource.java](src/main/java/org/acme/quickstart/GreetingResource.java). Replace `hello` with `hello there` in the `hello()` method.
    - Refresh the browser. You should now see `hello there`.
    - Undo the change, so the method returns `hello` again.
    - Refresh the browser. You should now see `hello`.

### Run Quarkus in JVM mode

When you're done iterating in developer mode, you can run the application as a
conventional jar file.

First compile it:

> ./mvnw package

Then run it:

> java -jar ./target/quarkus-app/quarkus-run.jar

Have a look at how fast it boots, or measure the total native memory consumption.


### How to reproduce the issue

```agsl
curl -v -i --limit-rate 10K --max-time 2 http://localhost:8080/hello-non-reactive/greetings -H 'Content-Type:application/json' --data "@data.json"
```

This command will post the data to the endpoint with the read timeout of 2 seconds.
