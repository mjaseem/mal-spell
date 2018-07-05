package com.mjaseem.splitter;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FuzzySearcherTest {

    @Test
    public void testSearch() {
        FuzzySearcher fuzzySearcher = new FuzzySearcher(testDictionary());
        assertTrue(containsString(fuzzySearcher.search("പെരു"), "പെരു"));
        assertTrue(containsString(fuzzySearcher.search("വഴി"), "വഴി"));
        assertTrue(containsString(fuzzySearcher.search("യോരത്ത്"), "ഓരത്ത്"));
        assertTrue(containsString(fuzzySearcher.search("ൂടെ"), "ഊടെ"));
        assertTrue(containsString(fuzzySearcher.search("യായി"), "ആയി"));
    }

    private boolean containsString(List<Word> words, String str) {
        return words.stream().map(Word::getString).anyMatch(str::equals);
    }

    private List<String> testDictionary() {
        return Stream.of("പെരു", "വഴി", "ഓരത്ത്", "ചോര", "ഊടെ", "ആയി").collect(Collectors.toList());
    }

    @Ignore
    @Test
    public void vowelModifierSimilarity() {
        ExtractedResult res = FuzzySearch.extractOne("ായി", Collections.singleton("ആയി"));
        assertEquals(res.getScore(), 100);
    }

}