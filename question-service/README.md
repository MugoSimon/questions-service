# Question Service

The Question Service is a microservice responsible for managing the questions and related data in the application.

## Features
- Provides endpoints to create, read, update, and delete questions
- Supports various question types (multiple choice, true/false, fill-in-the-blank, etc.)
- Allows for the organization of questions into different categories and difficulty levels

## Configuration
The Question Service requires the following configuration:

```properties
# Database Config
spring.datasource.url=jdbc:mysql://localhost:3306/questionService?enabledTLSProtocols=TLSv1.2
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

Make sure to update the database connection details according to your environment.

## Deployment
The Question Service can be deployed as a standalone Spring Boot application. Ensure that the Eureka server is running and the service is properly registered with it.

## Endpoints
The Question Service exposes the following endpoints:

- `POST /questions`: Creates a new question.
- `GET /questions/{id}`: Retrieves the details of a specific question.
- `PUT /questions/{id}`: Updates an existing question.
- `DELETE /questions/{id}`: Deletes a question.

## Dependencies
- Spring Boot
- Spring Data JPA
- MySQL
- Eureka (for service discovery)

## Known Issues
- The Question Service does not have any authentication or authorization mechanisms in place.
- The error handling and logging could be improved.

## Future Improvements
- Implement authentication and authorization for the Question Service.
- Improve the error handling and logging mechanisms.
- Add more test cases to ensure the reliability of the service.