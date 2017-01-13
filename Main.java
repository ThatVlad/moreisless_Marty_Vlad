package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Scanner;

import static moreisless_Marty_Vlad.Colors.myC;

public class Main {
    static Scanner sc;

    static void initialTurn() {
        Walls.initiateWalls(sc);
        Colors.initiateColors(sc);
        for(int i = 0; i < myC; i++) {
            TrueState.decodeTurnAndUpdate(sc.next(), i);
        }
        TrueState.turn = myC;
        //  >> find solution (actions), move is true if we have the ending-rush <<

        //TrueState.updateSelf(actions);
        //outputActions(actions);
    }

    static boolean executeTurn() {
        TrueState.turn += 4;
        boolean move = false;
        for(int i = 0; i < 4; i++) {
            String input = sc.next();
            if (input.equals("Move")) {
                move = true;
                break;
            }
            if (input.equals("Quit")) return false;
            TrueState.decodeTurnAndUpdate(input, i);
        }
        //  >> find solution (actions), move is true if we have the ending-rush <<

        //TrueState.updateSelf(actions);
        //outputActions(actions);
        return true;
    }

    // output chosen actions and update the TrueState
    static void outputActions(Point[][] actions) {
        String res = "";
        for (int i = 0; i < actions.length; i++) {
            if (actions[i][0] == null) return;
            Point a = actions[i][0];
            Point b = actions[i][1];
            if (i > 0) res += ':';
            res += (char)(a.y - 1 + 'a') + (a.x-1) + (char)(b.y - 1 + 'a') + (b.x-1);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        sc = new Scanner(System.in);
        initialTurn();
        while(executeTurn()) {
            // continue as long as executeTurn() is not false (as long as it doesn't read "Quit")
        }

        /*
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
        */
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
