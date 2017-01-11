package com.vbarvinko;

import java.util.Scanner;

public class Main {
    static char[][] walls;
    static Scanner sc;
    static char myC; // my color
    static char frC; // teammate Color

    static void initiateWalls () {
        walls = new char[19][19];
        int i = 0; // current index
        char w;
        String input = sc.next();
        for (int y = 2; y < 14; y++) { // for each of the seven rows
            for (int x = 1; x < 8; x++) { // get vertical walls
                w = input.charAt(i);
                i++;
                walls[2*x+2][2*y+1] = w;
            }
            for (int x = 1; x < 9; x++) { // get horizontal walls
                w = input.charAt(i);
                i++;
                walls[2*x+1][2*y+1] = w;
            }
        }
    }

    static void perspectifyWalls() {

    }

    static void initiateColors() {
        String c = sc.next();
        if (c.equals("Yellow")) myC = 0;
        else if (c.equals("Black")) myC = 1;
        else if (c.equals("White")) myC = 2;
        else myC = 3;
        frC = (char)((myC+2)%4);
    }

    static void initialTurn() {
        initiateWalls();
        initiateColors();

    }

    static void executeTurn() {

    }

    public static void main(String[] args) {
	// write your code here
        sc = new Scanner(System.in);
        initialTurn();
    }
}
