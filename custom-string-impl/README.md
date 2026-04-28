# PString

A lightweight custom implementation of a string wrapper in Java, designed to provide common string operations while emphasizing immutability, clarity, and testability.

## Overview

`PString` is a simple abstraction over Java's native `String`, offering a controlled API for string manipulation. It is built with a focus on clean behavior, predictable results, and unit test coverage using JUnit 5.

This project demonstrates how core string operations can be implemented and validated from scratch.

## Features

* Basic string operations:

  * `length()`
  * `isEmpty()`
  * `charAt(int index)`
  * `toArray()`

* Transformations:

  * `reverse()`
  * `trim()`
  * `substring(int start, int end)`

* Search & replace:

  * `indexOf(char c)`
  * `replace(char oldChar, char newChar)`

* Iteration:

  * `foreach(Consumer<Character>)`

* Serialization:

  * `toJson()` (converts string to JSON format)

* Object behavior:

  * Proper `equals()` and `hashCode()` implementations

* Error handling:

  * Throws `IndexOutOfBoundsException` for invalid operations

## Example Usage

```java
PString ps = new PString("hello");

ps.length();              // 5
ps.reverse();             // "olleh"
ps.trim();                // "hello"
ps.substring(0, 2);       // "he"
ps.replace('l', 'x');     // "hexxo"
ps.toJson();              // "\"hello\""
```

## Testing

The project includes comprehensive unit tests using JUnit 5, covering:

* Basic properties
* Transformations
* Search and replace
* Iteration behavior
* JSON serialization
* Equality and hash consistency
* Exception handling

### Example Test

```java
@Test
void testReverse() {
    PString ps = new PString("hello");
    assertEquals(new PString("olleh"), ps.reverse());
}
```

## Design Principles

* **Immutability**: Operations return new `PString` instances instead of modifying the original.
* **Clarity over complexity**: Focus on readable and predictable implementations.
* **Test-driven mindset**: All behaviors are validated through unit tests.
* **Consistency with Java String API**: Familiar method naming and behavior.

## Pros

* Easy to understand and extend
* Fully test-covered behavior
* Clean and predictable API
* Great for learning and experimentation

## Cons

* Reinvents functionality already available in `java.lang.String`
* Not optimized for performance
* Limited compared to production-grade string libraries

## Requirements

* Java 21+
* Maven
* JUnit 5
