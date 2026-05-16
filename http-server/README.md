# Simple HTTP Server (Java)

A minimal HTTP server built from scratch in Java, designed to demonstrate how web servers work at a low level using sockets, concurrency, and routing.

## Overview

This project implements a lightweight HTTP server without using any external frameworks. It handles basic HTTP `GET` requests, supports route registration, and processes multiple concurrent connections using **virtual threads (Project Loom)**.

It is ideal for learning purposes, experimentation, or understanding the fundamentals behind frameworks like Spring Boot or Netty.

## Features

* Basic HTTP server using `ServerSocket`
* Route handling with simple `GET` endpoints
* Concurrent request processing using virtual threads
* Lightweight and dependency-free (only JUnit for testing)
* Custom response handling (status codes + body)
* Integration tests simulating real HTTP requests

## Project Structure

```
src/
 ├── main/java/com/pedrohk/http/
 │     └── SimpleHttpServer.java
 └── test/java/com/pedrohk/http/
       └── SimpleHttpServerTest.java
```

## How It Works

* The server listens on a configurable port.
* Incoming connections are accepted via `ServerSocket`.
* Each request is handled in a **virtual thread**, allowing high concurrency with low overhead.
* Routes are stored in a thread-safe `ConcurrentHashMap`.
* Only `GET` requests are supported.
* Responses are written manually following the HTTP protocol format.

## Example Usage

```java
SimpleHttpServer server = new SimpleHttpServer(8080);

server.get("/hello", () -> "Hello World");
server.get("/status", () -> "Server is running");

server.start();
```

Then open in your browser:

```
http://localhost:8080/hello
```

## Example Response

```
HTTP/1.1 200 OK
Content-Type: text/plain
Content-Length: 11

Hello World
```

## Testing

This project includes **deep integration tests** using JUnit 5 that validate:

* Successful responses for registered routes
* Proper handling of unknown routes (404)
* Concurrent request handling under load

### Highlights

* Uses real HTTP connections (`HttpURLConnection`)
* Spins up the server in a background virtual thread
* Verifies behavior with multiple simultaneous requests

## Requirements

* Java 21+ (Virtual Threads required)
* Maven

## Build & Run

### Compile the project

```bash
mvn clean install
```

### Run tests

```bash
mvn test
```

## Design Decisions

### Why Virtual Threads?

Virtual threads (introduced with Project Loom) allow the server to handle thousands of concurrent requests efficiently without the complexity of reactive programming.

### Why No Framework?

The goal is to understand the **core mechanics of HTTP servers**, including:

* Socket communication
* Request parsing
* Manual response construction
* Thread management

## Limitations

* Only supports `GET` requests
* No HTTP headers parsing beyond basics
* No support for:

  * Query parameters
  * POST/PUT methods
  * JSON handling
  * Middleware or filters
* Minimal error handling

## Pros

* Extremely lightweight
* Easy to understand and extend
* Great for educational purposes
* Demonstrates modern Java concurrency (virtual threads)

## Cons

* Not production-ready
* Lacks HTTP compliance and robustness
* No security features (HTTPS, validation, etc.)
* Reinvents functionality provided by mature frameworks

## Future Improvements

* Support for query parameters (`/hello?name=Pedro`)
* Add POST and request body handling
* Basic HTTP headers parsing
* JSON responses
* Middleware support (logging, auth, etc.)
* Graceful shutdown
