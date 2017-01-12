package moreisless_Marty_Vlad;

import java.awt.*;
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

    public static void main(String[] args) throws InterruptedException {
	// write your code here
        sc = new Scanner(System.in);
    //    initialTurn();

        State init = new State();
        init.pieces = new Point[4];
        init.pieces[0] = new Point(0,0);
        init.pieces[1] = new Point(1,0);
        init.pieces[2] = new Point(0,1);
        init.pieces[3] = new Point(1,1);

        BruteSolver solver = new BruteSolver();
        State[] actions =  solver.solve(init);
        for(State state : actions) {
            drawBoard(state);
            Thread.sleep(10);
        }
    }

    static void drawBoard(State state)
    {
        System.out.flush();
        char[][] board = new char[8][8];
        for(int i= 0; i <4; i++)
            board[state.pieces[i].x][state.pieces[i].y] = 'O';
        for(int y = 0; y < 8; y++)
        {
            for(int x = 0; x < 8; x++)
            {
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
    }
}
