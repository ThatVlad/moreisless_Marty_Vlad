package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Scanner;

public class Main {
    static Scanner sc;
    static BruteSolver solver;
    static int COMP_TIME = 99999999;

    static void initialTurn() {
        System.err.println("Starting initial turn...");
        Walls.initiateWalls(sc.next());
        Colors.initiateColors(sc);
        for(int i = 0; i < Colors.myC; i++) {
            TrueState.decodeTurnAndUpdate(sc.next(), i);
        }
        TrueState.turn = Colors.myC;
        //  >> find solution (actions), move is true if we have the ending-rush <<

        // now find optimal state
        // lastMove is true if we have the ending-rush
        State init = new State(TrueState.pieces);
        Point[][] optMoveCoord = null;

        //long start = System.currentTimeMillis();
        Move opt = solver.solve(init, Colors.myC,COMP_TIME);
        //long dt = System.currentTimeMillis() - start;

        // execute own calculated move and output it for the others to see
        optMoveCoord = opt.getMoveCoordinates();
        TrueState.updateSelf(optMoveCoord);
        String output = MoveCoordinatesToString(optMoveCoord);
        System.err.println("OUT:" + output);
        System.out.println(output);
    }

    static boolean executeTurn() {
        System.err.println("____________________________________________");
        System.err.println("Starting executeTurn");
        // do necessary bookkeeping and updating before finding an optimal solution
        TrueState.turn += 4;
        System.err.println("Current turn: " + TrueState.turn);
        System.err.println("Reading and decoding previous actions from Caia...");
        boolean lastMove = false;
        for(int i = 0; i < 3; i++) {
            String input = sc.next();
            if (input.equals("Move")) {
                lastMove = true;
                break;
            }
            if (input.equals("Quit")) {
                System.err.println("Caia tells to Quit");
                return false;
            }
            TrueState.decodeTurnAndUpdate(input, (Colors.myC + 1 + i)%4);
        }
        System.err.println("Decoding successful");
        // now find optimal state
        // lastMove is true if we have the ending-rush
        State init = new State(TrueState.pieces);
        Point[][] optMoveCoord = null;

        if (!lastMove) {
            //long start = System.currentTimeMillis();
            System.err.println("Starting solver.solve()...");
            Move opt = solver.solve(init,Colors.myC,COMP_TIME);
            System.err.println("OPT found");
            //long dt = System.currentTimeMillis() - start;

            // execute own calculated move and output it for the others to see
            optMoveCoord = opt.getMoveCoordinates();
            TrueState.updateSelf(optMoveCoord);
            String output = MoveCoordinatesToString(optMoveCoord);
            System.err.println("OUT:" + output);
            System.out.println(output);
        }
        else {
            System.err.println("This is the last move!");
            // while not all pieces are in position
            boolean first = true;

            System.err.print("OUT:");
            while(init.fitness(Colors.myC) > 0.001) {
                //long start = System.currentTimeMillis();
                Move opt = solver.solve(init,Colors.myC,COMP_TIME);
                //long dt = System.currentTimeMillis() - start;
                optMoveCoord = opt.getMoveCoordinates();
                TrueState.updateSelf(optMoveCoord);
                if (!first) {
                    System.err.println(':');
                    System.out.print(':');
                }
                String output = MoveCoordinatesToString(optMoveCoord);
                System.err.print(output);
                System.out.print(output);
                init = new State(TrueState.pieces);
                first = false;
            }
            System.err.println();
            System.out.println();
        }
        return !lastMove;
    }

