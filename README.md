# Bajaj Finserv Health Java Qualifier

This is a Spring Boot application that completes the Bajaj Finserv Health Java qualifier task automatically on startup.

## Project Overview

The application performs the following steps automatically when it starts:

1. **Webhook Generation**: Sends a POST request to generate a webhook with registration details
2. **Problem Analysis**: Determines the question type based on registration number (odd/even)
3. **SQL Solution**: Generates the appropriate SQL query based on the question type
4. **Data Storage**: Stores the problem and solution in an H2 database
5. **Solution Submission**: Submits the final SQL query to the webhook URL using JWT authentication

## Features

- **Automatic Execution**: Runs automatically on application startup
- **REST API Integration**: Uses RestTemplate for HTTP communication
- **JWT Authentication**: Handles JWT tokens for secure API calls
- **Database Storage**: Stores all problem and solution data
- **Error Handling**: Comprehensive error handling and logging
- **H2 Database**: In-memory database for data persistence

## Technology Stack

- **Java 8+**
- **Spring Boot 2.7.18**
- **Spring Data JPA**
- **H2 Database**
- **Maven**
- **RestTemplate**

## Prerequisites

- Java 8 or higher
- Maven 3.6 or higher (or use an IDE with built-in Maven support)

## Setup Instructions

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd health-qualifier
```

### 2. Build the Project

**Option A: Using Maven (if installed)**
```bash
mvn clean compile
```

**Option B: Using IDE**
- Open the project in IntelliJ IDEA, Eclipse, or VS Code
- Use the IDE's built-in Maven support to build the project

**Option C: Using build.bat (Windows)**
```bash
build.bat
```

### 3. Run the Application

**Option A: Using Maven (if installed)**
```bash
mvn spring-boot:run
```

**Option B: Using IDE**
- Run the `HealthQualifierApplication` class from your IDE

**Option C: Using JAR file**
```bash
java -jar target/health-qualifier-1.0.0.jar
```

### 4. Build JAR File

**Option A: Using Maven (if installed)**
```bash
mvn clean package
```

**Option B: Using IDE**
- Use the IDE's Maven integration to run the package goal

The JAR file will be created in the `target/` directory.

## How It Works

### 1. Webhook Generation
- Sends POST request to: `https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA`
- Request body includes name, registration number, and email
- Receives webhook URL and access token

### 2. Question Type Determination
- Extracts last two digits from registration number "REG12347"
- Last two digits: "47" (odd number)
- Assigns Question 1 (ODD question type)

### 3. SQL Problem Solving
- **Question 1 (ODD)**: Generates SQL query for patient diagnosis and treatment data
- **Question 2 (EVEN)**: Generates SQL query for department statistics

### 4. Solution Submission
- Sends POST request to: `https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA`
- Includes the final SQL query in the request body
- Uses JWT token in Authorization header

## Database Schema

The application uses an H2 in-memory database with the following table:

### sql_problems
- `id`: Primary key
- `reg_no`: Registration number
- `question_type`: Question type (ODD/EVEN)
- `problem_description`: Problem description
- `solution_query`: Generated SQL solution
- `created_at`: Timestamp
- `webhook_url`: Webhook URL from API
- `access_token`: JWT access token

## API Endpoints

The application doesn't expose any REST endpoints. It only makes outbound API calls to:
- Webhook generation API
- Solution submission API

## Configuration

Key configuration properties in `application.properties`:
- H2 database settings
- JPA configuration
- Logging levels
- Server port (8080)

## Monitoring

- H2 Console available at: `http://localhost:8080/h2-console`
- Application logs show detailed execution steps
- Database tables can be viewed through H2 console

## Troubleshooting

### Common Issues

1. **Port Already in Use**: Change `server.port` in `application.properties`
2. **Network Issues**: Check internet connectivity for API calls
3. **JWT Token Issues**: Verify token format and expiration

### Logs

The application provides detailed logging for:
- Webhook generation process
- Question type determination
- SQL solution generation
- Database operations
- Solution submission

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── bajajfinserv/
│   │           ├── HealthQualifierApplication.java
│   │           ├── config/
│   │           │   └── AppConfig.java
│   │           ├── dto/
│   │           │   ├── WebhookRequest.java
│   │           │   ├── WebhookResponse.java
│   │           │   └── SolutionRequest.java
│   │           ├── entity/
│   │           │   └── SqlProblem.java
│   │           ├── repository/
│   │           │   └── SqlProblemRepository.java
│   │           └── service/
│   │               └── QualifierService.java
│   └── resources/
│       └── application.properties
pom.xml
README.md
```

## Build Output

After running `mvn clean package`, the following artifacts are generated:
- **JAR File**: `target/health-qualifier-1.0.0.jar`
- **Executable JAR**: Can be run with `java -jar target/health-qualifier-1.0.0.jar`

## License

This project is created for the Bajaj Finserv Health Java qualifier task.

## Support

For any issues or questions, please refer to the project documentation or contact the development team.
