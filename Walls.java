package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by Vladimir on 1/13/2017.
 */
public class Walls {
    private static int[][] walls;

    // reads wall encoding and stores wall setup
    public static void initiateWalls (Scanner sc) {
        walls = new int[15][15];
        int i = 0; // current input index
        int w;
        String input = sc.next();
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
        a.x -= 1;
        a.y -= 1;
        b.x -= 1;
        b.y -= 1;
        if (a.x < 0 || a.y < 0 || b.x > 7 || b.y > 7) return 512;
        else if (Math.abs(a.x-b.x) + Math.abs(a.y-b.y) != 1) return 512;
        else return (walls[a.x+b.x][a.y+b.y]);
    }
}
