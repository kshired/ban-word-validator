package io.github.kshired.banword;

public enum BanWordFilterPolicy {
    NUMBERS("[\\p{N}]"),
    WHITESPACES("[\\s]"),
    FOREIGN_LANGUAGES("[\\p{L}&&[^ㄱ-ㅎ가-힣ㅏ-ㅣa-zA-Z]]"),
    ;

    private final String regex;

    BanWordFilterPolicy(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
