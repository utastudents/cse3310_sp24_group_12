package uta.cse3310;

import java.util.Objects;

public class Word {
    String word;
    int startx;
    int endx;
    int starty;
    int endy;
    int length;

    public Word(String word, int startx, int endx, int starty, int endy, int length) {
        this.word = word;
        this.startx = startx;
        this.endx = endx;
        this.starty = starty;
        this.endy = endy;
        this.length = length;
    }

    @Override
    public boolean equals(Object word) {
        if (this == word)
            return true;
        if (word == null || getClass() != word.getClass())
            return false;
        Word wordObj = (Word) word;

        return (word.equals(wordObj.word) &&
                startx == wordObj.startx &&
                endx == wordObj.endx &&
                starty == wordObj.starty &&
                endy == wordObj.endy &&
                length == wordObj.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, startx, endx, starty, endy, length);
    }
}
