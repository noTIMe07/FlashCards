package app.animation;

import java.awt.*;

//public enum NodeId {
//    WINDOWBOARD_LEFT(new Point(700, 579)),
//    WINDOWBOARD_RIGHT(new Point(900, 579)),
//    TABLE_LEFT(new Point(830,727)),
//    TABLE_MIDDLE(new Point(1150, 727)),
//    TABLE_RIGHT(new Point(1470, 727)),
//    CHAIR(new Point(1280, 853)),
//    CHAIR_SIT(new Point(1280, 853)),
//    FLOOR_BELOWTABLE(new Point(1100, 1050)),
//    FLOOR_LEFT(new Point(400, 1050)),
//    SHELF_LEFT(new Point(1600, 810)),
//    SHELF_RIGHT(new Point(1750, 810));
//
//    Point position;
//
//    NodeId(Point position_){
//        position = position_;
//    }
//
//    public Point getPosition() {return position;}
//}

public enum NodeId {
    WINDOWBOARD_LEFT(null, 0, 0),
    WINDOWBOARD_RIGHT(null, 0, 0),
    TABLE_LEFT(null, 0, 0),
    TABLE_MIDDLE(null, 0, 0),
    TABLE_RIGHT(null, 0, 0),
    CHAIR(null, 0, 0),
    CHAIR_SIT(null, 0, 0);
//    FLOOR_BELOWTABLE(null, 0, 0),
//    FLOOR_LEFT(null, 0, 0),
//    SHELF_LEFT(null, 0, 0),
//    SHELF_RIGHT(null,0, 0);

    private double ratioX, ratioY;
    private int offsetX, offsetY;
    StaticSprite sprite;

    NodeId(StaticSprite sprite, int offsetX, int offsetY){
        this.sprite = sprite;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void setSprite(StaticSprite sprite){
        this.sprite = sprite;
    }

    public void setRatioAndOffset(double rx, double ry, int ox, int oy){
        ratioX = rx;
        ratioY = ry;
        offsetX = ox;
        offsetY = oy;
    }

    // getPosition return point from index, most left point is index 0
    public Point getPosition() {
        System.out.println(sprite.getPosition());
        int x =  sprite.getPosition().x + (int) (offsetX * sprite.getScale());
        int y = sprite.getPosition().y + (int) (offsetY * sprite.getScale());
        return new Point(x, y);
    }
}

