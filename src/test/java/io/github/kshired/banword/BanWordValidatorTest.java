package io.github.kshired.banword;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BanWordValidatorTest {
    private final BanWordValidator banWordValidator = new BanWordValidator(
            Set.of("바보", "졸라"),
            Set.of("고르곤졸라")
    );

    @Test
    void success_check_has_ban_words() {
        // given
        String input = "이 졸라 바보야";

        // when
        BanWordValidationResult result = banWordValidator.validate(input, Collections.emptySet());

        // then
        assertEquals(input, result.getOriginalSentence());
        assertEquals(2, result.getDetectedBanWords().size());
        assertEquals("졸라", result.getDetectedBanWords().get(0).getCharacter());
        assertEquals(2, result.getDetectedBanWords().get(0).getStartPosition());
        assertEquals(3, result.getDetectedBanWords().get(0).getEndPosition());
        assertEquals("바보", result.getDetectedBanWords().get(1).getCharacter());
        assertEquals(5, result.getDetectedBanWords().get(1).getStartPosition());
        assertEquals(6, result.getDetectedBanWords().get(1).getEndPosition());
    }

    @Test
    void success_check_has_no_ban_words() {
        // given
        String input = "안녕하세요";

        // when
        BanWordValidationResult result = banWordValidator.validate(input, Collections.emptySet());

        // then
        assertEquals(input, result.getOriginalSentence());
        assertEquals(0, result.getDetectedBanWords().size());
    }

    @Test
    void success_check_has_no_ban_words_with_allowed_words() {
        // given
        String input = "고르곤졸라";

        // when
        BanWordValidationResult result = banWordValidator.validate(input, Collections.emptySet());

        // then
        assertEquals(input, result.getOriginalSentence());
        assertEquals(0, result.getDetectedBanWords().size());
    }

    @Test
    void success_check_has_ban_words_with_allowed_words() {
        // given
        String input = "고르곤졸라 졸라 맛있음";

        // when
        BanWordValidationResult result = banWordValidator.validate(input, Collections.emptySet());

        // then
        assertEquals(input, result.getOriginalSentence());
        assertEquals(1, result.getDetectedBanWords().size());
        assertEquals("졸라", result.getDetectedBanWords().get(0).getCharacter());
        assertEquals(6, result.getDetectedBanWords().get(0).getStartPosition());
        assertEquals(7, result.getDetectedBanWords().get(0).getEndPosition());
    }

    @Test
    void success_check_with_filter_policy() {
        // given
        String input = "고르곤졸라는 졸1231라 바보같이 맛있음";

        // when
        BanWordValidationResult result = banWordValidator.validate(input, Set.of(BanWordFilterPolicy.NUMBERS));

        // then
        assertEquals(input, result.getOriginalSentence());
        assertEquals(2, result.getDetectedBanWords().size());
        assertEquals("졸1231라", result.getDetectedBanWords().get(0).getCharacter());
        assertEquals(7, result.getDetectedBanWords().get(0).getStartPosition());
        assertEquals(12, result.getDetectedBanWords().get(0).getEndPosition());
        assertEquals("바보", result.getDetectedBanWords().get(1).getCharacter());
        assertEquals(14, result.getDetectedBanWords().get(1).getStartPosition());
        assertEquals(15, result.getDetectedBanWords().get(1).getEndPosition());
    }

    @Test
    void success_check_with_multiple_filter_policy() {
        // given
        String input = "고르곤졸라는 졸1231라 바 보같이 맛있음";

        // when
        BanWordValidationResult result = banWordValidator.validate(input, Set.of(BanWordFilterPolicy.NUMBERS, BanWordFilterPolicy.WHITESPACES));

        // then
        assertEquals(input, result.getOriginalSentence());
        assertEquals(2, result.getDetectedBanWords().size());
        assertEquals("졸1231라", result.getDetectedBanWords().get(0).getCharacter());
        assertEquals(7, result.getDetectedBanWords().get(0).getStartPosition());
        assertEquals(12, result.getDetectedBanWords().get(0).getEndPosition());
        assertEquals("바 보", result.getDetectedBanWords().get(1).getCharacter());
        assertEquals(14, result.getDetectedBanWords().get(1).getStartPosition());
        assertEquals(16, result.getDetectedBanWords().get(1).getEndPosition());
    }
}
