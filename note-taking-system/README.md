# Note Taking System

A simple and efficient note-taking system built in Java, featuring immutable data modeling, version control, and bidirectional synchronization.

## Overview

This project demonstrates a lightweight note management system with:

- ✅ Immutable domain model using Java Records  
- ✅ Version-based conflict resolution  
- ✅ Thread-safe in-memory storage  
- ✅ Bidirectional synchronization between clients  
- ✅ Comprehensive unit and integration tests  

## Project Structure

```

note-taking-system/
├── src/
│   ├── main/java/com/pedrohk/notes/
│   │   ├── model/
│   │   │   └── Note.java             # Immutable note record
│   │   └── service/
│   │       ├── NoteService.java      # Core note operations
│   │       └── SyncService.java      # Synchronization logic
│   └── test/java/com/pedrohk/notes/
│       └── NoteSystemTest.java       # Tests
├── pom.xml                            # Maven configuration
└── README.md

````

## Technologies

- **Java 25** - Main programming language  
- **Maven** - Build and dependency management  
- **JUnit 5** - Testing framework  
- **Java Records** - Immutable data structures  

## Core Concepts

### 1. Immutable Notes

The system uses a `record` to represent notes:

- Each note is immutable  
- Updates generate a new instance  
- Version is incremented automatically  

```java
Note updated = existing.update("New Title", "New Content");
````

### 2. Versioning

Each note contains a `version` field:

* Starts at `1`
* Increments on every update
* Used for conflict resolution during sync

### 3. Thread-Safe Storage

`NoteService` uses:

```java
ConcurrentHashMap<UUID, Note>
```

This ensures safe concurrent access in multi-threaded environments.

### 4. Bidirectional Sync

`SyncService` synchronizes two note stores:

* Copies missing notes
* Resolves conflicts using **highest version wins**
* Works in both directions (local ↔ remote)

## Features

### NoteService

* `addNote(title, content)` → Create a new note
* `editNote(id, title, content)` → Update note (increments version)
* `deleteNote(id)` → Remove note
* `getAllNotes()` → Retrieve notes sorted by last modified (descending)

### SyncService

* `sync(local, remote)`

  * Merges two data sources
  * Resolves conflicts based on version
  * Ensures both sides reach consistency

## How to Use

### Setup

```bash
# Clone the repository
git clone <repository-url>

# Enter project directory
cd note-taking-system

# Compile project
mvn clean compile

# Run tests
mvn test
```

### Example Usage

```java
NoteService service = new NoteService();

// Create note
Note note = service.addNote("Title", "Content");

// Edit note
Note updated = service.editNote(note.id(), "New Title", "Updated Content");

// List notes
var notes = service.getAllNotes();
```

### Sync Example

```java
NoteService clientA = new NoteService();
NoteService clientB = new NoteService();

clientA.addNote("Note A", "Content A");
clientB.addNote("Note B", "Content B");

SyncService syncService = new SyncService();
syncService.sync(clientA, clientB);
```

## Tests

The project includes comprehensive tests:

* ✅ Create and edit notes
* ✅ Delete operations and error handling
* ✅ Bidirectional synchronization
* ✅ Conflict resolution using versioning
* ✅ Sorting by last modified date

Run tests with:

```bash
mvn test
```

## Design Trade-offs

### Pros

* ✔ Simple and easy to understand architecture
* ✔ Immutable data model reduces bugs
* ✔ Thread-safe without complex locking
* ✔ Clear and predictable conflict resolution
* ✔ No external dependencies required

### Cons

* ❌ In-memory storage (no persistence)
* ❌ Last-write-wins strategy may overwrite changes
* ❌ No user authentication or access control
* ❌ No partial merge of note content
* ❌ Not optimized for large-scale distributed systems

## Future Improvements

* [ ] Persistent storage (database or file system)
* [ ] Encryption before synchronization
* [ ] REST API (Spring Boot)
* [ ] User authentication and authorization
* [ ] Conflict merging strategies (not just version-based)
* [ ] Real-time synchronization
* [ ] Offline-first support

## Requirements

* Java 25+
* Maven 3.8+
