//package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.HashSet;
import java.util.PriorityQueue;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class BruteSolver {

    /**
     * @param initalState state from which brute force is started
     * @param colorID specifies for which player we optimize
     * @return returns the optimal first move
     */
    Move solve(State initalState, int colorID, long compTime)
    {
        long startTime = System.currentTimeMillis();
        PriorityQueue<Node> open = new PriorityQueue<Node>();
        HashSet<Integer> closed = new HashSet<Integer>();
        Node start = initalState.node;

        initalState.node.buildHashCode();
        start.fitness = start.state.fitness(colorID);
        start.G = 0;
        start.state.firstMoveMade = new Move();
        start.state.time = colorID;
        open.add(start);

        Node currentNode = null;
        Node currentBest = start;
        while(!open.isEmpty()) {
            currentNode = open.poll();
            int  current = new Integer(currentNode.hashCode);
            if(currentBest.fitness > currentNode.fitness)
                currentBest = currentNode;

            if(currentNode.H() <= 1/100.0 || compTime < System.currentTimeMillis() - startTime) break;

            if (closed.contains(current))
                continue;

            closed.add(current);
            for (State target : currentNode.state.transitions()) {
                //  if (current.G + 1 < target.node.G) { //TODO: maybe this breaks stuff? idk
                if(closed.contains(new Integer(target.node.hashCode)))
                    continue;; //TODO:maybe this breaks stuff? idk
                target.node.G = target.AP;
                target.node.fitness = target.fitness(colorID);
                open.add(target.node);
                //   }
            }
        }
        //System.out.println("States searched: " + closed.size());
        if(closed.size() > 270690)
        {
            int WTF = 123;
        }
        double fitness = currentNode.fitness;
        if (currentBest != null)
        {
            return currentBest.state.firstMoveMade;
        }
        System.out.println("Wut..");
        return new Move();
    }
}