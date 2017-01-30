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

    // returns a matrix with up to 3 pairs of points. The first point gives the starting location, the second the target.
    public Point[][] getMoveCoordinates() {
        // first deep copy pieces and board before proceeding to move computation
        Point[][] res = new Point [3][2];
        Point[][] pieces = new Point[4][4];
        for (int i = 0; i < 4;  i++) {
            for (int j = 0; j < 4; j++) {
                pieces[i][j] = new Point(TrueState.pieces[i][j]);
            }
        }
        // compute movecode for each move in the movelist
        for (int i = 0; i < 3; i++) {
            if (moveId[i] == -1) continue; // no move stored
            Point a = pieces[pieceId[i] > 3 ? Colors.frC : Colors.myC][pieceId[i]%4]; // start node
            Point b = new Point (a.x + Util.dx[moveId[i]%4], a.y+Util.dy[moveId[i]%4]);
            if (moveId[i] > 3) {
                b.x+=Util.dx[moveId[i]%4];
                b.y+=Util.dy[moveId[i]%4];
            }
            res [i][0] = a;
            res [i][1] = b;
        }
        return res;
    }
}