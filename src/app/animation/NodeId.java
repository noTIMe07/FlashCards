package app.animation;

import java.awt.*;

public enum NodeId {
    WINDOWBOARD_LEFT(new Point(700, 579)),
    WINDOWBOARD_RIGHT(new Point(900, 579)),
    TABLE_LEFT(new Point(830,727)),
    TABLE_MIDDLE(new Point(1150, 727)),
    TABLE_RIGHT(new Point(1470, 727)),
    CHAIR(new Point(1280, 853)),
    CHAIR_SIT(new Point(1280, 853)),
    FLOOR_BELOWTABLE(new Point(1100, 1050)),
    FLOOR_LEFT(new Point(400, 1050)),
    SHELF_LEFT(new Point(1600, 810)),
    SHELF_RIGHT(new Point(1750, 810));

    Point position;

    NodeId(Point position_){
        position = position_;
    }

    public Point getPosition() {return position;}
}
