package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class State {
    Point[] pieces;
    int[] dx = new int[] { 1, 0,0,-1};
    int[] dy = new int[] { 0,-1,1,0};
    int time;
    int AP; //ability points
    Move firstMoveMade;

    Point[] goal = new Point[] {
            new Point(7,0),         //End goal of yellow
            new Point(7,7),         //End goal of black
            new Point(0,0),         //End goal of white
            new Point(0,7),         //End goal of red
    };
    int id = 1; //Color ID
    Node node;

    public State()
    {
        node = new Node();
        node.state = this;
    }

    int fitness()
    {
        int result = 0;
        for(int i = 0; i < 4; i++)
        {
            result += Math.abs(goal[id].x - pieces[i].x) + Math.abs(goal[id].y - pieces[i].y);
        }
        return result;
    }

    //Clone constructor
    public State(State s)
    {
        pieces = new Point[4];
        for(int i = 0; i < 4; i++) pieces[i] = new Point(s.pieces[i].x, s.pieces[i].y);
        time = s.time;
        node = new Node();
        node.state = this;
        firstMoveMade = s.firstMoveMade;
    }

    boolean isEnd()
    {
        if(pieces[0].x == 7 && pieces[0].y == 7)
            return true;
        return false;
    }

    boolean isValid()
    {
        for(int i = 0; i < 4; i++)
        {
            if(pieces[i].x < 0 || pieces[i].y<0 || pieces[i].x>7 || pieces[i].y>7)
                return false;
            if(pieces[i].x == 3 && pieces[i].y !=6)
                return false;
        }
        return true;
    }

    //TODO: IMPLEMENT WALLS AND JUMPS
    ArrayList<State> transitions(boolean first)
    {
        ArrayList<State> L = new ArrayList<>();
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j < 4; j++) {
                State newState = new State(this); //Clone this state
                Point location = pieces[i];
                Point newLoc = new Point(location.x+dx[j], location.y+dy[j]);
                newState.pieces[i].setLocation(newLoc);
                AP++;
                if(AP > 3)
                    newState.time++;
                if(first)
                    newState.firstMoveMade = new Move(j,i);
                L.add((newState));
            }
        }

       /* L = new ArrayList<>();
        State newState = new State(this); //Clone this state
        Point location = pieces[0];
        Point newLoc = new Point(location.x-1, location.y);
        newState.pieces[0].setLocation(newLoc);
        if(first)
            newState.firstMoveMade = new Move(1,0);
        L.add((newState));

        newState = new State(this); //Clone this state
        location = pieces[1];
        newLoc = new Point(location.x+1, location.y);
        newState.pieces[1].setLocation(newLoc);
        if(first)
            newState.firstMoveMade = new Move(0,1);
        L.add((newState));*/
        return L;
    }
}
