package moreisless_Marty_Vlad;

import java.awt.*;

/**
 * Created by Vladimir on 1/13/2017.
 */
public class TrueState {
    static int board [][];
    static Point pieces [][];
    static int turn;

    static public void initializeTrueState() {
        turn = 0;
        board = new int[10][10];
        for (int i = 0; i < 10; i ++ ) {
            // build walls around board
            board[0][i] = 4;
            board[i][0] = 4;
            board[9][i] = 4;
            board[i][9] = 4;
        }
        pieces = new Point[4][4];

        // initialize piece positions
        int[] xori = {1,1,7,7};
        int[] yori = {7,1,7,1};
        int[] xoff = {0,1,1,0};
        int[] yoff = {0,0,1,1};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int x = xori[i]+xoff[j];
                int y = yori[i]+yoff[j];
                pieces[i][j] = new Point(x,y);
                board[x][y] = i+10;
            }
        }
    }

    // decodes turn of color c, and updates truestate accordingly
    static void decodeTurnAndUpdate (String input, int c) {
        System.err.println("Color " + c + " move: " + input);
        if (input.equals("Nil")) return;
        int moveAmnt = (input.length()+1)/5;
        for (int m = 0; m < moveAmnt; m++) {
            // decode start and end destination
            Point a = new Point(input.charAt(1+m*5)-'0',input.charAt(m*5)-'a'+1);
            Point b = new Point(input.charAt(3+m*5)-'0',input.charAt(2+m*5)-'a'+1);
            // update board and player pieces lists
            c = board[a.x][a.y]-10;
            board[a.x][a.y] = 0;
            board[b.x][b.y] = c+10;
            for (int i = 0; i < 4; i++) {
                if (pieces[c][i].x == a.x && pieces[c][i].y == a.y) {
                    pieces[c][i].x = b.x;
                    pieces[c][i].y = b.y;
                    break;
                }
            }
            System.err.println("(" + a.x + "," + a.y + ") to (" + b.x + "," + b.y + ")");
        }
    }

    // executes the move by moving our own pieces
    static void updateSelf (Point[][] coord) {
        // execute move for each move in the movelist (coord)
        for (int i = 0; i < 3; i++) {
            if (coord[i][0] == null) continue; // no move stored
            Point a = coord[i][0]; // start node
            Point b = coord[i][1]; // end node
            board[b.x][b.y] = board[a.x][a.y];
            board[a.x][a.y] = 0;
            // updating piece in pieces
            int pc = board[b.x][b.y]-10;
            if (pc == -10) {
                System.err.println("pc=-10 Move from (" + a.x + "," + a.y + ") to (" + b.x + "," + b.y + ")");
                System.out.println("kill me please");
            }
            for(int j = 0; j < 4; j++) {
                Point piece = pieces[pc][j];
                if (piece.x == a.x && piece.y == a.y)
                {
                    piece.x = b.x;
                    piece.y = b.y;
                    break;
                }
            }
        }
    }

    static Point[] goal = new Point[] {
            new Point(7,1),         //End goal of yellow
            new Point(7,7),         //End goal of black
            new Point(1,1),         //End goal of white
            new Point(1,7),         //End goal of red
    };

    public static double fitness(int colorID)
    {
        int result = 0;
        for(int i = 0; i < 4; i++) {
            int min = 100000;
            for (int x = goal[colorID].x; x <= goal[colorID].x + 1; x++) { // TODO: GENERALIZE FOR MULTIPLE PLAYERS
                for (int y = goal[colorID].y; y <= goal[colorID].y + 1; y++) {
                    int pieceX = Util.readX(pieces[colorID], i);
                    int pieceY = Util.readY(pieces[colorID], i);
                    int squareType = board[x][y];
                    if (squareType == 0 || //Square is free
                            (x == pieceX && y == pieceY) || //This piece is on the square
                            (squareType-10)/4 != colorID) { //Piece of other player is blocking square
                        int dist = Math.abs(x - pieceX) + Math.abs(y - pieceY);
                        min = Math.min(min, dist);
                    }
                }
            }
            result += min;
        }
        return result/2.0;
    }
}
