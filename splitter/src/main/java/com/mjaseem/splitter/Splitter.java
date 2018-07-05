package com.mjaseem.splitter;

import java.util.*;
import java.util.stream.Collectors;

/*
Attempts all possible splits.
TODO Optimize to quit when first split is found, prioritizing long words

 */
public class Splitter {

    private final Joiner joiner;
    private final Searcher searcher;
    private String inputString;

    private Map<Split, List<Word>> memo;

    Splitter(Joiner joiner, Searcher searcher, String inputString) {
        this.joiner = joiner;
        this.searcher = searcher;
        this.inputString = inputString;
        memo = new HashMap<>();
    }

    public List<String> split() {
        Word firstWord = new Word("");
        return split(firstWord, 0).stream()
                .map(Word::getString)
                .collect(Collectors.toList());
    }

    private List<Word> split(Word lastWord, int firstCharPos) { //TODO refactor crappy code
        Split split = new Split(lastWord, firstCharPos);
        if (memo.containsKey(split)) return memo.get(split);

        memo.put(split, new ArrayList<>()); //Empty list if no possible split found

        for (int i = inputString.length(); i > firstCharPos; i--) {
            String sub = inputString.substring(firstCharPos, i);
            Optional<Word> possibleWord = findViableNextWord(sub, lastWord);

            if (!possibleWord.isPresent()) continue;
            Word secondWord = possibleWord.get();
            int nextCharPos = firstCharPos + sub.length();
            assert nextCharPos <= inputString.length();
            if (nextCharPos == inputString.length()) {
                memo.put(split, Collections.singletonList(secondWord));
                break;
            } else {
                List<Word> nextSplitOutput = split(secondWord, nextCharPos);
                if (nextSplitOutput.isEmpty()) continue;
                List<Word> result = new ArrayList<>();
                result.add(secondWord);
                result.addAll(nextSplitOutput);
                memo.put(split, result);
                break;
            }
        }

        return memo.get(split);
    }

    private Optional<Word> findViableNextWord(String sub, Word lastWord) {
        List<Word> possibleFirstWords = searcher.search(sub);
        return possibleFirstWords.stream()
                .map(w -> joiner.join(lastWord, w))
                .filter(w -> containedInInputString(w))
                .findFirst();
    }

    private boolean containedInInputString(Word joined) {
        return inputString.contains(joined.getString());// FIXME dumb placeholder. Replace with proper logic
    }

    private class Split {
        Integer nextPos;
        Word lastWord;

        Split(Word lastWord, int nextPos) {

            this.lastWord = lastWord;
            this.nextPos = nextPos;
        }

        @Override
        public int hashCode() { //FIXME better hashcode
            return nextPos.hashCode() ^ lastWord.getString().hashCode();
        }
    }

}
