package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class BruteSolver {

    public State[] solve(State initalState)
    {
        PriorityQueue<Node> open = new PriorityQueue<Node>();
        Node start = initalState.node;
        Node end = new Node();
        start.G = 0;
        open.add(start);

        while(!open.isEmpty())
        {
            Node current = open.poll();
            if (current == end)
                break;
            if (current.inClosed)
                continue;
            current.inClosed = true;
            ArrayList<State> trans = current.state.transistions();
            for(State target : current.state.transistions())
            {
                if (!target.isValid()) continue;
                double currF = current.G;
                double tarF = target.node.G;
                if(current.G + 1 < target.node.G)
                {
                    target.node.G = current.G + 1;
                    target.node.parent = current;
                    open.add(target.node);
                }
            }
        }
        if (end.parent != null)
        {
            return reversePath(start, end);
        }
        System.out.println("Wut..");
        return null;
    }

    State[] reversePath(Node start, Node end)
    {
        ArrayList<State> path = new ArrayList<State>();
        Node current = end;
        while (current != start)
        {
            path.add(current.state);
            current = current.parent;
        }
        Collections.reverse(path);
        return (State[])path.toArray();
    }
}
