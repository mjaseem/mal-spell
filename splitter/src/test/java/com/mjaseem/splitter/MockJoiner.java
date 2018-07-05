package com.mjaseem.splitter;

public class MockJoiner implements Joiner {

    @Override
    public Word join(Word firstWord, Word secondWord) {
        String firstWordStr = firstWord.getString();
        String secondWordStr = secondWord.getString();
        if (firstWordStr.equals("വഴി") && secondWordStr.equals("ഓരത്ത്")) {
            return new Word("വഴിയോരത്ത്");
        } else if (firstWordStr.equals("ഓരത്ത്") && secondWordStr.equals("ഊടെ")) {
            return new Word("ഓരത്തൂടെ");
        } else if (firstWordStr.equals("ഊടെ") && secondWordStr.equals("ആയി")) {
            return new Word("ഊടെയായി");
        } else {
            return new Word(firstWordStr + secondWordStr);
        }
    }
}
