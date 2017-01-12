package moreisless_Marty_Vlad;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class Node implements Comparable<Node> {
    int G = Integer.MAX_VALUE;
    Node parent;
    State state;
    boolean inClosed = false;

    int H()
    {
        return state.fitness();
    }
    public int F()
    {
        return H() + G;
    }

    @Override
    public int compareTo(Node o) {
        return F() > o.F() ? 1 : (o.F() < F() ? -1 : 0);
    }
}
