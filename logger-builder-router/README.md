# Logger Builder Router

A flexible and extensible logging system that allows you to route logs to different destinations such as File System, ELK stack, or Console, using a unified API.

The system supports both **synchronous and asynchronous logging** through a simple and fluent Builder pattern.

## 🚀 Features

* Unified logging API
* Multiple log destinations:

  * File System
  * ELK (Logstash simulation)
  * Console
* Sync and Async execution modes
* Builder pattern for easy configuration
* Lightweight and dependency-free (except tests)
* Uses Java Virtual Threads for async execution

## 📦 Project Structure

```
logger-builder-router/
├── pom.xml
└── src/
    ├── main/java/com/pedrohk/router/
    │   ├── Logger.java
    │   ├── LogDestination.java
    │   ├── AsyncLogger.java
    │   ├── LoggerBuilder.java
    │   └── core/
    │       ├── ConsoleLogger.java
    │       ├── ElkLogger.java
    │       └── FileSystemLogger.java
    └── test/java/com/pedrohk/router/
        └── LoggerRouterTest.java
```

## 🧠 Concept

This project implements a **Router + Builder pattern**:

* **Router**: Chooses the logging destination
* **Builder**: Configures how the logger behaves (sync/async)
* **Decorator (AsyncLogger)**: Adds asynchronous behavior transparently

## ⚙️ Installation

Build with Maven:

```bash
mvn clean install
```

## 🛠️ Usage

### Basic Example (Synchronous)

```java
Logger logger = new LoggerBuilder()
    .to(LogDestination.CONSOLE)
    .build();

logger.log("Hello, world!");
```

### Asynchronous Logging

```java
Logger logger = new LoggerBuilder()
    .to(LogDestination.ELK)
    .async()
    .build();

logger.log("Async log message");
```

### File System Logging

```java
Logger logger = new LoggerBuilder()
    .to(LogDestination.FILESYSTEM)
    .build();

logger.log("Writing to file system");
```

## 🔄 How It Works

1. Choose a destination via `LogDestination`
2. Optionally enable async mode
3. Build the logger
4. Use the same `.log()` API regardless of implementation

Internally:

* `LoggerBuilder` creates the correct implementation
* `AsyncLogger` wraps any logger for async execution
* Virtual threads handle concurrency efficiently

## 🧪 Tests

The project includes unit tests using JUnit 5:

* Sync vs Async validation
* Virtual thread execution behavior
* Builder configuration checks

Run tests:

```bash
mvn test
```

## 📌 Requirements

* Java 21+ (Virtual Threads required)
* Maven 3+

## 🔮 Future Improvements

* Support for multiple destinations (broadcast logging)
* Real ELK integration (HTTP / Logstash)
* Log levels (INFO, WARN, ERROR)
* Structured logging (JSON)
* Pluggable destinations (SPI)
