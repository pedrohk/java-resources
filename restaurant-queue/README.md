# Restaurant Queue

A simple demonstration project that models a restaurant order queue in Java. It shows a small service responsible for receiving orders, estimating wait times based on chef capacity, and sending asynchronous notifications to subscribers when order status changes.

This repository is intended as a compact example for learning about concurrent queues, basic service design, and using asynchronous notifications in Java.

## Key features
- Place and complete orders using a thread-safe queue
- Estimate wait time taking chef capacity into account
- Send notifications to subscribed listeners asynchronously

## Project structure

- `src/main/java`
  - `com.pedrohk.queue.model` — domain models (`Dish`, `Order`)
  - `com.pedrohk.queue.service` — core service (`QueueService`)
  - `com.pedrohk.router` — notification abstractions and implementation (`NotificationListener`, `NotificationService`)

- `src/test/java` — unit and integration tests demonstrating behavior

## Main classes

- `QueueService` — maintains a concurrent queue of `Order` objects, supports `placeOrder`, `completeOrder` and `estimateWaitTimeMinutes`.
- `NotificationService` — allows subscribing `NotificationListener`s and notifies them asynchronously when order status changes (uses an Executor).
- `Dish`, `Order` — small records that represent the menu item and customer order respectively.

## Requirements

- Java: configured for Java 25 in `pom.xml` (requires a JDK compatible with the configured source/target; Java 21+ is recommended when using virtual threads/features in the code)
- Maven: used to build and run tests

## Build and test (PowerShell)

Open PowerShell in the project root and run:

```powershell
mvn -q test
```

This will compile the project and run the tests under `src/test/java`.

## Example usage (conceptual)

The following demonstrates how `QueueService` can be used programmatically:

```java
NotificationService notificationService = new NotificationService();
QueueService queueService = new QueueService(2, notificationService); // 2 chefs

Dish pizza = new Dish("Pizza", 20);
Order order = new Order(1, "customer@example.com", pizza, java.time.LocalDateTime.now());

notificationService.subscribe((o, status) -> System.out.println(o.id() + " -> " + status));
queueService.placeOrder(order);
System.out.println("Estimated wait: " + queueService.estimateWaitTimeMinutes(pizza) + " minutes");
queueService.completeOrder(); // will trigger READY notification
```

## Notes and design choices

- The queue is a `ConcurrentLinkedDeque` for thread-safe operations.
- Notifications are dispatched to subscribers using an `ExecutorService` so listeners are invoked asynchronously.
- `estimateWaitTimeMinutes` computes a simple estimate summing base preparation times and dividing by `chefCapacity`. It is intentionally simple for demonstration.

## Tests

- Unit tests are in `src/test/java/com/pedrohk/queue/QueueServiceTest.java` and cover estimation, capacity impact and queue completion.
- Integration-style tests are in `NotificationIntegrationTest.java` and verify asynchronous notifications are delivered to subscribers.
