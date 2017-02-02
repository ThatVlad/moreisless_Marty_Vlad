package moreisless_Marty_Vlad;

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
     * @param maxCompTime specifies how many ms this function may take
     * @return returns the optimal first move
     */
    Move solve(State initalState, int colorID)
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

            if(currentNode.fitness < currentBest.fitness)
                currentBest = currentNode;

            if(currentNode.H() < 1/100.0) break;

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
        System.out.println("States searched: " + closed.size());
        Point loc1 = Util.readPoint(currentNode.state.pieces[1], 1);
        Point loc2 = Util.readPoint(currentNode.state.pieces[1], 2);
        Point loc3 = Util.readPoint(currentNode.state.pieces[1], 3);
        Point loc4 = Util.readPoint(currentNode.state.pieces[1], 4);
        double fitness = currentNode.fitness;
        if (currentNode != null)
        {
            return currentNode.state.firstMoveMade;
        }
        System.out.println("Wut..");
        return new Move();
    }
}
