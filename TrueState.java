//package moreisless_Marty_Vlad;

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
            Point a = new Point(input.charAt(1+m*5)-'0'+1,input.charAt(m*5)-'a'+1);
            Point b = new Point(input.charAt(3+m*5)-'0'+1,input.charAt(2+m*5)-'a'+1);
            // update board and player pieces lists
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
}
