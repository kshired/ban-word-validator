# Ban word validator

Simple Java ban word validation library with allowed words. 
By using ahocorasick algorithm, it can detect ban words in a text faster than a simple iteration.

This library is inspired by an [article](https://techblog.woowahan.com/15764/) by [woowabros](https://github.com/woowabros).

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
