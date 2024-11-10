package io.github.kshired.banword;

public class DetectedWord {
    private final String character;
    private final int startPosition;
    private final int endPosition;

    public DetectedWord(String character, int startPosition, int endPosition) {
        this.character = character;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    public String getCharacter() {
        return character;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }
}
