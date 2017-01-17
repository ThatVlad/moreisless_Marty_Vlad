package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class State {
    static int[][] board = new int[10][10];

    Point[][] pieces;
    int[] dx = new int[] { 1, 0,0,-1};
    int[] dy = new int[] { 0,-1,1,0};

    int time;
    int AP; //ability points
    Move firstMoveMade;

    Point[] goal = new Point[] {
            new Point(8,1),         //End goal of yellow
            new Point(8,8),         //End goal of black
            new Point(1,1),         //End goal of white
            new Point(1,8),         //End goal of red
    };
    int id = 1; //Color ID
    Node node;

    public State()
    {
        node = new Node();
        node.state = this;

        firstMoveMade = new Move();
    }

    int fitness()
    {
        int result = 0;
        for(int i = 0; i < 4; i++)
        {
            int min = Integer.MAX_VALUE;
            for(int x = 7; x<=8; x++) {
                for (int y = 7; y <= 8; y++) {
                    if (board[x][y] == i+10 || board[x][y] == 0) {
                        int dist = Math.abs(x - pieces[Colors.myC][i].x) + Math.abs(y - pieces[Colors.myC][i].y);
                        min = Math.min(min, dist);
                    }
                }
            }
            result += min;
        }
        return result;
    }

    //Clone constructor
    public State(State s)
    {
        pieces = new Point[4][4];
        for(int i = 0; i < 4; i++) pieces[Colors.myC][i] = new Point(s.pieces[Colors.myC][i].x, s.pieces[Colors.myC][i].y);
        time = s.time;
        node = new Node();
        node.state = this;
        firstMoveMade = new Move();
        this.AP = s.AP;

        for(int i = 0; i < 3; i++) {
            firstMoveMade.pieceId[i]= s.firstMoveMade.pieceId[i];
            firstMoveMade.moveId[i] = s.firstMoveMade.moveId[i];
        }
        firstMoveMade.numMoves = s.firstMoveMade.numMoves;
    }

    boolean isEnd()
    {
        if(pieces[Colors.myC][0].x == 8 && pieces[Colors.myC][0].y == 8)
            return true;
        return false;
    }

    boolean isValid()
    {
        for(int i = 0; i < 4; i++)
        {
            if(pieces[Colors.myC][i].x < 0 || pieces[Colors.myC][i].y<0 || pieces[Colors.myC][i].x>7 || pieces[Colors.myC][i].y>7)
                return false;
            if(pieces[Colors.myC][i].x == 3 && pieces[Colors.myC][i].y !=6)
                return false;
        }
        return true;
    }

    //TODO: IMPLEMENT WALLS AND JUMPS
    ArrayList<State> transitions(boolean first, int colorID)
    {
        clearBoard();
        for(int j = 0; j < 1; j++)
            for(int i = 0; i < 4; i++)
                board[pieces[j][i].x][pieces[j][i].y] = i+j*4+10;
       // board[8][8] = 0;
        ArrayList<State> L = new ArrayList<>();

        ArrayList<Integer> order = swap();
        for(int i2= 0; i2 < 4; i2++)
        {
            int i = order.get(i2);
            for(int j2 = 0; j2 < 4; j2++) {
                int j = j2;
                State newState = new State(this); //Clone this state
                Point location = pieces[Colors.myC][i];
                Point newLoc = new Point(location.x+dx[j], location.y+dy[j]);
                if(newLoc.x < 0 || newLoc.y < 0)
                {
                    int abc =123;
                }
                if(board[newLoc.x][newLoc.y] >0)
                    continue;
                Point a = this.pieces[Colors.myC][i];
                Point b =newLoc;
                int abc = Walls.getWall(this.pieces[Colors.myC][i], newLoc);
                if(abc != 0)
                {
                    int adf=123;
                }
                if(a.x == 5 && b.x==6 &&abc == 0)
                {
                    int dfadf = 123;
                }
                if(Walls.getWall(this.pieces[Colors.myC][i], newLoc) > 0)
                    continue;
                newState.pieces[Colors.myC][i].setLocation(newLoc);
                newState.AP++;
                if(newState.AP > 3) {
                    newState.time++;
                    newState.AP=0;
                }
                if(newState.time == 0) {
                    if(newState.firstMoveMade.numMoves == 3)
                    {
                        int ab12c =123;
                    }
                    newState.firstMoveMade.addMove(j, i);
                }
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

    static Random random = new Random();
    ArrayList<Integer> swap()
    {
        ArrayList<Integer> order = new ArrayList<>();
        order.add(0);
        order.add(1);
        order.add(2);
        order.add(3);
        for(int i = 0; i < 4; i++)
        {
            for(int j = 0; j<4; j++) {
                if (random.nextBoolean()) {
                    Collections.swap(order, i,j);
                }
            }
        }

        return order;
    }

    void clearBoard() {
        for(int x = 0; x<10; x++)
            for(int y = 0; y<10;y++) {
                if(x == 0 || y == 0 || x == 9 || y == 9)
                    board[x][y] = 5;
                else board[x][y] = 0;
            }
    }
}
