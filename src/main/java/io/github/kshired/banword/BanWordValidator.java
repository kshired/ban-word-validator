package io.github.kshired.banword;

import org.ahocorasick.trie.Trie;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BanWordValidator {
    private final Trie banWordTrie;
    private final Trie allowedWordTrie;

    public BanWordValidator(Set<String> banWords, Set<String> allowedWords) {
        this.banWordTrie = Trie.builder().addKeywords(banWords).build();
        this.allowedWordTrie = Trie.builder().addKeywords(allowedWords).build();
    }

    public BanWordValidationResult validate(String sentence, Set<BanWordFilterPolicy> filterPolicies) {
        String filteredInput = sentence;
        List<FilteredCharacter> filteredCharacters = new ArrayList<>();
        for (BanWordFilterPolicy filterPolicy : filterPolicies) {
            Pattern pattern = Pattern.compile(filterPolicy.getRegex());
            pattern.matcher(sentence).results().forEach(matcher -> {
                filteredCharacters.add(new FilteredCharacter(matcher.group(), matcher.start()));
            });
            filteredInput = pattern.matcher(filteredInput).replaceAll("*");
        }
        filteredCharacters.sort(Comparator.comparingInt(FilteredCharacter::getPosition));
        filteredInput = filteredInput.replace("*", "");

        List<DetectedWord> detectedBanWords = banWordTrie.parseText(filteredInput).stream()
                .map(keyword -> new DetectedWord(keyword.getKeyword(), keyword.getStart(), keyword.getEnd()))
                .collect(Collectors.toList());
        if (detectedBanWords.isEmpty()) {
            return new BanWordValidationResult(sentence, Collections.emptyList());
        }

        List<DetectedWord> allowedWords = allowedWordTrie.parseText(filteredInput).stream()
                .map(keyword -> new DetectedWord(keyword.getKeyword(), keyword.getStart(), keyword.getEnd()))
                .collect(Collectors.toList());

        List<DetectedWord> resultWords = detectedBanWords.stream()
                .filter(detectedWord ->
                        allowedWords.stream().noneMatch(allowedWord ->
                                allowedWord.getStartPosition() <= detectedWord.getStartPosition() && allowedWord.getEndPosition() >= detectedWord.getEndPosition()
                        )
                ).map(
                        detectedWord -> {
                            int startPosition = adjustPosition(detectedWord.getStartPosition(), filteredCharacters);
                            int endPosition = adjustPosition(detectedWord.getEndPosition(), filteredCharacters);
                            return new DetectedWord(sentence.substring(startPosition, endPosition + 1), startPosition, endPosition);
                        }
                ).collect(Collectors.toList());


        return new BanWordValidationResult(sentence, resultWords);
    }

    private int adjustPosition(int filteredPosition, List<FilteredCharacter> filteredCharacters) {
        int offset = 0;
        for (FilteredCharacter filteredCharacter : filteredCharacters) {
            if (filteredPosition + offset < filteredCharacter.getPosition()) break;
            offset += filteredCharacter.getCharacter().length();
        }
        return filteredPosition + offset;
    }
}
