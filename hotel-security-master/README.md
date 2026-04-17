# 🏨 Hotel Security – CSRF Protection (Spring Security 6)

A deep dive into **CSRF (Cross-Site Request Forgery) protection** using modern Spring Security.

This project demonstrates how to secure state-changing operations in a **Hotel Booking API**, ensuring that malicious external requests cannot manipulate sensitive data.

---

## 🧠 Concept – The Hotel Analogy

In the context of your hotel:

> CSRF is like a fraudster forging a guest’s signature on a room service order.

Imagine this scenario:

* A guest is logged into your hotel system.
* They open a malicious website in another tab.
* That site silently sends a request:

  > “Transfer this guest’s loyalty points to the attacker.”

Since the browser still holds valid session cookies, your system might trust the request.

🚨 **Without CSRF protection, the attack succeeds.**

---

## 🔐 How CSRF Protection Works (The Authenticity Seal)

Spring Security protects your application using a **CSRF Token mechanism**:

1. When a guest loads a page, the server generates a **unique secret token**.
2. Every state-changing request (**POST, PUT, DELETE**) must include this token.
3. If the token is:

   * ❌ Missing
   * ❌ Invalid

   → The request is **blocked (403 Forbidden)**.

💡 A malicious site cannot access this token, so forged requests fail.

---

## ⚖️ Pros & Cons

| Feature          | Pros                                                   | Cons                                                            |
| ---------------- | ------------------------------------------------------ | --------------------------------------------------------------- |
| Security         | Prevents unauthorized data changes and financial abuse | Adds complexity for APIs, mobile apps, and tools like Postman   |
| Default Behavior | Enabled by default in Spring Security                  | Missing token leads to `403 Forbidden`, even for valid requests |

---

## 🏗️ Project Overview

This project is a **complete CSRF-focused implementation** using:

* **Spring Boot 3.4**
* **Spring Security 6**
* **Java 21**
* **Integration Testing with MockMvc**

It demonstrates:

* ✅ Secure POST endpoints with CSRF enforcement
* ✅ Free access to GET endpoints (read-only)
* ✅ Authentication layer precedence over CSRF
* ✅ Behavior with and without tokens

---

## 📦 Tech Stack

* Java 21
* Spring Boot 3.4
* Spring Security 6
* Spring Web
* JUnit 5 + MockMvc
* Spring Security Test

---

## 🔑 Core Security Rules

```text
✔ GET, HEAD, OPTIONS → No CSRF required (read-only)
✔ POST, PUT, DELETE → CSRF Token REQUIRED
```

---

## 🔐 Security Configuration Highlights

* All endpoints require authentication
* Supports:

  * HTTP Basic Auth
  * Form Login
* In-memory user:

  ```text
  username: receptionist
  password: password
  ```

---

## 🌐 API Endpoints

### 📖 GET /api/v1/bookings

* Returns all bookings
* ✅ No CSRF required
* 🔐 Requires authentication

---

### 📝 POST /api/v1/bookings

* Creates a new booking
* 🔐 Requires authentication
* 🔒 Requires CSRF token

Example payload:

```json
{
  "guestName": "Pedro"
}
```

---

## 🧪 Integration Tests (Deep Validation)

The test suite validates real-world security behavior:

### ✅ 1. GET بدون CSRF

```text
Result: 200 OK
```

### ❌ 2. POST without CSRF

```text
Result: 403 Forbidden
```

### ✅ 3. POST with CSRF

```text
Result: 200 OK
```

### 🚫 4. Unauthenticated Request

```text
Result: 401 Unauthorized
```

💡 **Important Insight:**

> Authentication is evaluated *before* CSRF protection.

---

## ▶️ Running the Project

Run tests with:

```bash
mvn clean test
```

---

## 🧩 Architecture Overview

```
Client (Browser / Postman)
        ↓
Spring Security Filter Chain
        ↓
[ Authentication Layer ]
        ↓
[ CSRF Protection Layer ]
        ↓
BookingController (REST API)
```

---

## 🚀 Key Takeaways

* CSRF attacks exploit **trusted sessions**
* Spring Security blocks them using **tokens**
* Protection is:

  * ✔ Automatic
  * ✔ Layered
  * ✔ Essential for web apps
