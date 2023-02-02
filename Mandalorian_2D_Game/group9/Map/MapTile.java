package ca.sfu.group9.Map;

import java.awt.image.BufferedImage;

public class MapTile {

    /**
     * Type of MapTile.
     */
    public enum TileType {
        EMPTY,
        BARRIER,
        UI,
        OBJECTIVE,
        EXITOPEN,
        EXITCLOSED,
        GUNNERSTAND,
        BUILDING1,
        BUILDING2,
        BUILDING3,
        BUILDING4,
        MARKETLEFT1,
        MARKETLEFT2,
        MARKETRIGHT1,
        MARKETRIGHT2,
        ROCKS,
        BRICKS
    }
    public TileType type;

    boolean isPassable = true;
    public BufferedImage image;

    MapTile() {

    }

    /**
     * Returns if the MapTile can be walked on or not.
     * @return true if the MapTile can be walked on
     */
    public boolean isPassable() {
        return isPassable;
    }
}
