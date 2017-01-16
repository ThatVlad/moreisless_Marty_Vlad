package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class BruteSolver {

    Move solve(State initalState)
    {
        PriorityQueue<Node> open = new PriorityQueue<Node>();
        HashSet<Node> closed = new HashSet<Node>();
        Node start = initalState.node;

        start.fitness = start.state.fitness();
        Node end = new Node();
        start.G = 0;
        start.state.firstMoveMade = new Move(-1,-1);
        open.add(start);
        boolean firstMove = true;

        Node current = null;

        while(!open.isEmpty()) {
            current = open.poll();
            int moveID = current.state.firstMoveMade.moveId;
            int pieceID = current.state.firstMoveMade.pieceId;
            if (current.state.pieces[0].x == 7 && current.state.pieces[0].y == 7)
                if (current.state.pieces[1].x == 7 && current.state.pieces[1].y == 7)
                    if (current.state.pieces[2].x == 7 && current.state.pieces[2].y == 7)
                        if (current.state.pieces[3].x == 7 && current.state.pieces[3].y == 7)
                            break;
            if (closed.contains(current))
                continue;
            closed.add(current);
            ArrayList<State> test123=  current.state.transitions(firstMove);
            for (State target : current.state.transitions(firstMove)) {
                if (!target.isValid()) continue;
                if (target.pieces[0].x == 8) {
                    int abc = 23;
                }
                double currF = current.G;
                double tarF = target.node.G;
                if (current.G + 1 < target.node.G) { //TODO: SORT ON DIFFERENCE IN FITNESS!!
                    target.node.G = current.G + 1;
                    int tar = target.fitness();
                    int cur = current.state.fitness();
                    target.node.fitness = target.fitness(); //-current.state.fitness();
                    open.add(target.node);
                }
            }
            firstMove = false;
        }
        if (current != null)
        {
            return current.state.firstMoveMade;
        }
        System.out.println("Wut..");
        return new Move(-1,-1);
    }

    State[] reversePath(Node start, Node end)
    {
        ArrayList<State> path = new ArrayList<State>();
        Node current = end;
        while (current != start)
        {
            path.add(current.state);
            //current = current.parent;
        }
        Collections.reverse(path);
        return (State[])path.toArray();
    }
}
