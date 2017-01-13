package moreisless_Marty_Vlad;

import java.awt.*;

/**
 * Created by Vladimir on 1/13/2017.
 */
public class TrueState {
    static int board [][];
    static Point pieces [][];
    static int turn;

    TrueState() {
        turn = 0;
        board = new int[10][10];
        for (int i = 0; i < 10; i ++ ) {
            board[0][i] = -1;
            board[i][0] = -1;
            board[9][i] = -1;
            board[i][9] = -1;
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
                board[x][y] = i;
            }
        }
    }

    // decodes turn of color c, and updates truestate accordingly
    static void decodeTurnAndUpdate (String input, int c) {
        //h1f1:g1g3
        int moveAmnt = (input.length()+1)/5;
        for (int m = 0; m < moveAmnt; m++) {
            // decode start and end destination
            Point a = new Point(input.charAt(1+m*5)-'0'+1,input.charAt(m*5)-'a'+1);
            Point b = new Point(input.charAt(3+m*5)-'0'+1,input.charAt(2+m*5)-'a'+1);
            // update board and player pieces lists
            board[a.x][a.y] = 0;
            board[b.x][b.y] = c;
            for (int i = 0; i < 4; i++) {
                if (pieces[c][i].x == a.x && pieces[c][i].y == a.y) {
                    pieces[c][i].x = b.x;
                    pieces[c][i].y = b.y;
                    break;
                }
            }
        }
    }
}
