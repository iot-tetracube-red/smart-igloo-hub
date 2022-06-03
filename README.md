# Smart Igloo Hub Project

Smart Igloo Hub is made in Quarkus and is written having scalability and high availability in mind.
These requirements are not requiring necessarily a powerful hardware, instead you can run the hub in 
a kubernetes installation in a RaspberryPi 4 where are hosted also other services (see PiHole).

## Kubernetes preparation

If you want to develop or deploy production environment of the application server you need to prepare 
the kubernetes configurations and secrets to store in the cluster itself.

### Namespace creation

The namespace is essential to isolate the services in the kubernetes cluster. For this reason
in first instance you need to deploy the namespace launching:
```shell
kubectl apply -f deployments/kubernetes/smart-igloo-hub-namespace.yaml
```

### Database deployment

The database needs secrets to protect unwanted access from external applications. For this reason
is essential to create default username and password to access and use the database itself.
To archive this you need to create the credentials by typing this command to create username:
```shell
echo -n '<insert your password here>' > ./db_password
```

Then is possible to store secrets in kubernetes cluster referencing the right namespace:
```shell
kubectl create secret generic igloo-db-storage-secrets -n smart-igloo-hub \
  --from-file=./db_password
```

You can optionally remove previously created files:
```shell
rm -v ./db_password
```

Now is time to prepare a folder where persist database's data files. This folder is used
by kubernetes to persist database's date over deployments and upgrades. So in this way is possible
upgrade, change or remove or scale database without loosing data stored in. 

To archive this, identify a folder in your system where you can create (as normal user) a folder 
and run the script *prepare_db_storage.py*:
```shell
python ./deployments/kuberenetes/prepare_db_storage.py
```


## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./gradlew quarkusDev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./gradlew build
```
It produces the `quarkus-run.jar` file in the `build/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `build/quarkus-app/lib/` directory.

The application is now runnable using `java -jar build/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./gradlew build -Dquarkus.package.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar build/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./gradlew build -Dquarkus.package.type=native
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./gradlew build -Dquarkus.package.type=native -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./build/smart-igloo-hub-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/gradle-tooling.

## Related Guides

- YAML Configuration ([guide](https://quarkus.io/guides/config#yaml)): Use YAML to configure your Quarkus application
- Kubernetes ([guide](https://quarkus.io/guides/kubernetes)): Generate Kubernetes resources from annotations
- Eclipse Vert.x ([guide](https://quarkus.io/guides/vertx)): Write reactive applications with the Vert.x API
- Hibernate Validator ([guide](https://quarkus.io/guides/validation)): Validate object properties (field, getter) and method parameters for your beans (REST, CDI, JPA)
- SmallRye Reactive Messaging ([guide](https://quarkus.io/guides/reactive-messaging)): Produce and consume messages and implement event driven and data streaming applications
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI
- Kotlin ([guide](https://quarkus.io/guides/kotlin)): Write your services in Kotlin
- Kubernetes Config ([guide](https://quarkus.io/guides/kubernetes-config)): Read runtime configuration from Kubernetes ConfigMaps and Secrets
- Reactive PostgreSQL client ([guide](https://quarkus.io/guides/reactive-sql-clients)): Connect to the PostgreSQL database using the reactive pattern

## Provided Code

### YAML Config

Configure your application with YAML

[Related guide section...](https://quarkus.io/guides/config-reference#configuration-examples)

The Quarkus application configuration is located in `src/main/resources/application.yml`.

### RESTEasy Reactive

Easily start your Reactive RESTful Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
