## JAX-RS and Jersey: REST webservices

### Overview
This repository contains an example of how to implement RESTful Web Services in Java using JAX-RS and Jersey.

#### JAX-RS
Java API for RESTful Web Services (JAX-RS) is a Java programming language API spec that provides support in creating web services according to the Representational State Transfer (REST) architectural pattern. JAX-RS uses annotations, introduced in Java SE 5, to simplify the development and deployment of web service clients and endpoints.

From version 1.1 on, JAX-RS is an official part of Java EE 6. A notable feature of being an official part of Java EE is that no configuration is necessary to start using JAX-RS. For non-Java EE 6 environments a small entry in the web.xml deployment descriptor is required.

#### Jersey
Jersey RESTful Web Services framework is an open source framework for developing RESTful Web Services in Java. It provides support for JAX-RS APIs and serves as a JAX-RS (JSR 311 & JSR 339) Reference Implementation.

### How to run the application



Execute the following command to deploy the application:

```docker-compose up```

It contains two controllers, one managing carts and their products,
and another one for managing products. Below are some curl commands to reach out the
operations provided by the application.

---

### Cart Operations

**Create a new cart:**

`curl -d '{"street": "street one","city": "new york","products": [{"price": 115.9,"name": "shoes","quantity": 1},{"price": 45,"name": "t-shirt","quantity": 1}]}' -H "Content-Type: application/json" -X POST http://localhost:8081/api/carts`

**Find all carts:**

`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8081/api/carts`

**Find a specific cart:**

`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8081/api/carts/1`

**Delete a cart:**

`curl -X "DELETE" http://localhost:8081/api/carts/1`

**Delete product by id from a specific cart:**

`curl -X "DELETE" localhost:8080/api/carts/1/products/1`

**Update product quantity:**

`curl -X PUT -H "Content-Type: application/json" -d '{"quantity": 2}' http://localhost:8081/api/carts/1/products/1/quantity`

---

### Project Operations

**Create a new project:**

`curl -d '{"name": "project","year": "2018"}' -H "Content-Type: application/json" -X POST http://localhost:8081/api/projects`

**Find all projects:**

`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8081/api/projects`

**Find a specific project:**

`curl -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET http://localhost:8081/api/projects/1`

**Delete a project:**

`curl -X "DELETE" http://localhost:8081/api/projects/1`

---

### Postman
The postman collection is available to be imported at: https://github.com/rafael-pieri/jaxrs-and-jersey-alura/tree/master/postman
