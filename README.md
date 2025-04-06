# Spring Boot Mainframe DB2 Integration Application

This project demonstrates how to connect and interact with a DB2 database on a Mainframe system using Spring Boot and NamedParameterJdbcTemplate.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Technology Stack](#technology-stack)
- [Configuration](#configuration)
- [Database Setup](#database-setup)
- [API Documentation](#api-documentation)
- [Development Guide](#development-guide)
- [Testing](#testing)
- [Deployment](#deployment)
- [Troubleshooting](#troubleshooting)
- [Contributing](#contributing)

## Prerequisites

- Java 17 or later
- Maven 3.8 or later
- Access to a Mainframe DB2 database
- IBM DB2 JDBC driver (included in the project)

## Project Structure

```
src/main/java/com/example/demo/
├── config/         # Configuration classes
├── constant/       # Application constants
├── controller/     # REST API controllers
├── dao/           # Data Access Objects
├── model/         # Data models/entities
├── service/       # Business logic services
└── DemoApplication.java
```

## Technology Stack

- Spring Boot 3.2.3
- Spring JDBC (NamedParameterJdbcTemplate)
- IBM DB2 JDBC Driver 11.5.8.0
- Springdoc OpenAPI 2.3.0 (Swagger)
- Lombok
- Maven

## Configuration

### Database Connection

Configure your database connection in `application.properties`:

```properties
spring.datasource.jdbcUrl=jdbc:db2://mainframe-host:446/DBNAME
spring.datasource.username=db2user
spring.datasource.password=db2pass
spring.datasource.driver-class-name=com.ibm.db2.jcc.DB2Driver
spring.datasource.hikari.data-source-properties.currentSchema=SCHEMA
```

Replace the following placeholders:
- `mainframe-host`: Your Mainframe hostname
- `DBNAME`: Your DB2 database name
- `db2user`: DB2 username
- `db2pass`: DB2 password
- `SCHEMA`: DB2 schema name

## Database Setup

The application uses the following table structure:

```sql
CREATE TABLE users (
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY 
     (START WITH 1, INCREMENT BY 1, NO CACHE),
  username VARCHAR(100) NOT NULL,
  email VARCHAR(100),
  PRIMARY KEY (id)
);
```

## API Documentation

The application provides the following REST API endpoints:

1. **Create User**: `POST /api/users`
2. **Get All Users**: `POST /api/users/all`
3. **Find User**: `POST /api/users/find`
4. **Update User**: `POST /api/users/update`
5. **Delete User**: `POST /api/users/delete`
6. **Test Connection**: `POST /api/users/test`

Access the Swagger UI at: `http://localhost:8080/swagger-ui.html`

## Development Guide

### Setting Up Development Environment

1. Clone the repository
2. Import the project into your IDE as a Maven project
3. Configure your database connection in `application.properties`
4. Install dependencies: `mvn clean install`

### Adding New Features

1. Create new model classes in `model` package
2. Add DAO interfaces in `dao` package
3. Implement services in `service` package
4. Create controllers in `controller` package
5. Add appropriate tests

### Code Style

- Follow Java naming conventions
- Use Lombok annotations for boilerplate code
- Document public methods and classes
- Follow REST API best practices

## Testing

Run tests using:
```bash
mvn test
```

The project includes:
- Unit tests for services and controllers
- Integration tests for database operations
- API tests using MockMvc

## Deployment

1. Build the application:
```bash
mvn clean package
```

2. Run the application:
```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Troubleshooting

Common issues and solutions:

1. **Connection Issues**
   - Verify network connectivity to Mainframe
   - Check firewall settings
   - Validate credentials
   - Use the test endpoint to verify connection

2. **Performance Issues**
   - Check connection pool settings
   - Optimize SQL queries
   - Monitor Mainframe resource usage

3. **Character Encoding Issues**
   - Verify EBCDIC encoding settings
   - Check database character set configuration

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## References

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [IBM DB2 JDBC Driver Documentation](https://www.ibm.com/docs/en/db2/11.5?topic=connectivity-java-jdbc-driver-type-4)
- [Spring JDBC Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#jdbc) 