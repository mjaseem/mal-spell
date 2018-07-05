package com.mjaseem.splitter;

import java.util.*;
import java.util.stream.Collectors;

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

    List<String> split() {
        Word firstWord = new Word("");
        return split(firstWord, 0).stream()
                .map(Word::getString)
                .collect(Collectors.toList());
    }

    private List<Word> split(Word lastWord, int firstCharPos) {
        Split split = new Split(lastWord, firstCharPos);
        if (memo.containsKey(split)) return memo.get(split);

        memo.put(split, new ArrayList<>()); //Empty list if no possible split found

        for (int i = inputString.length(); i> firstCharPos; i--) {
            String sub = inputString.substring(firstCharPos, i);
            Optional<Word> possibleWord = findViableNextWord(sub, lastWord, firstCharPos);
            if (!possibleWord.isPresent()) continue;
            List<Word> result = concatWithFollowingWords(firstCharPos, sub, possibleWord.get());
            memo.put(split, result);
            if (!result.isEmpty()) break;
        }

        return memo.get(split);
    }

    private List<Word> concatWithFollowingWords(int firstCharPos, String sub, Word possibleWord) {
        int nextCharPos = firstCharPos + sub.length();
        assert nextCharPos <= inputString.length();

        List<Word> result = new ArrayList<>();
        if (nextCharPos == inputString.length()) {
            result.add(possibleWord);
        } else {
            List<Word> nextSplitOutput = split(possibleWord, nextCharPos);
            if (!nextSplitOutput.isEmpty()) {
                result.add(possibleWord);
                result.addAll(nextSplitOutput);
            }
        }
        return result;
    }

    private Optional<Word> findViableNextWord(String sub, Word lastWord, int firstCharPos) {
        List<Word> possibleNextWords = searcher.search(sub);
        return possibleNextWords.stream()
                .filter(word -> containedInInputString(lastWord, word, firstCharPos))
                .findFirst();
    }

    private boolean containedInInputString(Word firstWord, Word secondWord, int firstCharPos) {
        Word joined = joiner.join(firstWord, secondWord);
        int start = Math.max(firstCharPos - 3, 0);
        int end = Math.min(firstCharPos + 3, inputString.length());
        String substringToTestOn = inputString.substring(start, end);
        return joined.getString().contains(substringToTestOn);
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
