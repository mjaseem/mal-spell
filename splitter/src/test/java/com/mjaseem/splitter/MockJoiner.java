package com.mjaseem.splitter;

public class MockJoiner implements Joiner {

    @Override
    public Word join(Word firstWord, Word secondWord) {
        if (firstWord.equals("വഴി") && secondWord.equals("ഓരത്തു")) {
            return new Word("വഴിയോരം");
        } else if (firstWord.equals("ഓരത്ത്") && secondWord.equals("ഊടെ")) {
            return new Word("ഓരത്തൂടെ");
        } else if (firstWord.equals("ഊടെ") && secondWord.equals("ആയി")) {
            return new Word("ഊടെയായി");
        } else {
            return new Word(firstWord.getString() + secondWord.getString());
        }
    }
}
