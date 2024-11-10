package io.github.kshired.banword;

import java.util.List;

public class BanWordValidationResult {
    private final String originalSentence;
    private final List<DetectedWord> detectedBanWords;

    public BanWordValidationResult(String originalSentence, List<DetectedWord> detectedBanWords) {
        this.originalSentence = originalSentence;
        this.detectedBanWords = detectedBanWords;
    }

    public String getOriginalSentence() {
        return originalSentence;
    }

    public List<DetectedWord> getDetectedBanWords() {
        return detectedBanWords;
    }
}
