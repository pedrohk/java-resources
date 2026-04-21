# Logistic System (Dynamic Freight Pricing)

A flexible and extensible logistics pricing system built in Java, designed to dynamically calculate freight costs based on transport type, package dimensions, and weight.

This project demonstrates the use of **Strategy Pattern + Runtime Registry**, allowing pricing rules to be updated dynamically without changing the core system.

## 🚀 Features

* Dynamic freight pricing engine
* Strategy Pattern for transport-specific calculations
* Runtime strategy updates (no restart required)
* Thread-safe pricing registry
* Clean domain modeling using Java records
* Fully unit tested with JUnit 5

## 📦 Project Structure

```text
java_resources/
└── logistic-system/
    ├── pom.xml
    └── src/
        ├── main/java/com/pedrohk/logistic/
        │   ├── model/
        │   │   ├── Dimensions.java
        │   │   └── TransportType.java
        │   ├── strategy/
        │   │   ├── FreightStrategy.java
        │   │   ├── DynamicPricingRegistry.java
        │   │   └── impl/
        │   │       ├── TruckStrategy.java
        │   │       └── BoatStrategy.java
        │   └── service/
        │       └── LogisticService.java
        └── test/java/com/pedrohk/logistic/
            └── LogisticSystemTest.java
```

## 🧠 Concept

This system is built around two core ideas:

### 1. Strategy Pattern

Each transport type has its own pricing algorithm:

* `TruckStrategy`
* `BoatStrategy`
* (Extensible for Rail, Air, etc.)

### 2. Dynamic Registry

The `DynamicPricingRegistry` allows:

* Adding strategies at runtime
* Updating pricing rules dynamically
* Decoupling pricing logic from business logic

## ⚙️ Installation

Clone the repository:

```bash
git clone https://github.com/pedrohk/java_resources.git
cd java_resources/logistic-system
```

Build with Maven:

```bash
mvn clean install
```

## 🛠️ Usage

### Basic Example

```java
DynamicPricingRegistry registry = new DynamicPricingRegistry();

// Register strategies
registry.updateStrategy(TransportType.TRUCK, new TruckStrategy(1.5));
registry.updateStrategy(TransportType.BOAT, new BoatStrategy());

LogisticService service = new LogisticService(registry);

// Create package dimensions
Dimensions dim = new Dimensions(10, 10, 10);

// Calculate freight
double price = service.calculateFreight(TransportType.TRUCK, dim, 100);

System.out.println("Freight cost: " + price);
```

## 🔄 Dynamic Pricing Example

You can update pricing logic at runtime:

```java
registry.updateStrategy(TransportType.TRUCK, new TruckStrategy(1.0));
double price1 = service.calculateFreight(TransportType.TRUCK, dim, 100);

registry.updateStrategy(TransportType.TRUCK, new TruckStrategy(2.0));
double price2 = service.calculateFreight(TransportType.TRUCK, dim, 100);
```

No restart required — changes take effect immediately.

## 📐 Domain Model

### Dimensions

```java
public record Dimensions(double length, double width, double height) {
    public double volume() {
        return length * width * height;
    }
}
```

### Transport Types

```java
public enum TransportType {
    BOAT, TRUCK, RAIL
}
```

## 🧪 Tests

The project includes comprehensive unit tests covering:

* Dynamic strategy updates
* Pricing correctness
* Exception handling
* Volume calculations

Run tests:

```bash
mvn test
```

## 📌 Requirements

* Java 21+ (compatible with modern features like records)
* Maven 3+

## 🔮 Future Improvements

* Add support for `RAIL` strategy
* Introduce Observer Pattern for pricing updates
* Add REST API layer (Spring Boot)
* Support currency and regional pricing rules
* Add caching for repeated calculations
* Implement audit logging for pricing changes
