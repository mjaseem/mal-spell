package com.mjaseem.splitter;

import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.ExtractedResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Should be improved to consider ാ and ആ as same letters
 */
public class FuzzySearcher implements Searcher {
    private List<String> dictionary;
    private static Logger log = LoggerFactory.getLogger(FuzzySearcher.class);

    public FuzzySearcher(List<String> dictionary) {

        this.dictionary = dictionary;
    }

    public List<Word> search(String query) {
        List<ExtractedResult> extractedResults = FuzzySearch.extractTop(query, dictionary, 5, 30); //TODO optimize limit and cutoff
        log.info("Result for query {} : {}", query, extractedResults);
        return extractedResults.stream()
                .map(ExtractedResult::getString)
                .map(Word::new)
                .collect(Collectors.toList());
    }
}
