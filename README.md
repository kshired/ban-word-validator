# Ban word validator

[<img src="https://img.shields.io/maven-central/v/io.github.kshired/ban-word-validator.svg?label=latest%20release"/>](https://search.maven.org/search?q=g:io.github.kshired)
![GitHub](https://img.shields.io/github/license/kshired/ban-word-validator)

Simple Java ban word validation library with allowed words. 
By using ahocorasick algorithm, it can detect ban words in a text faster than a simple iteration.

This library is inspired by an [article](https://techblog.woowahan.com/15764/) by [woowabros](https://github.com/woowabros).

## Installation

### Gradle

```groovy
dependencies {
    implementation 'io.github.kshired:ban-word-validator:0.1.1'
}
```

### Gradle(kotlin)

```kotlin
dependencies {
    implementation("io.github.kshired:ban-word-validator:0.1.1")
}
```

### Maven

```xml
<dependency>
    <groupId>io.github.kshired</groupId>
    <artifactId>ban-word-validator</artifactId>
    <version>0.1.1</version>
</dependency>
```

## Usage

```java
import com.kshired.banwordvalidator.BanWordValidator;

public class Main {
    public static void main(String[] args) {
        BanWordValidator banWordValidator = new BanWordValidator(
            Set.of("ero"), // ban words
            Set.of("hero") // allowed words
        );
        
        System.out.println(banWordValidator.validate("erotic").getDetectedBanWords().size); // 1
        System.out.println(banWordValidator.validate("hero").getDetectedBanWords().size); // 0
    }
}
```
