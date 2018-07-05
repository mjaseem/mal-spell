package com.mjaseem.splitter;

import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.testng.Assert.assertEquals;

public class SplitterTest {

    @Test
    public void testSplit() {
        Splitter splitter = new Splitter(new MockJoiner(), new FuzzySearcher(testDictionary()), "പെരുവഴിയോരത്തൂടെയായി");
        List<String> output = splitter.split();
        assertEquals(output.size(), 5);
        assertEquals(output.get(0), "പെരു");
        assertEquals(output.get(1), "വഴി");
        assertEquals(output.get(2), "ഓരത്ത്");
        assertEquals(output.get(3), "ഊടെ");
        assertEquals(output.get(4), "ആയി");
    }

    @Test
    public void testCharacters() {
        String word = "പെരുവഴിയോരത്തൂടെയായി";
        for (char c : word.toCharArray()) {
            System.out.println(c);
        }
    }

    private List<String> testDictionary() {
        return Stream.of("പെരു", "വഴി", "ഓരത്ത്", "ചോര", "ഊടെ", "ആയി").collect(Collectors.toList());
    }
}