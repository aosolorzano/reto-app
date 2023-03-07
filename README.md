## Aplicacion Nativa con Spring Native, WebFlux y TDD.

* **Autor**: [Andres Solorzano](https://www.linkedin.com/in/aosolorzano/).
* **Nivel**: Intermedio.
* **Tecnologías**: Java, Spring Boot, Spring Native, Spring WebFlux, Flyway, Testcontainers, Postgres and Docker.

---

## Description
This project uses the Spring Boot Framework to handle errors in a Spring Native application that uses WebFlux and i18n.
The idea is to use a TDD methodology from the beginning with the support of Testcontainers to execute the Integration Tests.
This project also uses Docker with the compose plugin to deploy a local cluster alongside the other required services by the Spring Boot microservice.

### Running the application using Docker Compose
Execute the following command from the root of the project:
```bash
docker compose up --build
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/html/#build-image)
* [GraalVM Native Image Support](https://docs.spring.io/spring-boot/docs/3.0.4/reference/html/native-image.html#native-image)
* [Testcontainers Postgres Module Reference Guide](https://www.testcontainers.org/modules/databases/postgres/)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#web.reactive)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#data.sql.jpa-and-spring-data)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#howto.data-initialization.migration-tool.flyway)
* [Validation](https://docs.spring.io/spring-boot/docs/3.0.4/reference/htmlsingle/#io.validation)
* [Testcontainers](https://www.testcontainers.org/)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)
* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)

### Additional Links
These additional references should also help you:

* [Configure AOT settings in Build Plugin](https://docs.spring.io/spring-boot/docs/3.0.4/maven-plugin/reference/htmlsingle/#aot)

## GraalVM Native Support

This project has been configured to let you generate either a lightweight container or a native executable.
It is also possible to run your tests in a native image.

### Lightweight Container with Cloud Native Buildpacks
If you're already familiar with Spring Boot container images support, this is the easiest way to get started.
Docker should be installed and configured on your machine prior to creating the image.

To create the image, run the following goal:

```
$ ./mvnw spring-boot:build-image -Pnative
```

Then, you can run the app like any other container:

```
$ docker run --rm reto-app:1.0.0-SNAPSHOT
```

### Executable with Native Build Tools
Use this option if you want to explore more options such as running your tests in a native image.
The GraalVM `native-image` compiler should be installed and configured on your machine.

NOTE: GraalVM 22.3+ is required.

To create the executable, run the following goal:

```
$ ./mvnw native:compile -Pnative
```

Then, you can run the app as follows:
```
$ target/reto-app
```

You can also run your existing tests suite in a native image.
This is an efficient way to validate the compatibility of your application.

To run your existing tests in a native image, run the following goal:

```
$ ./mvnw test -PnativeTest
```

