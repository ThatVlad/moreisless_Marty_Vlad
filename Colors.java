//package moreisless_Marty_Vlad;

import java.util.Scanner;

/**
 * Created by Vladimir on 1/13/2017.
 */

public class Colors {
    static char myC; // my color
    static char frC; // teammate Color

    static void initiateColors(Scanner sc) {
        String c = sc.next();
        if (c.equals("Yellow")) myC = 0;
        else if (c.equals("Black")) myC = 1;
        else if (c.equals("White")) myC = 2;
        else myC = 3;
        frC = (char)((myC+2)%4);
    }
}
