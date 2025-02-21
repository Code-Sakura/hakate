# hakate

## About

* A state managing library for kotlin
* It can manage state with dependency

### repository

* https://central.sonatype.com/artifact/net.kigawa/hakate

## Using

### Requirement

* kotlin
  * jvm
  * js

### Getting Started

#### 1. add to dependency

pom.xml

```pom.xml
<dependency>
  <groupId>net.kigawa</groupId>
  <artifactId>hakate</artifactId>
  <version>{version}</version>
</dependency>
```

build.gradle.kts

```build.gradle.kts
implementation("net.kigawa:hakate:{version}")
```

#### 2. write the code

new state
```kotlin
val initializer = HakateInitializer()
val dispatcher = initializer.newStateDispatcher()
val state = stateDispatcher.newState("default value")
```

collect state
```kotlin
stateDispatcher.useState {
  state.collect {
    value = it
    isSet = true
  }
}
```

child state
```kotlin
stateDispatcher.useState {
  val child = state.child { parent ->
    return@child "$parent-child"
  }
}
```

set state
```kotlin
state.set("new value")
```

## Author

* kigawa
    * contact@kigawa.net

# Making

## Version

### Example: 9.1.2

* **9**: major, destructive
* **1**: miner, new function
* **2**: miner, bug fix

## Dependency

### Artifact Dependency

* org.jetbrains.kotlinx:kotlinx-coroutines-core
* org.jetbrains.kotlin:kotlin-stdlib-js
* org.jetbrains.kotlin:kotlin-stdlib

### Dev Dependency

* cl.franciscosolis.sonatype-central-upload
* com.vanniktech.maven.publish
* gradle
* github
* io.github.gradle-nexus.publish-plugin:io.github.gradle-nexus.publish-plugin.gradle.plugin
* kotlin
* org.gradle.toolchains.foojay-resolver-convention
* org.jetbrains.dokka
* org.jetbrains.kotlin:kotlin-test-common
* org.jetbrains.kotlin:kotlin-test-annotations-common
* org.jetbrains.kotlin:kotlin-test-junit
* org.jetbrains.kotlinx:kotlinx-coroutines-test
* org.jetbrains.kotlin:kotlin-test
* org.jetbrains.kotlin.multiplatform
