# Hotel Reservation System

A hotel reservation system developed in Java with role-based authentication and room availability validation.

## Overview

This project implements a hotel reservation management system with:
- ✅ Role-based authentication and authorization (USER, ADMIN)
- ✅ Reservation conflict validation
- ✅ Support for consecutive bookings
- ✅ Full integration tests

## Project Structure

```

hotel-reservation-system/
├── src/
│   ├── main/java/com/pedrohk/
│   │   ├── Main.java                    # Main class
│   │   └── hotel/
│   │       ├── auth/
│   │       │   ├── RequiresAuth.java    # Security annotation
│   │       │   ├── SecurityContext.java # Security context (ThreadLocal)
│   │       │   └── SecurityInterceptor.java # Authentication interceptor
│   │       ├── model/
│   │       │   └── Reservation.java     # Reservation record
│   │       └── service/
│   │           └── BookingService.java  # Booking service
│   └── test/java/com/pedrohk/hotel/
│       └── HotelIntegrationTest.java    # Integration tests
├── pom.xml                               # Maven configuration
└── README.md

````

## Technologies

- **Java 25** - Main language
- **Maven** - Dependency manager
- **JUnit 5** - Testing framework
- **Java Records** - Immutable data structure for Reservation

## Components

### 1. SecurityContext
Manages the security context using `ThreadLocal` to store the current user's role.

```java
SecurityContext.setRole("USER");    // Set role
String role = SecurityContext.getRole(); // Get role
SecurityContext.clear();            // Clear context
````

### 2. RequiresAuth

Annotation to mark methods that require authentication.

```java
@RequiresAuth(role = "USER")
public Reservation bookRoom(...) { ... }
```

### 3. SecurityInterceptor

Intercepts method calls and validates permissions using reflection.

```java
interceptor.inspect(bookingService, "bookRoom");
```

### 4. BookingService

Main service responsible for managing room reservations.

**Features:**

* `bookRoom(roomNumber, start, end, email)` - Creates a new reservation

  * Validates date range
  * Checks room availability
  * Allows consecutive bookings
  * Prevents overlapping reservations

### 5. Reservation

Immutable record representing a reservation:

* `id` - Unique UUID
* `userEmail` - User email
* `roomNumber` - Room number
* `checkIn` - Check-in date
* `checkOut` - Check-out date

## Authorization Rules

| Role  | Permission                                    |
| ----- | --------------------------------------------- |
| USER  | Can make reservations                         |
| ADMIN | Full access (can book even without USER role) |
| None  | Access denied                                 |

## How to Use

### Setup

```bash
# Clone the project
git clone <repository-url>

# Navigate to the directory
cd hotel-reservation-system

# Compile
mvn clean compile

# Run tests
mvn test
```

### Usage Example

```java
// 1. Create service and interceptor
BookingService bookingService = new BookingService();
SecurityInterceptor interceptor = new SecurityInterceptor();

// 2. Set user role
SecurityContext.setRole("USER");

// 3. Validate permission
interceptor.inspect(bookingService, "bookRoom");

// 4. Make reservation
LocalDate checkIn = LocalDate.now();
LocalDate checkOut = LocalDate.now().plusDays(3);
Reservation res = bookingService.bookRoom(101, checkIn, checkOut, "user@example.com");

// 5. Clear context
SecurityContext.clear();
```

## Tests

The project includes integration tests in `HotelIntegrationTest.java`:

* ✅ `shouldAllowBookingWithUserRole` - User with USER role can book
* ✅ `shouldDenyBookingWithoutRole` - User without role is denied
* ✅ `adminShouldHaveFullAccess` - Admin can perform any booking
* ✅ `shouldBlockOverlappingReservations` - Rooms cannot have overlapping reservations
* ✅ `shouldAllowConsecutiveBookings` - Allows bookings starting when the previous one ends
* ✅ `shouldFailOnInvalidDateRange` - Rejects invalid dates (end date before start date)

### Run Tests

```bash
mvn test
```

## Security

### ThreadLocal Context

`SecurityContext` uses `ThreadLocal` to ensure isolation between requests in multi-threaded environments.

### Reflection Validation

`SecurityInterceptor` uses reflection to:

* Find the method in the object
* Check for the `@RequiresAuth` annotation
* Validate the required role

## Future Improvements

* [ ] Integration with web framework (Spring Boot)
* [ ] Persistent database
* [ ] Availability caching
* [ ] Email notifications
* [ ] Full REST API
* [ ] Cancellation handling
* [ ] Payment system
* [ ] Occupancy reports

## Requirements

* Java 25+
* Maven 3.8+
