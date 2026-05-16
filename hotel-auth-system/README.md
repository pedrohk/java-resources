# 🏨 Hotel Authorization System – Spring Security Filter Chain

A practical and modern implementation of **authorization rules using `SecurityFilterChain` and `HttpSecurity`** in Spring Security 6.

This project demonstrates how to control **who can access which areas** of a Hotel API, based on authentication and user roles.

---

## 🧠 Concept – The Hotel Analogy

In the context of your hotel:

> **Authorize HTTP Requests** is the security policy of your reception desk.

Your hotel has different access levels:

* 🏢 **Lobby (Public)** → Anyone can enter
* 🛏️ **Guest Rooms (Private)** → Only authenticated guests
* 🔐 **Management Office (Restricted)** → Only managers

---

## 🚧 SecurityFilterChain – The Security Barrier

The `SecurityFilterChain` acts like a **series of gates and guards** at the hotel entrance.

Every incoming request must pass through this chain before reaching your system.

💡 Think of it as:

> “Check ID → Verify access level → Allow or deny entry”

---

## ⚙️ HttpSecurity – The Manager’s Rules

`HttpSecurity` is where you define **who can access what**.

With Spring Security 6, rules are written using **Lambda DSL**, making them cleaner and more readable.

### 🧾 Example Rules

* `permitAll()` → Public endpoints (hotel info)
* `authenticated()` → Logged-in users only
* `hasRole("MANAGER")` → Restricted to managers
* `hasAnyRole("GUEST", "MANAGER")` → Shared access

---

## ⚖️ Pros & Cons

| Feature         | Pros                                               | Cons                                     |
| --------------- | -------------------------------------------------- | ---------------------------------------- |
| Centralization  | All security rules in one place                    | Incorrect rule order can break access    |
| Flexibility     | Fine-grained control over endpoints                | Debugging can be difficult               |
| Modern Standard | Replaces deprecated `WebSecurityConfigurerAdapter` | Too many filters may add slight overhead |

---

## 🏗️ Project Overview

This project demonstrates:

* ✅ Public, Guest, and Manager access levels
* ✅ Role-based authorization (`GUEST`, `MANAGER`)
* ✅ Centralized security configuration
* ✅ Integration tests simulating real authentication
* ✅ Modern Spring Security (6.4+) setup

---

## 📦 Tech Stack

* Java 21
* Spring Boot 3.4
* Spring Security 6
* Spring Web
* JUnit 5
* MockMvc + Spring Security Test

---

## 🔐 Security Rules

```text
/api/public/**   → Public (no authentication required)
/api/guest/**    → GUEST or MANAGER
/api/manager/**  → MANAGER only
Any other route  → Authenticated users
```

---

## 👥 Users (In-Memory)

| Username     | Password | Role    |
| ------------ | -------- | ------- |
| guest_user   | password | GUEST   |
| manager_user | admin123 | MANAGER |

---

## 🌐 API Endpoints

### 🏢 Public Area

```http
GET /api/public/info
```

✔ Accessible by anyone

---

### 🛏️ Guest Area

```http
GET /api/guest/my-room
```

✔ Requires authentication
✔ Accessible by:

* GUEST
* MANAGER

---

### 🔐 Manager Area

```http
GET /api/manager/reports
```

✔ Requires authentication
✔ Accessible only by:

* MANAGER

---

## 🧪 Integration Tests

This project includes a full test suite validating access rules:

### ✅ Public Access

* No authentication required → `200 OK`

### ✅ Guest Access

* Guest accessing own data → `200 OK`

### ✅ Manager Inheritance

* Manager accessing guest endpoints → `200 OK`

### ❌ Forbidden Access

* Guest accessing manager endpoints → `403 Forbidden`

### 🚫 Unauthorized Access

* No authentication on protected routes → `401 Unauthorized`

💡 **Key Insight:**

> Authorization rules are enforced after authentication through the Security Filter Chain.

---

## ▶️ Running the Project

Run tests with:

```bash
mvn clean test
```

---

## 🧩 Architecture Overview

```
Client (Browser / API Client)
        ↓
SecurityFilterChain
        ↓
[ Authentication Layer ]
        ↓
[ Authorization Rules (HttpSecurity) ]
        ↓
Controller (Hotel API)
```

---

## 🚀 Key Takeaways

* `SecurityFilterChain` is the **core of request security**
* `HttpSecurity` defines **authorization rules clearly**
* Role-based access ensures:

  * 🔐 Sensitive endpoints stay protected
  * 👥 Users access only what they should

---

## 🔜 Next Step

Now that you understand **endpoint-level authorization**, the next evolution is:

👉 Using `@PreAuthorize` for **method-level security**, giving you even more granular control.
