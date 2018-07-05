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
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class WordSearcherTest {

    @Test
    public void testSearch() {
        WordSearcher wordSearcher = new WordSearcher(testDictionary());
        assertTrue(containsString(wordSearcher.search("പെരു"), "പെരു"));
        assertTrue(containsString(wordSearcher.search("വഴി"), "വഴി"));
        assertTrue(containsString(wordSearcher.search("യോരത്ത്"), "ഓരത്ത്"));
        assertTrue(containsString(wordSearcher.search("ൂടെ"), "ഊടെ"));
        assertTrue(containsString(wordSearcher.search("യായി"), "ആയി"));
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

    @Test
    public void superStringSearch() {
        ExtractedResult res = FuzzySearch.extractOne("പെരുവഴിയോരത്തൂടെയായി", Collections.singleton("വഴി"));
        System.out.println( FuzzySearch.partialRatio("പെരുവഴിയോരത്തൂടെയായി","വഴി") );
        System.out.println( FuzzySearch.partialRatio("വഴി","പെരുവഴിയോരത്തൂടെയായി") );
        System.out.println( FuzzySearch.tokenSetPartialRatio("പെരുവഴിയോരത്തൂടെയായി","വഴി") );
        System.out.println( FuzzySearch.tokenSetPartialRatio("വഴി","പെരുവഴിയോരത്തൂടെയായി") );
        System.out.println( FuzzySearch.ratio("പെരുവഴിയോരത്തൂടെയായി","വഴി") );
        System.out.println( FuzzySearch.ratio("വഴി","പെരുവഴിയോരത്തൂടെയായി") );
        System.out.println( FuzzySearch.weightedRatio("പെരുവഴിയോരത്തൂടെയായി","വഴി") );
        System.out.println( FuzzySearch.weightedRatio("വഴി","പെരുവഴിയോരത്തൂടെയായി") );
        System.out.println( FuzzySearch.tokenSetRatio("പെരുവഴിയോരത്തൂടെയായി","വഴി") );
        System.out.println( FuzzySearch.tokenSetRatio("വഴി","പെരുവഴിയോരത്തൂടെയായി") );
        System.out.println(res);
        assertNotEquals(res.getScore(), 100);
    }

}