package uta.cse3310;

import java.awt.Point;
import java.util.ArrayList;
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

        return (this.word.equals(wordObj.word) &&
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

    public ArrayList<Point> getPoints() {
        ArrayList<Point> points = new ArrayList<Point>();
        if (startx == endx) {
            for (int i = starty; i <= endy; i++) {
                points.add(new Point(startx, i));
            }
        } else if (starty == endy) {
            for (int i = startx; i <= endx; i++) {
                points.add(new Point(i, starty));
            }
        } else {
            int x = startx;
            int y = starty;
            while (x <= endx && y <= endy) {
                points.add(new Point(x, y));
                x++;
                y++;
            }
        }
        return points;
    }
}
