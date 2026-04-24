# ⚡ Redis Clone (Java)

A lightweight **Redis-like in-memory key-value store** built from scratch in Java.  
This project focuses on core systems design concepts such as networking, concurrency, protocol handling, and data structures.

---

## 🚀 Features

- 🔑 String key-value storage (`SET`, `GET`, `APPEND`)
- 🗂️ Hash data structure support (`HSET`, `HGET`, `HKEYS`, `HVALS`)
- 🌐 TCP server with custom text-based protocol
- ⚡ Concurrent request handling using **Virtual Threads**
- 🧠 In-memory storage engine
- 🧪 Integration tests with real client-server communication

---

## 🏗️ Project Structure

```

redis-clone/
├── storage/
│   └── StorageEngine.java
├── server/
│   └── RedisServer.java
├── client/
│   └── RedisClient.java
└── test/
└── RedisIntegrationTest.java

```id="struct21"

---

## 🧠 Core Concepts

### 1. Storage Engine

The `StorageEngine` is the heart of the system.

It uses thread-safe structures:
- `ConcurrentHashMap<String, String>` → for string values
- `ConcurrentHashMap<String, Map<String, String>>` → for hash structures

Supported operations:

| Command | Description |
|--------|------------|
| `SET key value` | Store a value |
| `GET key` | Retrieve a value |
| `APPEND key value` | Append to existing value |
| `DEL key` | Remove a key |
| `HSET key field value` | Set hash field |
| `HGET key field` | Get hash field |
| `HKEYS key` | List fields |
| `HVALS key` | List values |

---

### 2. Redis-like Server

The `RedisServer` provides a TCP interface:

- Listens on a configurable port (default: `6379`)
- Handles multiple clients concurrently
- Uses **Virtual Threads (Project Loom)** for scalability
- Parses simple space-delimited commands

Example protocol:

```

SET mykey hello
GET mykey

```

Responses:
```

OK
hello

````

---

### 3. Client Wrapper

The `RedisClient` simplifies communication:

```java id="clientex"
try (RedisClient client = new RedisClient("localhost", 6379)) {
    client.send("SET mykey hello");
    String value = client.send("GET mykey");
}
````

---

### 4. Integration Testing

Instead of mocking, this project uses **real server-client interaction**.

Test coverage includes:

* String operations
* Hash operations
* Server startup and connection retry logic

---

## ⚙️ Tech Stack

* Java 25
* Maven
* JUnit 5
* Virtual Threads (Project Loom)

---

## ▶️ How to Run

### 1. Build the project

```bash id="buildcmd"
mvn clean install
```

### 2. Start the server

```java id="serverrun"
new RedisServer().start(6379);
```

### 3. Connect via client

You can use:

* The provided `RedisClient`
* Telnet or Netcat:

```bash id="telnetcmd"
telnet localhost 6379
```

---

## 🧪 Run Tests

```bash id="testcmd"
mvn test
```

---

## 🔄 Trade-offs

### ✅ Pros

* Simple and educational design
* Real networking (TCP sockets)
* Concurrent and scalable with virtual threads
* Easy to extend with new commands
* Clean separation of concerns (storage, server, client)

### ⚠️ Cons

* No persistence (data is lost on restart)
* No replication or clustering
* Limited protocol (not RESP-compatible)
* No memory eviction strategy
* Basic parsing (no validation or binary safety)

---

## 🔮 Future Improvements

* ⏳ TTL (Time-To-Live) for key expiration
* 💾 Persistence (RDB / AOF-like)
* 📡 RESP protocol compatibility (real Redis clients)
* 🧹 LRU / LFU eviction policies
* 🔐 Authentication support
* 📊 Metrics and monitoring

---

## 📚 Learning Goals

This project is ideal for understanding:

* How Redis works internally
* Building a TCP server from scratch
* Concurrency models in modern Java
* Designing a simple database engine
* Trade-offs of in-memory systems
