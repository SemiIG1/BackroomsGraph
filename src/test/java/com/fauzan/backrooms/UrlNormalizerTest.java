package com.fauzan.backrooms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlNormalizerTest {

    @Test
    void givenHttpUrl_whenNormalizedUsingUrlNormalizer_thenHttpsIsReturned() {
        assertEquals("https://backrooms-wiki.wikidot.com/level-101/", UrlNormalizer.normalize("http://backrooms-wiki.wikidot.com/level-101/"));
    }

    @Test
    void givenNoTrailingSlashUrl_whenNormalizedUsingUrlNormalizer_thenTrailingSlashIsReturned() {
        assertEquals("https://backrooms-wiki.wikidot.com/level-101/", UrlNormalizer.normalize("https://backrooms-wiki.wikidot.com/level-101"));
    }

    @Test
    void givenUrlWithEmptySpace_whenNormalizedUsingUrlNormalizer_thenUrlIsTrimmed() {
        assertEquals("https://backrooms-wiki.wikidot.com/level-101/", UrlNormalizer.normalize("  https://backrooms-wiki.wikidot.com/level-101/  "));
    }
}