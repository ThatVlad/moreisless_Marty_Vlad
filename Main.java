package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import static moreisless_Marty_Vlad.Colors.myC;

public class Main {
    static Scanner sc;
    static BruteSolver solver;

    static void initialTurn() {
        Walls.initiateWalls(sc.next());
        Colors.initiateColors(sc);
        for(int i = 0; i < myC; i++) {
            TrueState.decodeTurnAndUpdate(sc.next(), i);
        }
        TrueState.turn = myC;

        // now find optimal state
        // lastMove is true if we have the ending-rush
        State init = new State(TrueState.pieces);
        Point[][] optMoveCoord = null;

        //long start = System.currentTimeMillis();
        Move opt = solver.solve(init);
        //long dt = System.currentTimeMillis() - start;

        // execute own calculated move and output it for the others to see
        optMoveCoord = opt.getMoveCoordinates();
        TrueState.updateSelf(optMoveCoord);

        System.out.println(MoveCoordinatesToString(optMoveCoord));
    }

    static boolean executeTurn() {
        // do necessary bookkeeping and updating before finding an optimal solution
        TrueState.turn += 4;
        boolean lastMove = false;
        for(int i = 0; i < 4; i++) {
            String input = sc.next();
            if (input.equals("Move")) {
                lastMove = true;
                break;
            }
            if (input.equals("Quit")) return false;
            TrueState.decodeTurnAndUpdate(input, i);
        }
        // now find optimal state
        // lastMove is true if we have the ending-rush
        State init = new State(TrueState.pieces);
        Point[][] optMoveCoord = null;

        if (!lastMove) {
            //long start = System.currentTimeMillis();
            Move opt = solver.solve(init);
            //long dt = System.currentTimeMillis() - start;

            // execute own calculated move and output it for the others to see
            optMoveCoord = opt.getMoveCoordinates();
            TrueState.updateSelf(optMoveCoord);
            System.out.println(MoveCoordinatesToString(optMoveCoord));
        }
        else {
            // while not all pieces are in position
            boolean first = true;
            while(init.fitness() > 0.001) {
                //long start = System.currentTimeMillis();
                Move opt = solver.solve(init);
                //long dt = System.currentTimeMillis() - start;
                optMoveCoord = opt.getMoveCoordinates();
                TrueState.updateSelf(optMoveCoord);
                if (!first) System.out.print(':');
                System.out.print(MoveCoordinatesToString(optMoveCoord));
                init = new State(TrueState.pieces);
                first = false;
            }
            System.out.println();
        }
        return !lastMove;
    }

    // translates a 3x2 matrix of coordinates to the appropriate output format
    public static String MoveCoordinatesToString (Point[][] coord) {
        // compute movecode for each move in the movelist (coord)
        String res = "";
        for (int i = 0; i < coord.length; i++) {
            if (coord[i][0] == null) continue; // no move stored
            if (i > 0) res += ':'; // piece-moving is seperated by :
            Point a = coord[i][0]; // start node
            res += (char)(a.y - 1 + 'a') + (a.x-1); // add starting position to string
            Point b = coord[i][1];
            res += (char)(b.y - 1 + 'a') + (b.x-1); // append end node to output
        }
        return res;
    }

    public static void wipmain(String[] args) throws InterruptedException {
        sc = new Scanner(System.in);
        solver = new BruteSolver();
        initialTurn();
        while(executeTurn()) {
            //continue as long as executeTurn() is not false (as long as it doesn't read "Quit")
        }
    }

    public static void main(String[] args) throws InterruptedException {
        sc = new Scanner(System.in);
        //initialTurn();
      //  while(executeTurn()) {
            // continue as long as executeTurn() is not false (as long as it doesn't read "Quit")
       // }
        String walls = "0000100000000000000100000000000000100000000000000000000000000000100000000000000100000000000000100000000000000100";
        Walls.initiateWalls(walls);

        Colors.myC = 0;
        while(true) {
            State init = new State();
            init.pieces = new Point[4][4];
            init.pieces[Colors.myC] = new Point[4];
            init.pieces[Colors.myC][0] = new Point(2, 1);
            init.pieces[Colors.myC][1] = new Point(2, 1);
            init.pieces[Colors.myC][2] = new Point(1, 2);
            init.pieces[Colors.myC][3] = new Point(2, 2);

            solver = new BruteSolver();
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
