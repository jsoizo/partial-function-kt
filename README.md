# partial-function-kt

![Maven Central Version](https://img.shields.io/maven-central/v/com.jsoizo/partial-function-kt)

This library provides Scala's PartialFunction and a set of functions that take it as an argument in Kotlin.  
Partial functions are those that might not provide an answer for every possible input, unlike total functions which offer a result for each input.

## Getting Started

### Requirements

- JDK version 8 or above

### Installation

#### Gradle

Add the dependency to your project:

```kotlin
dependencies {
    implementation("com.jsoizo:partial-function-kt:0.1.1")
}
```

### Usage

To define a PartialFunction

```kotlin
val devide = partialFunction<Int, Int>(
    { it != 0 },  // idDefinedAt: Checks if a value is contained in the function's domain
    { 42 / it }   // apply:       Apply the body of this function to the argument
)
```

To chain PartialFunctions for complex logic

```kotlin
val add100 = partialFunction<Int, Int>(
    { it >= 0 },
    { it + 100 }
)

val devide42AndAdd100 = devide42 andThen add100
val devide42OrAdd100 = devide42 orElse add100
```

To use the defined PartialFunction with Array, Result, or Nullable operations

```kotlin
val numbers = listOf(1, 2, 0, -4, -3)
val collected = numbers.collect(devide42AndAdd100)
// listOf(142, 121, -10, -14)
```

## License

This project is licensed under the MIT License.