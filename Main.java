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

        HashSet<Node> nodes = new HashSet<>();
        State testA= new State();
        testA.pieces = new Point[4][4];
        testA.pieces[0][1] = new Point(3,3);
        testA.pieces[0][2]= new Point(3,3);
        testA.pieces[0][3]= new Point(9,6);
        testA.pieces[0][0] = new Point(10,3);

        State testB= new State();
        testB.pieces = new Point[4][4];
        testB.pieces[0][1]= new Point(3,3);
        testB.pieces[0][2]= new Point(3,3);
        testB.pieces[0][3]= new Point(9,6);
        testB.pieces[0][0] = new Point(10,3);
        nodes.add(testA.node);

        if(nodes.contains(testB.node))
        {
            int abc=13;
        }
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
            Point[] oldLocs = new Point[4];
            for(int i = 0; i < move.numMoves; i++) {
                int piece = move.pieceId[i];
                if (oldLocs[piece] == null)
                    oldLocs[piece] = new Point(init.pieces[Colors.myC][piece].x, init.pieces[Colors.myC][piece].y);
                int a = Colors.myC;
                init.pieces[Colors.myC][move.pieceId[i]].x += dx[move.moveId[i]];
                init.pieces[Colors.myC][move.pieceId[i]].y += dy[move.moveId[i]];
            }

            System.out.println("Time taken (ms): " + dt);
          //  System.out.println("X: " + init.pieces[0].x + " Y:" + init.pieces[0].y);
            drawBoard(init, oldLocs);
            //  Thread.sleep(100);
            int abc=123;
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

    static void drawBoard(State state, Point oldLocs[])
    {
        System.out.flush();
        char[][] board = new char[10][10];
        for(int i= 0; i <4; i++) {
            if (oldLocs[i] != null)
                board[oldLocs[i].x][oldLocs[i].y] = 'X';
            board[state.pieces[Colors.myC][i].x][state.pieces[Colors.myC][i].y] = (char)(i + '0');
        }

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
