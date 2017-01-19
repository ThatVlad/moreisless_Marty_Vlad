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

           // drawBoard2(current.state);
          /*  if (current.state.pieces[Colors.myC][0].x == 8 && current.state.pieces[Colors.myC][0].y == 8)
                if (current.state.pieces[Colors.myC][1].x == 8 && current.state.pieces[Colors.myC][1].y == 8)
                    if (current.state.pieces[Colors.myC][2].x == 8 && current.state.pieces[Colors.myC][2].y == 8)
                        if (current.state.pieces[Colors.myC][3].x == 8 && current.state.pieces[Colors.myC][3].y == 8)
                            break;*/
            if(current.H() < 1/100.0) break;

            if (closed.contains(current))
                continue;
            if(closed.size() > 90000000)
            {
                int abc= 1231;
            }
            closed.add(current);
            for (State target : current.state.transitions(firstMove, Colors.myC)) {
            //    if (!target.isValid()) continue;
                double currF = current.G;
                double tarF = target.node.G;
                //  if (current.G + 1 < target.node.G) { //TODO: maybe this breaks stuff? idk
                if(closed.contains(target.node))
                    continue;; //TODO:maybe this breaks stuff? idk
                target.node.G = target.AP;
                double tar = target.fitness();
                double cur = current.state.fitness();
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

    static void drawBoard(State state)
    {
        System.out.flush();
        char[][] board = new char[10][10];
        for(int i= 0; i <4; i++)
            board[state.pieces[Colors.myC][i].x][state.pieces[Colors.myC][i].y] = 'O';

        System.out.println("===============");
        for(int y = 0; y < 10; y++)
        {
            for(int x = 0; x < 10; x++)
            {
                if(board[x][y] == '\u0000')
                    board[x][y] = ' ';
                if(state.moveNewLoc != null && x == state.moveNewLoc.x && y == state.moveNewLoc.y)
                    board[x][y]= 'X';
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
        System.out.println("===============");
    }

    static void drawBoard2(State state)
    {
        System.out.flush();
        char[][] board = new char[10][10];
        for(int i= 0; i <4; i++) {
            board[state.pieces[Colors.myC][i].x][state.pieces[Colors.myC][i].y] = (char)(i + '0');
        }

        for(int y = 0; y < 10; y++)
        {
            for(int x = 0; x < 10; x++)
            {
                if(board[x][y] == '\u0000')
                    board[x][y] = '-';
                if(x ==6) System.out.print(y==4? '-' : '|');
                System.out.print(board[x][y]);
            }
            System.out.println();
        }
    }
}
