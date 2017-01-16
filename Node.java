package moreisless_Marty_Vlad;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class Node implements Comparable<Node> {
    public int G = Integer.MAX_VALUE;
    int fitness;
    State state;

    int H() {
        return fitness;
    }

    public int F()
    {
        return H() + G;
    }

    @Override
    public int compareTo(Node o) {
        return F() > o.F() ? 1 : (F() < o.F() ? -1 : 0);
    }

    @Override
    public int hashCode() {
        int h_pieces = Arrays.hashCode(state.pieces);
        int h_moveId = Arrays.hashCode(state.firstMoveMade.moveId);
        int h_pieceId = Arrays.hashCode(state.firstMoveMade.pieceId);
        return Objects.hash(G,fitness, state.AP, state.time, h_pieces, h_moveId, h_pieceId);
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Node)) {
            return false;
        }
        Node other = (Node) o;
        if (G != other.G)
            return false;
        if (fitness != other.fitness)
            return false;
        if(state.AP != other.state.AP)
            return false;
        if (state.time != other.state.time)
            return false;
        if (!Arrays.equals(state.pieces, other.state.pieces))
            return false;
        if (!Arrays.equals(state.firstMoveMade.moveId,other.state.firstMoveMade.moveId))
            return false;
        if (!Arrays.equals(state.firstMoveMade.pieceId, other.state.firstMoveMade.pieceId))
            return false;
        return true;
    }
}
