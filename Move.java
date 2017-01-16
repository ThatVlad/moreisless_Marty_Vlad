package moreisless_Marty_Vlad;

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
}
