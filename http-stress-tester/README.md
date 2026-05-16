# HTTP Stress Tester

A lightweight and efficient HTTP stress testing tool built with modern Java (Java 21+ / Virtual Threads). This project allows you to simulate high-load scenarios against HTTP endpoints and measure performance metrics such as throughput and success/failure rates.

---

## 🚀 Features

* ⚡ High-performance HTTP stress testing
* 🧵 Uses **Virtual Threads** for massive concurrency
* 📊 Calculates requests per second (RPS)
* ✅ Success and failure tracking
* 🧪 Fully tested with integration-style tests
* 🔌 No external dependencies (pure Java)

---

## 📦 Tech Stack

* Java 21+ (Virtual Threads / Project Loom)
* `java.net.http.HttpClient`
* JUnit 5 for testing
* Maven

---

## 📁 Project Structure

```
src/
 ├── main/java/com/pedrohk/stress
 │    ├── HttpStressTester.java
 │    └── StressResult.java
 └── test/java/com/pedrohk/stress
      └── HttpStressTesterTest.java
```

---

## ⚙️ How It Works

The `HttpStressTester` sends a defined number of asynchronous HTTP requests to a target URL using virtual threads.

* Each request is executed using `HttpClient.sendAsync`
* Responses are categorized as:

  * **Success** → HTTP 2xx
  * **Failure** → Non-2xx or exceptions
* Results are aggregated using `LongAdder` for thread-safe performance

---

## 📊 StressResult

The result of a stress test includes:

* Total requests
* Successful requests
* Failed requests
* Total duration (ms)
* Requests per second (RPS)

Example:

```java
StressResult result = tester.run("http://localhost:8080", 1000);

System.out.println("RPS: " + result.requestsPerSecond());
```

---

## ▶️ Usage Example

```java
HttpStressTester tester = new HttpStressTester(100);

StressResult result = tester.run("http://localhost:8080/api", 1000);

System.out.println("Total: " + result.totalRequests());
System.out.println("Success: " + result.successfulRequests());
System.out.println("Failures: " + result.failedRequests());
System.out.println("Duration: " + result.durationMs() + " ms");
System.out.println("RPS: " + result.requestsPerSecond());
```

---

## 🧪 Testing

The project includes robust tests that simulate:

* ✅ Successful load execution
* ❌ Failure scenarios (invalid endpoints)
* 🔌 Connection errors
* 🚀 High concurrency stress

Run tests with:

```bash
mvn test
```

---

## ⚖️ Trade-offs

### ✅ Pros

* Extremely lightweight (no external libraries)
* High scalability with virtual threads
* Simple and easy to understand
* Fast execution for load testing scenarios

### ❌ Cons

* No real-time metrics during execution
* No built-in reporting or visualization
* Limited configurability (e.g., headers, payloads, methods)
* Not suitable for distributed stress testing
* No latency breakdown (p95, p99, etc.)

---

## 🔮 Possible Improvements

* 📡 Real-time metrics (live RPS, success rate)
* 📈 Export results to JSON/CSV
* 🧾 Detailed latency metrics (percentiles)
* 🔧 Support for POST, PUT, headers, and payloads
* 🌍 Distributed stress testing support
* 📊 Dashboard integration (e.g., Grafana)

---

## 🛠️ Build

```bash
mvn clean install
```
