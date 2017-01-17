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
        start.state.firstMoveMade = new Move();
        open.add(start);
        boolean firstMove = true;

        Node current = null;

        while(!open.isEmpty()) {
            current = open.poll();

          /*  if (current.state.pieces[Colors.myC][0].x == 8 && current.state.pieces[Colors.myC][0].y == 8)
                if (current.state.pieces[Colors.myC][1].x == 8 && current.state.pieces[Colors.myC][1].y == 8)
                    if (current.state.pieces[Colors.myC][2].x == 8 && current.state.pieces[Colors.myC][2].y == 8)
                        if (current.state.pieces[Colors.myC][3].x == 8 && current.state.pieces[Colors.myC][3].y == 8)
                            break;*/
            if(current.H() == 0) break;
           // if(current.H() < 2) break; //TESTING
            if (closed.contains(current))
                continue;
            closed.add(current);
            for (State target : current.state.transitions(firstMove, Colors.myC)) {
            //    if (!target.isValid()) continue;
                double currF = current.G;
                double tarF = target.node.G;
                //  if (current.G + 1 < target.node.G) { //TODO: maybe this breaks stuff? idk
                target.node.G = target.time;
                int tar = target.fitness();
                int cur = current.state.fitness();
                target.node.fitness = target.fitness();
                open.add(target.node);
                //   }
            }
            firstMove = false;
        }
        System.out.println("States searched: " + closed.size());
        if (current != null)
        {
            return current.state.firstMoveMade;
        }
        System.out.println("Wut..");
        return new Move();
    }
}
