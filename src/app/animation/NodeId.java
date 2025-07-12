package app.animation;

import java.awt.*;

public enum NodeId {
    WINDOWBOARD_LEFT(null, -100, 35),
    WINDOWBOARD_RIGHT(null, 100, 0),
    TABLE_LEFT(null, -250, -175),
    TABLE_MIDDLE(null, -50, -175),
    TABLE_RIGHT(null, 250, -175),
    CHAIR(null, 100, -56),
    CHAIR_SIT(null, 100, -56);
//    FLOOR_BELOWTABLE(null, 0, 0),
//    FLOOR_LEFT(null, 0, 0),
//    SHELF_LEFT(null, 0, 0),
//    SHELF_RIGHT(null,0, 0);

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

    // getPosition return point from index, most left point is index 0
    public Point getCenterOffset() {
        int x =  sprite.getCenterOffset().x + offsetX;
        int y = sprite.getCenterOffset().y + offsetY;
        return new Point(x, y);
    }
}

