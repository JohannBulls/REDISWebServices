Here's an improved version of your README file in English, incorporating the details about running on `https://localhost:8444` and using Docker to run Redis:

# Ticketing System with Spring Boot and Redis

This project is a ticketing system application built with Spring Boot and Redis. It allows users to generate tickets and verify their validity using Redis as the data store.

## Features

- **Ticket Generation:** Users can generate tickets which are stored in Redis.
- **Ticket Verification:** Ability to check if a ticket is valid by querying Redis.

## Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

Make sure you have the following installed:
- Java Development Kit (JDK)
- Maven
- Git
- Docker (optional, for running Redis)

## Main Classes Explained

### 1. `BBApplicationContextAware.java`

This class implements `ApplicationContextAware` from Spring, allowing access to the application context from Spring-unmanaged classes. This is useful for accessing Spring beans from non-Spring managed classes like utilities or configuration classes.

### 2. `DrawingServiceController.java`

REST controller class that handles requests related to the drawing service. It provides two main endpoints:

- **`/status`**: Returns the current server status in JSON format, including server date and time.
- **`/getticket`**: Returns a generated ticket, using `TicketRepository` to store and retrieve tickets.

### 3. `MvcConfig.java`

MVC configuration setting up access routes for different views, such as the home page (`/index`) and login page (`/login`). This allows controlling navigation flow without additional controllers.

### 4. `RedisConfig.java`

Spring configuration for connecting and setting up Redis as the data store for the application. It uses Lettuce as the Redis client, configuring the connection through properties defined in `application.properties`.

### 5. `TicketRepository.java`

Component responsible for ticket generation and management using Redis as storage. It uses `StringRedisTemplate` to interact with Redis and `ListOperations` to handle a list of tickets stored in Redis. It also implements methods for ticket generation and verification.

### 6. `WebSecurityConfig.java`

Security configuration for the application using Spring Security. Defines authorization rules for different endpoints and configures the custom login form (`/login`). Additionally, it provides a `UserDetailsService` defining in-memory users with user and admin roles for authentication.


### 7. `BBEndpoint.java`

WebSocket endpoint class handling drawing actions using the WebSocket protocol. Manages open WebSocket sessions, message processing, and error handling.

- **`send(String msg)`**: Method to send a message to all connected WebSocket clients except the sender.
- **`processPoint(String message, Session session)`**: Handles incoming messages from WebSocket clients. Verifies and accepts tickets before sending messages.
- **`openConnection(Session session)`**: Manages WebSocket connection opening, registering the session in the session queue.
- **`closedConnection(Session session)`**: Manages WebSocket connection closure, removing the session from the queue and deleting the associated ticket.
- **`error(Session session, Throwable t)`**: Handles WebSocket errors, logging errors and removing the session from the queue.

### 8. `BBConfigurator.java`

Configuration class for WebSocket server endpoint and scheduling. Enables exporting of WebSocket server endpoint using `ServerEndpointExporter`.

- **`serverEndpointExporter()`**: Bean definition for `ServerEndpointExporter`, enabling registration of WebSocket server endpoint.

### 9. `BBAppStarter.java`

Main class of the Spring Boot application. Annotated with `@SpringBootApplication`, it indicates the main entry point for the Spring Boot application.

- **`main(String[] args)`**: Main method starting the Spring Boot application.

### JavaScript Functions (Used in Frontend)

#### `startAudioContext()`

Function that checks and resumes the audio context if suspended.

#### `BBServiceURL()`

Function that calculates and returns the WebSocket service URL (`wss://`) based on the current host.

#### `ticketServiceURL()`

Function that calculates and returns the REST service URL for obtaining tickets based on the current host.

#### `WShostURL()`

Function that calculates and returns the base URL (`ws://`) for the current host.

#### `RESThostURL()`

Function that calculates and returns the base URL (`http://` or `https://`) for the current host.

### Additional Frontend Classes (React)

#### `WSBBChannel`

JavaScript class handling WebSocket connection logic, including connection opening, message reception, and error handling.

#### `BBCanvas`

React component representing the drawing canvas, using the p5.js library to render and send drawing points to the WebSocket server.

#### `Editor`

Main React component displaying the drawing canvas (`BBCanvas`) and other UI elements.

### Installing

Clone the repository:
```bash
git clone https://github.com/JohannBulls/REDISWebServices
```

### Running the Application

#### 1. Start Redis (via Docker)
```bash
docker run --name some-redis -p 45000:6379 -d redis
```

#### 2. Build and Run Spring Boot Application
```bash
cd REDISWebServices
mvn spring-boot:run
```

The application will be accessible at:
```
https://localhost:8444
```

### Usage

- Access the status endpoint to check server status:
  ```
  GET https://localhost:8444/status
  ```

- Generate a new ticket:
  ```
  GET https://localhost:8444/getticket
  ```

## Project Structure

### Backend (Spring Boot)

- **DrawingServiceController.java**: REST controller for handling status and ticket generation.
- **MvcConfig.java**: Configuration for MVC views.
- **WebSecurityConfig.java**: Configuration for Spring Security.
- **RedisConfig.java**: Configuration for Redis connection.

### Redis

- **TicketRepository.java**: Repository handling ticket generation and verification using Redis.

## Built With

- [Spring Boot](https://spring.io/projects/spring-boot) - Backend framework
- [Redis](https://redis.io/) - In-memory data store
- [Maven](https://maven.apache.org/) - Dependency management

## Authors

- Johann Amaya Lopez - [@JohannBulls](https://github.com/JohannBulls)

## License

This project is licensed under the GNU License - see the LICENSE.txt file for details.


