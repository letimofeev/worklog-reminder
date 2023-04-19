# Employee Service

This service is an API for managing employees. It provides endpoints for creating, retrieving, updating, and deleting
employee records. Users can add employees, view a single employee by ID or by Jira key, get a list of all employees,
update an existing employee, or delete an employee by ID.
The API returns data in JSON format and may return error responses for invalid requests or unexpected errors.

Implemented in Java 11 with Spring Boot.

[<img src="https://img.shields.io/badge/springboot-2.7.8-green.svg?logo=springboot">](https://spring.io/projects/spring-boot#overview)

# Table of Contents

1. [Architecture](#architecture)
2. [Employee API Endpoints](#employee-api-endpoints)
3. [OpenAPI](#openapi)
4. [Integrations](#integrations)
5. [Frameworks](#frameworks)
6. [Environment variables](#environment-variables)

## Architecture

This Employee service is implemented using hexagonal architecture, with a clear separation between the business logic in
the inner hexagon and the adapters that handle interactions with external systems.
The API controllers, persistence layer, and other external interfaces are implemented as adapters, providing input and
output ports that are decoupled from the core business logic.
This architecture allows for flexibility and scalability in the system, making it easier to test, maintain, and evolve
over time. The use of hexagonal architecture also promotes the modularity and reusability of the code, making it easier
to integrate with other systems or reuse components in different contexts.

![Employee architecture](../../docs/images/employee-hexagon.png "Employee architecture")

## Employee API Endpoints

This API provides endpoints for managing employees.

| Endpoint                           | Type       | Description                                                                |
|------------------------------------|------------|----------------------------------------------------------------------------|
| `/api/employees`                   | **POST**   | Adds a new employee to the system with the provided employee data          |
| `/api/employees/{id}`              | **GET**    | Retrieves the employee with the specified ID                               |
| `/api/employees`                   | **GET**    | Retrieves a list of all employees in the system                            |
| `/api/employees?jiraKey={jiraKey}` | **GET**    | Retrieves a list of employees with the specified JIRA key                  |
| `/api/employees`                   | **PUT**    | Updates an existing employee in the system with the provided employee data |
| `/api/employees/{id}`              | **DELETE** | Deletes the employee with the specified ID                                 |

## OpenAPI

### OpenAPI Specification

`GET /api/docs/services/employee`

Retrieves OpenAPI v3 JSON specification

### Swagger UI

`GET /api/docs/services/employee/swagger-ui`

Opens Swagger OpenAPI v3 specification UI

## Integrations

### Integration with Notification Service

The service integrates with the `Notification Service` to retrieve employee notification status.
The integration is implemented using the `/api/users` endpoint provided by the Notification Service.  
Employee and notification user mapped by skype login: `skypeLogin` corresponds `login` field.

| Endpoint                     | Type       | Description                                                                                                                                                    |
|------------------------------|------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `/api/users?logins={logins}` | **GET**    | Retrieves a list of notification users by list of Skype logins. <br/>Method used to fill `botConnected` and `notificationEnabled` fields in the Employee model |
| `/api/users`                 | **PATCH**  | Updates the notification user. <br/>Method used to update the notification enable flag when the employee is updated                                            |
| `/api/users/{id}`            | **DELETE** | Deletes the notification user by id. <br/>Method used to delete the corresponding notification user when the employee is deleted                               |

### Subscription on Region Events

The `Employee` microservice should keep information about regions in sync with the `Region` microservice.
It listens for region events from RabbitMQ and updates its own database accordingly.
If an event fails to process, the listener will retry a configurable number of times before sending it to a
corresponding dead letter queue.

Default exchange name: `region.exchange`

| Queue (default)                 | Event Type         | Description                                               |
|---------------------------------|--------------------|-----------------------------------------------------------|
| `employee.region.created.queue` | **region_created** | Triggered when a new region is created in the system      |
| `employee.region.updated.queue` | **region_updated** | Triggered when a existing region is updated in the system |
| `employee.region.deleted.queue` | **region_deleted** | Triggered when a region is deleted from the system        |

For dead letters used exchange and queues with `.dlq` postfix, f.e. `employee.region.created.queue.dql`

## Frameworks

| Framework       | Description                                                                                                                                                                                                                                                  |
|-----------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Spring Web      | Provides support for building web applications, including RESTful web services and web applications with MVC architecture, using Java or Kotlin                                                                                                              |
| Spring Data JPA | Provides a set of abstractions and helper classes to simplify the implementation of data access layers in Java applications using the Java Persistence API (JPA)                                                                                             |
| Spring AMPQ     | Provides support for developing AMQP-based messaging solutions. It provides abstractions and implementations for sending and receiving messages using the AMQP protocol, as well as additional features such as message conversion, retry and error handling |
| Spring Boot     | Simplifies the development of stand-alone, production-grade Spring-based applications by providing a pre-configured and opinionated set of dependencies and conventions                                                                                      |
| Liquibase       | Database schema management tool that provides a consistent and repeatable way to track, version, and deploy database changes alongside application code                                                                                                      |
| Lombok          | Provides a set of annotations to automatically generate boilerplate code, such as getters, setters, and constructors, at compile time, reducing the amount of boilerplate code needed in Java classes                                                        |
| MapStruct       | code generation tool that simplifies the process of mapping between Java classes by generating mapping code based on annotations and interface definitions                                                                                                   |

## Environment variables

- `SERVER_PORT`
  - Port on which the application server should listen for incoming HTTP requests
  - Default value: `8300`
- `POSTGRES_URL`
    - Url to connect to the employee database
    - Default value: `jdbc:postgresql://localhost:5434/employee`
- `POSTGRES_USER`
    - Username for the employee database
    - Default value: `postgres`
- `POSTGRES_PASSWORD`
    - Password for the employee database
    - Default value: `postgres`
- `NOTIFICATION_BASE_URL`
    - Notification service base url
    - Default value: `http://localhost:8200`
- `RABBITMQ_HOST`
  - Hostname or IP address of the RabbitMQ server
  - Default value: `localhost`
- `RABBITMQ_PORT`
  - Port number on which RabbitMQ server is listening for connections
  - Default value: `5672`
- `RABBITMQ_USERNAME`
  - Username for RabbitMQ
  - Default value: `guest`
- `RABBITMQ_PASSWORD`
  - Password for RabbitMQ
  - Default value: `guest`
- `RABBITMQ_REGION_EXCHANGE`
  - Custom exchange name for region events
  - Default value: `region.exchange`
- `RABBITMQ_REGION_CREATED_QUEUE`
  - Custom queue name for **region_created** events
  - Default value: `employee.region.created.queue`
- `RABBITMQ_REGION_UPDATED_QUEUE`
  - Custom queue name for **region_updated** events
  - Default value: `employee.region.updated.queue`
- `RABBITMQ_REGION_DELETED_QUEUE`
  - Custom queue name for **region_deleted** events
  - Default value: `employee.region.deleted.queue`
