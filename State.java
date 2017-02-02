package moreisless_Marty_Vlad;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by Timmermans on 12-1-2017.
 */
public class State {
    static int[][] board = new int[10][10];

    int[] pieces;

    int time;
    int AP; //ability points
    Move firstMoveMade;

    static Point[] goal = new Point[] {
            new Point(7,1),         //End goal of yellow
            new Point(7,7),         //End goal of black
            new Point(1,1),         //End goal of white
            new Point(1,7),         //End goal of red
    };
    Node node;

    public State()
    {
        node = new Node();
        node.state = this;

        firstMoveMade = new Move();
        pieces = new int[4];
    }

    // copies TrueState. Used to construct original state
    public State(Point[][] initPieces) {

        node = new Node();
        node.state = this;
        this.AP = 0;
        this.time = TrueState.turn;
        firstMoveMade = new Move();
        pieces = new int[4];
        for (int player = 0; player < 4; player++) {
            for (int piece = 0; piece < 4; piece++) {
                int x = initPieces[player][piece].x;
                int y = initPieces[player][piece].y;
                pieces[player] = Util.updateXY(pieces[player], piece, x, y);
            }
        }
    }

    /**
     * Computes min amount of AP to finish
     * @param colorID for which player you want to calculate
     * @return Min amount of AP
     */
    double fitness(int colorID)
    {
        int result = 0;
        for(int i = 0; i < 4; i++) {
            int min = 100000;
            for (int x = goal[colorID].x; x <= goal[colorID].x + 1; x++) { // TODO: GENERALIZE FOR MULTIPLE PLAYERS
                for (int y = goal[colorID].y; y <= goal[colorID].y + 1; y++) {
                    int pieceX = Util.readX(pieces[colorID], i);
                    int pieceY = Util.readY(pieces[colorID], i);
                    int squareType = board[x][y];
                    if (squareType == 0 || //Square is free
                       (x == pieceX && y == pieceY) /*|| //This piece is on the square
                            (squareType-10)/4 != colorID*/) { //Piece of other player is blocking square
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
    public State(State s, int prevHashCode, int colorID, Point start, Point end)
    {
        // TODO: GENERALIZE FOR MULTIPLE PLAYERS
        pieces = new int[4];
        for(int i = 0; i < 4; i++) {
            pieces[i] = s.pieces[i];
        }
        time = s.time;
        node = new Node(prevHashCode, colorID, start, end);
        node.state = this;
        firstMoveMade = new Move();
        this.AP = s.AP;

        for(int i = 0; i < 3; i++) {
            firstMoveMade.pieceId[i]= s.firstMoveMade.pieceId[i];
            firstMoveMade.moveId[i] = s.firstMoveMade.moveId[i];
        }
        firstMoveMade.numMoves = s.firstMoveMade.numMoves;
    }

    ArrayList<State> transitions()
    {
        int colorID = time % 4; //Decide whose should move

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

                Point location = Util.readPoint(pieces[colorID],i);
                Point friendlyPos = new Point(location.x+Util.dx[j], location.y+Util.dy[j]);
                Point newLoc = new Point(location.x+Util.dx[j], location.y+Util.dy[j]);

                int apCost = 1+ Walls.getWall(location, newLoc);
                if(apCost != 1 && (AP+apCost)/4 != AP/4) //If you spend more than 1 AP for the move, and it costs you an extra turn, then skip move
                    continue;
                if((board[friendlyPos.x][friendlyPos.y] >= 10 + 4*colorID && board[friendlyPos.x][friendlyPos.y] < 4+4*colorID+10))      //there is friendly to jump over
                {
                    Point jumpLoc = new Point(location.x+2*Util.dx[j], location.y+2*Util.dy[j]);
                    if (board[jumpLoc.x][jumpLoc.y] > 0)
                        continue; //There is a piece on location where you jump to
                    if (Walls.getWall(friendlyPos, jumpLoc) > 0)
                        continue; //Wall between friendly and where to jump to
                    if (Walls.getWall(location, friendlyPos) > 0)
                        continue; //Wall between friendly and me

                    State newState = new State(this, node.hashCode, colorID, location, jumpLoc); //Clone this state

                    newState.pieces[colorID] = Util.updateXY(newState.pieces[colorID], i, jumpLoc.x, jumpLoc.y);

                    newState.AP+=apCost;
                    if (newState.AP > 3) {
                        newState.time+=4;
                    }
                    if (newState.time < 4) {
                        newState.firstMoveMade.addMove(4 + j, i);
                    }

                    L.add((newState));
                }

                if(board[newLoc.x][newLoc.y] >0)
                    continue;

                State newState = new State(this, node.hashCode, colorID, location, newLoc); //Clone this state
                newState.pieces[colorID] = Util.updateXY(newState.pieces[colorID], i, newLoc.x, newLoc.y);; //set new location

                newState.AP+= apCost;
                if(newState.AP > 3) {
                    newState.time+=4;
                }
                if(newState.time < 4) {
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
        for (int j = 0; j < 4; j++) //TODO: EXTEND FOR 4 PLAYERS
        {
            for (int i = 0; i < 4; i++) {
                int pieceX = Util.readX(pieces[j], i);
                int pieceY = Util.readY(pieces[j], i);
                board[pieceX][pieceY] = i + j * 4 + 10;
            }
        }
    }
}
