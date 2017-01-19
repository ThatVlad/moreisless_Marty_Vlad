package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class Node implements Comparable<Node> {
    static int hashTable [][][];

    public int G = Integer.MAX_VALUE;

    State state;
    int hashCode;

        return fitness;
    }

    {
        return H() + G;
    }

    // placeholder
    Node () {
        if (hashTable == null) {
            hashTable = new int[10][10][4];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    for (int k = 0; k < 4; k++) {
                        hashTable[i][j][k] = Util.rand.nextInt(Integer.MAX_VALUE);
                    }
                }
            }
        }
    }

    // takes extra info to efficiently update hash value
    Node (int  originalHash, int color, Point start, Point end) {
        int startHash = hashTable[start.x][start.y][color];
        int endHash = hashTable[end.x][end.y][color];
        hashCode = originalHash ^ startHash ^ endHash;
    }

    // builds HashCode from assigned "State"-object
    public void buildHashCode () {
        int posCode;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 4; j++) {
                posCode = hashTable[state.pieces[i][j].x][state.pieces[i][j].y][i];
                hashCode = hashCode ^ posCode;
            }
        }
    }

    @Override
    public int compareTo(Node o) {
        return F() > o.F() ? 1 : (F() < o.F() ? -1 : 0);
    }

    @Override
    public int hashCode() {

        //Optimize? https://en.wikipedia.org/wiki/Zobrist_hashing
        int h_pieces = Arrays.deepHashCode(state.pieces);
        int h_moveId = Arrays.hashCode(state.firstMoveMade.moveId);
        int h_pieceId = Arrays.hashCode(state.firstMoveMade.pieceId);

        return Objects.hash(/*G,fitness, state.AP,*/ h_pieces/*, h_moveId, h_pieceId*/);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Node)) {
            return false;
        }
        Node other = (Node) o;
        //if (G != other.G)
        //    return false;
        //if (fitness != other.fitness)
        //    return false;
        //if(state.AP != other.state.AP)
        //    return false;
        //if(state.time != other.state.time)
        //    return false;
        if (!Arrays.deepEquals(state.pieces, other.state.pieces))
            return false;
       //if (!Arrays.equals(state.firstMoveMade.moveId,other.state.firstMoveMade.moveId))
       //     return false;
       // if (!Arrays.equals(state.firstMoveMade.pieceId, other.state.firstMoveMade.pieceId))
       //     return false;
        return true;
    }
}
