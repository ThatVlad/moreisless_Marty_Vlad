package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by Vladimir on 1/13/2017.
 */
public class Walls {
    private static int[][] walls;

    // reads wall encoding and stores wall setup
    public static void initiateWalls (Scanner sc, String in) {
        walls = new int[15][15];
        int i = 0; // current input index
        int w;
        String input = in;
        for (int y = 0; y < 7; y++) { // for each of the seven rows
            for (int x = 0; x < 7; x++) { // get vertical walls
                w = input.charAt(i)-'0';
                i++;
                walls[2*x+1][2*y] = w;
            }
            for (int x = 0; x < 8; x++) { // get horizontal walls
                w = input.charAt(i)-'0';
                i++;
                walls[2*x][2*y+1] = w;
            }
        }
        for (int x = 0; x < 7; x++) { // get vertical walls of last row
            w = input.charAt(i)-'0';
            i++;
            walls[2*x+1][14] = w;
        }
        int abc = 2143;
    }

    // returns the amount of walls between neighbors a and b. Returns -1 for invalid input
    public static int getWall(Point a, Point b) {
        int ax = a.x -1;
        int ay = a.y - 1;
        int bx = b.x - 1;
        int by = b.y - 1;

        int test = (walls[ax+bx][ay+by]);
        if(test == 0)
        {
            int abc =123;
        }
        if(test == 1)
        {
            int bcd=231;
        }
        if (ax < 0 || ay < 0 || bx > 7 || by > 7) return 512;
        else if (Math.abs(ax-bx) + Math.abs(ay-by) != 1) return 512;
        else return (walls[ax+bx][ay+by]);
    }
}
