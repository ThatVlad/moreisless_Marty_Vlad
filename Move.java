package moreisless_Marty_Vlad;

import java.awt.*;

/**
 * Created by s157928 on 13-1-2017.
 */
public class Move {
    int[] moveId;
    int[] pieceId;
    int numMoves = 0; //TODO: MAYBE ADD THIS TO HASHING??

    public Move()
    {
        moveId = new int[3];
        pieceId = new int[3];

        for(int i = 0; i < 3; i++) {
            moveId[i] = -1; pieceId[i] = -1;
        }
    }

    void addMove(int move, int piece)
    {
        moveId[numMoves] = move;
        pieceId[numMoves] = piece;
        numMoves++;
    }

    String getMoveString () {
        // first deep copy pieces and board before proceeding to string computation
        Point[][] pieces = new Point[4][4];
        for (int i = 0; i < 4;  i++) {
            for (int j = 0; j < 4; j++) {
                pieces[i][j] = new Point(TrueState.pieces[i][j]);
            }
        }
        int[][] board = new int [10][10];
        for (int i = 0; i < 10;  i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = TrueState.board[i][j];
            }
        }
        // compute movecode for each move in the movelist
        String res = "";
        for (int i = 0; i < 3; i++) {
            if (moveId[i] == -1) continue; // no move stored
            if (i > 0) res += ':'; // piece-moving is seperated by :
            Point a = pieces[Colors.myC][pieceId[i]]; // start node
            res += (char)(a.y - 1 + 'a') + (a.x-1); // add starting position to string
            Point b = new Point (a.x + Util.dx[moveId[i]], a.y+Util.dy[moveId[i]]);
            if (board[b.x][b.y] > 1) {
                b.x+=Util.dx[moveId[i]];
                b.y+=Util.dy[moveId[i]];
            }
            board[a.x][a.y] = 0;
            board[b.x][b.y] = Colors.myC;
            res += (char)(b.y - 1 + 'a') + (b.x-1); // append end node to output
        }
        return res;
    }
}