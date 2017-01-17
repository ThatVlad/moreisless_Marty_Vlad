package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Arrays;
import java.util.Scanner;

import static moreisless_Marty_Vlad.Colors.myC;

public class Main {
    static Scanner sc;

    static void initialTurn() {
       // Walls.initiateWalls(sc);
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

    public static void main(String[] args) throws InterruptedException {
        sc = new Scanner(System.in);
        //initialTurn();
      //  while(executeTurn()) {
            // continue as long as executeTurn() is not false (as long as it doesn't read "Quit")
       // }
        String walls = "0000100000000000000100000000000000100000000000000000000000000000100000000000000100000000000000100000000000000100";
        Walls.initiateWalls(null, walls);
        int[][] testA = new int[][] { {1,2,3}, {1,2,3}, {1,2,3}};
        int[][] testB = new int[][] { {1,2,3}, {1,2,3}, {1,2,3}};
        int[] a = new int[] { 1,2,3,4,5,6};
        int[] b= new int[] { 1,2,3,4,5,6};
        boolean ans = Arrays.deepEquals(testA, testB);
        boolean ans2 = Arrays.equals(a,b);
        Colors.myC = 0;
        State init = new State();
        init.pieces = new Point[4][4];
        init.pieces[Colors.myC] = new Point[4];
        init.pieces[Colors.myC][0] = new Point(8, 8);
        init.pieces[Colors.myC][1] = new Point(2, 1);
        init.pieces[Colors.myC][2] = new Point(1, 2);
        init.pieces[Colors.myC][3] = new Point(2, 2);

        int[] dx = new int[] { 1, 0,0,-1};
        int[] dy = new int[] { 0,-1,1,0};
        BruteSolver solver = new BruteSolver();
        while(true) {
            long start = System.currentTimeMillis();
            Move move = solver.solve(init);
            long dt = System.currentTimeMillis() - start;
            for(int i = 0; i < move.numMoves; i++) {
                init.pieces[Colors.myC][move.pieceId[i]].x += dx[move.moveId[i]];
                init.pieces[Colors.myC][move.pieceId[i]].y += dy[move.moveId[i]];
            }

            System.out.println("Move: " + move);
          //  System.out.println("X: " + init.pieces[0].x + " Y:" + init.pieces[0].y);
            drawBoard(init);
            //  Thread.sleep(100);
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
        char[][] board = new char[10][10];
        for(int i= 0; i <4; i++)
            board[state.pieces[Colors.myC][i].x][state.pieces[Colors.myC][i].y] = 'O';

        for(int y = 0; y < 10; y++)
        {
            for(int x = 0; x < 10; x++)
            {
                if(board[x][y] == '\u0000')
                    board[x][y] = '1';
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
    }
}
