//package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by Vladimir on 1/13/2017.
 */
public class Walls {
    private static int[][] walls;

    // reads wall encoding and stores wall setup
    public static void initiateWalls (String in) {
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
    }

    // returns the amount of walls between neighbors a and b. Returns -1 for invalid input
    public static int getWall(Point a, Point b) {
        int ax = a.x -1;
        int ay = a.y - 1;
        int bx = b.x - 1;
        int by = b.y - 1;

        if (ax > 7 || ay > 7 || ax < 0 || ay < 0) return 512;
        if(bx > 7 || by > 7 || by < 0 || bx < 0) return 512;
        else if (Math.abs(ax-bx) + Math.abs(ay-by) != 1) return 512;
        else return (walls[ax+bx][ay+by]);
    }
}
