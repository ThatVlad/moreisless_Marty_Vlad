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

    int[] piecesOpt;

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
        piecesOpt = new int[4];
    }

    // copies TrueState. Used to construct original state
    public State(Point[][] initPieces) {
        node = new Node();
        node.state = this;
        this.AP = 0;
        this.time = TrueState.turn;
        firstMoveMade = new Move();
        pieces = new Point[4][4];
        for (int i = 0; i < 4 ; i++) {
            pieces[i] = new Point[4];
            for (int j = 0; j < 4; j++) {
                pieces[i][j] = new Point(initPieces[i][j]);
            }
        }
    }

    double fitness()
    {
        int result = 0;
        for(int i = 0; i < 4; i++)
        {
            int min = Integer.MAX_VALUE;
            for(int x = 7; x<=8; x++) { // TODO: GENERALIZE FOR MULTIPLE PLAYERS
                for (int y = 7; y <= 8; y++) {
                    int pieceX = Util.readX(piecesOpt[Colors.myC],i);
                    int pieceY = Util.readY(piecesOpt[Colors.myC],i);
                    if ((x == pieceX&& y == pieceY) || board[x][y] == 0) {
                        int dist = Math.abs(x - pieceX) + Math.abs(y - pieceY);
                        min = Math.min(min, dist);
                    }
                }
            }
            result += min;
        }
        return result/2.0;
    }
    //Clone constructor
    public State(State s, int prevHashCode, int color, Point start, Point end)
    {
        // TODO: GENERALIZE FOR MULTIPLE PLAYERS
        piecesOpt = new int[4];
        for(int i = 0; i < 4; i++) {
            piecesOpt[i] = s.piecesOpt[i];
        }
        time = s.time;
        node = new Node(prevHashCode, color, start, end);
        node.state = this;
        firstMoveMade = new Move();
        this.AP = s.AP;

        for(int i = 0; i < 3; i++) {
            firstMoveMade.pieceId[i]= s.firstMoveMade.pieceId[i];
            firstMoveMade.moveId[i] = s.firstMoveMade.moveId[i];
        }
        firstMoveMade.numMoves = s.firstMoveMade.numMoves;
    }

    ArrayList<State> transitions(int colorID)
    {
        setBoard();

        ArrayList<State> L = new ArrayList<>();

        ArrayList<Integer> order = swap();
        ArrayList<Integer> order2 = swap();

        //Do one step
        for(int i2= 0; i2 < 4; i2++)
        {
            int i = order.get(i2);
            for(int j2 = 0; j2 < 4; j2++) {
                int j = order2.get(j2);

                Point location = Util.readPoint(piecesOpt[Colors.myC],i);
                Point friendlyPos = new Point(location.x+Util.dx[j], location.y+Util.dy[j]);
                Point newLoc = new Point(location.x+Util.dx[j], location.y+Util.dy[j]);

                int apCost = 1+ Walls.getWall(location, newLoc);
                if(apCost != 1 && (AP+apCost)/4 != AP/4) //If you spend more than 1 AP for the move, and it costs you an extra turn, then skip move
                    continue;
                if((board[friendlyPos.x][friendlyPos.y] >= 10 + 4*Colors.myC && board[friendlyPos.x][friendlyPos.y] < 4+4*Colors.myC+10))      //there is friendly to jump over
                {
                    Point jumpLoc = new Point(location.x+2*Util.dx[j], location.y+2*Util.dy[j]);
                    if (board[jumpLoc.x][jumpLoc.y] > 0)
                        continue; //There is a piece on location where you jump to
                    if (Walls.getWall(friendlyPos, jumpLoc) > 0)
                        continue; //Wall between friendly and where to jump to
                    if (Walls.getWall(location, friendlyPos) > 0)
                        continue; //Wall between friendly and me

                    State newState = new State(this, node.hashCode, Colors.myC, location, jumpLoc); //Clone this state

                    newState.piecesOpt[Colors.myC] = Util.updateXY(newState.piecesOpt[Colors.myC], i, jumpLoc.x, jumpLoc.y);

                    newState.AP+=apCost;
                    if (newState.AP > 3) {
                        newState.time+=4;
                    }
                    if (newState.time == 0) {
                        newState.firstMoveMade.addMove(4 + j, i);
                    }

                    L.add((newState));
                }

                if(board[newLoc.x][newLoc.y] >0)
                    continue;

                State newState = new State(this, node.hashCode, Colors.myC, location, newLoc); //Clone this state
                newState.piecesOpt[Colors.myC] = Util.updateXY(newState.piecesOpt[Colors.myC], i, newLoc.x, newLoc.y);; //set new location

                newState.AP+= apCost;
                if(newState.AP > 3) {
                    newState.time+=4;
                }
                if(newState.time == 0) {
                    newState.firstMoveMade.addMove(j, i);
                }

                L.add((newState));
            }
        }

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

    void setBoard() {
        for (int x = 0; x < 10; x++)
            for (int y = 0; y < 10; y++) {
                if (x == 0 || y == 0 || x == 9 || y == 9)
                    board[x][y] = 5;
                else board[x][y] = 0;
            }
        for (int j = 0; j < 1; j++) //TODO: EXTEND FOR 4 PLAYERS
            for (int i = 0; i < 4; i++) {
                int pieceX = Util.readX(piecesOpt[Colors.myC], i);
                int pieceY = Util.readY(piecesOpt[Colors.myC], i);
                board[pieceX][pieceY] = i + j * 4 + 10;
            }
    }
}
