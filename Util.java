package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Random;

/**
 * Created by Vladimir on 1/19/2017.
 */
public class Util {
    static Random rand = new Random();
    static int[] dx = new int[]{1, 0, 0, -1};
    static int[] dy = new int[]{0, -1, 1, 0};

    Point[] start = new Point[] {
            new Point(0,6),         //Start of yellow (left top)
            new Point(0,0),         //Start of black
            new Point(6,6),         //Start of white
            new Point(6,0),         //Start of red
    };

    Point[] goal = new Point[] {
            new Point(7,0),         //End goal of yellow
            new Point(7,7),         //End goal of black
            new Point(0,0),         //End goal of white
            new Point(0,7),         //End goal of red
    };
}