    // translates a 3x2 matrix of coordinates to the appropriate output format
    public static String MoveCoordinatesToString (Point[][] coord) {
        if (coord[0][0] == null) {
            System.err.println("ERROR: Empty move coordinates given to MoveCoordinatesToString");
            System.out.println("kill me");
        }
        for (int i = 0; i < coord.length; i++) {
            System.err.print("Turn " + TrueState.turn + ", move " + i + ": ");
            if (coord[i][0] != null) {
                for (int j = 0; j < coord[i].length; j++) {
                    System.err.print(coord[i][j].x + "," + coord[i][j].y + " : ");
                }
            }
            else System.err.print("EMPTY");
            System.err.println();
        }
        // compute movecode for each move in the movelist (coord)
        String res = "";
        for (int i = 0; i < coord.length; i++) {
            if (coord[i][0] == null) continue; // no move stored
            if (i > 0) res += ':'; // piece-moving is seperated by :
            Point a = coord[i][0]; // start node
            res= res + (char)(a.y - 1 + 'a') + (a.x); // add starting position to string
            Point b = coord[i][1];
            res = res + (char)(b.y - 1 + 'a') + (b.x); // append end node to output
        }
        System.err.println("Turn " + TrueState.turn + " code: "+ res);
        return res;
    }

    public static void main2(String[] args) throws InterruptedException {
        sc = new Scanner(System.in);
        TrueState.initializeTrueState();
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

        Colors.myC = 1;
        while(true) {
            State init = new State();
            Point[] startLocs = new Point[] {
                    new Point(1,7),         //End goal of yellow
                    new Point(1,1),         //End goal of black
                    new Point(7,7),         //End goal of white
                    new Point(7,1),         //End goal of red
            };
            Point[][] pieces = new Point[4][4];
            for(int iPlayer = 0; iPlayer<4; iPlayer++) {
                pieces[iPlayer] = new Point[4];
                for(int dx = 0; dx < 2; dx++)
                    for(int dy = 0; dy < 2; dy++)
                        pieces[iPlayer][2*dx+dy] = new Point(startLocs[iPlayer].x + dx, startLocs[iPlayer].y+dy);
            }

            init.pieces = new int[4];
         /*   for(int iPlayer = 0; iPlayer<4; iPlayer++) {
                for (int m = 0; m < 4; m++)
                    init.pieces[iPlayer] = Util.updateXY(init.pieces[iPlayer], m, pieces[iPlayer][m].x, pieces[iPlayer][m].y);
            }*/
            for (int m = 0; m < 4; m++)
                init.pieces[Colors.myC] = Util.updateXY(init.pieces[Colors.myC], m, pieces[Colors.myC][m].x, pieces[Colors.myC][m].y);

            solver = new BruteSolver();
            int numMoves = 0;
            long totTime = 0;
            while (init.fitness(Colors.myC) != 0) {
                long start = System.currentTimeMillis();
                Move move = solver.solve(init, Colors.myC,COMP_TIME);
                long dt = System.currentTimeMillis() - start;
                totTime += dt;
                Point oldLoc;
                for (int i = 0; i < move.numMoves; i++) {
                    int piece = move.pieceId[i];
                    oldLoc= Util.readPoint(init.pieces[Colors.myC], piece);

                    int steps = move.moveId[i] >= 4 ? 2 : 1;

                    Point newLoc= new Point(Util.dx[move.moveId[i]%4]*steps+oldLoc.x, Util.dy[move.moveId[i]%4]*steps+oldLoc.y);
                    init.pieces[Colors.myC] = Util.updateXY(init.pieces[Colors.myC], move.pieceId[i], newLoc.x, newLoc.y);

                    System.out.println("===============================================");
                    drawBoard(init, oldLoc);
                    numMoves++;
                    int BREAKPOINT = 123;
                }
                System.out.println("Turn time: " + dt);
            }
            System.out.println("Total time: " + totTime);
            System.out.println("--------------------------");
            if(numMoves != 26)
            {
                int BREAK =123;
            }
        }
    }

    static void drawBoard(State state, Point oldLoc)
    {
        char[][] board = new char[10][10];
        for(int j =0; j<4; j++) {
            for (int i = 0; i < 4; i++) {
                board[Util.readX(state.pieces[j], i)][Util.readY(state.pieces[j], i)] = (char) (j + '0');
            }
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