//package moreisless_Marty_Vlad;

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

    static int readX(int bitstring, int piece)
    {
        return (bitstring>>>4*2*piece)&15;
    }
    static int readY(int bitstring, int piece)
    {
        return (bitstring>>>4*2*piece+4)&15;
    }
    static Point readPoint(int bitstring, int piece)
    {
        return new Point(readX(bitstring,piece), readY(bitstring,piece));
    }
    static int updateXY(int bitstring, int piece, int x, int y)
    {
        int mask = 255 << piece*2*4;
        int res = (bitstring & ~mask) | ((x<<2*4*piece) | (y << 2*4*piece+4)&mask);

        return res;
    }

}
