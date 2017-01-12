package moreisless_Marty_Vlad;

import java.awt.*;

/**
 * Created by s157928 on 11-1-2017.
 */
public class Player {
    Point[] pieces = new Point[4];

    Point[] start = new Point[] {
            new Point(0,6),         //Start of yellow (left top)
            new Point(0,0),         //Start of black
            new Point(6,6),         //Start of white
            new Point(6,0),         //Start of red
    };

    Point[] goal = new Point[] {
            new Point(7,0),         //End goal of yellow
            new Point(7,7),         //End goal of black
            new Point(0,0),         //End goal of white
            new Point(0,7),         //End goal of red
    };
    int id; //Color ID


    int fitness()
    {
        int result = 0;
        for(int i = 0; i < 4; i++)
        {
            result += Math.abs(goal[id].x - pieces[i].x) + Math.abs(goal[id].y - pieces[i].y);
        }
        return result;
    }

    
}
