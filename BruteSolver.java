//package moreisless_Marty_Vlad;

import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class BruteSolver {

    Move solve(State initalState)
    {
        PriorityQueue<Node> open = new PriorityQueue<Node>();
        HashSet<Integer> closed = new HashSet<Integer>();
        Node start = initalState.node;

        initalState.node.buildHashCode();
        start.fitness = start.state.fitness();
        start.G = 0;
        start.state.firstMoveMade = new Move();
        open.add(start);

        Node currentNode = null;

        while(!open.isEmpty()) {
            currentNode = open.poll();
            int  current = new Integer(currentNode.hashCode);
            if(currentNode.H() < 1/100.0) break;

            if (closed.contains(current))
                continue;

            closed.add(current);
            for (State target : currentNode.state.transitions(Colors.myC)) {
                //  if (current.G + 1 < target.node.G) { //TODO: maybe this breaks stuff? idk
                if(closed.contains(new Integer(target.node.hashCode)))
                    continue;; //TODO:maybe this breaks stuff? idk
                target.node.G = target.AP;
                target.node.fitness = target.fitness();
                open.add(target.node);
                //   }
            }
        }
        //System.out.println("States searched: " + closed.size());
        if (currentNode != null)
        {
            return currentNode.state.firstMoveMade;
        }
        System.out.println("ERROR293");
        return new Move();
    }
}
