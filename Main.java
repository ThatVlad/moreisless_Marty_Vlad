package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
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

        Colors.myC = 0;
        while(true) {
            State init = new State();
            init.pieces = new Point[4][4];
            init.pieces[Colors.myC] = new Point[4];
            init.pieces[Colors.myC][0] = new Point(2, 1);
            init.pieces[Colors.myC][1] = new Point(2, 1);
            init.pieces[Colors.myC][2] = new Point(1, 2);
            init.pieces[Colors.myC][3] = new Point(2, 2);

            BruteSolver solver = new BruteSolver();
            int numJumps = 0;
            while (init.fitness() != 0) {
                long start = System.currentTimeMillis();
                Move move = solver.solve(init);
                long dt = System.currentTimeMillis() - start;
                Point oldLoc;
                for (int i = 0; i < move.numMoves; i++) {
                    int piece = move.pieceId[i];
                    oldLoc= new Point(init.pieces[Colors.myC][piece].x, init.pieces[Colors.myC][piece].y);
                    int steps = move.moveId[i] >= 4 ? 2 : 1;
                    numJumps += steps-1;
                    init.pieces[Colors.myC][move.pieceId[i]].x += Util.dx[move.moveId[i]%4]*steps;
                    init.pieces[Colors.myC][move.pieceId[i]].y += Util.dy[move.moveId[i]%4]*steps;
                    drawBoard(init, oldLoc);
                    int BREAKPOINT = 123;
                }

              //  System.out.println("Time taken (ms): " + dt);
             //   System.out.println("TotalJumps: " + numJumps);
                //  System.out.println("X: " + init.pieces[0].x + " Y:" + init.pieces[0].y);
             //   drawBoard(init, new Point(0,0));
                //  Thread.sleep(100);
                int abc = 123;
            }

        }
    }

    static void drawBoard(State state, Point oldLoc)
    {
        System.out.flush();
        char[][] board = new char[10][10];
        for(int i= 0; i <4; i++) {
            board[state.pieces[Colors.myC][i].x][state.pieces[Colors.myC][i].y] = (char)(i + '0');
        }
        board[oldLoc.x][oldLoc.y] = 'X';

        for(int y = 0; y < 10; y++)
        {
            for(int x = 0; x < 10; x++)
            {
                if(board[x][y] == '\u0000')
                    board[x][y] = '-';
                if(x ==6) System.out.print(y==4? '-' : '|');
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
    }
}
