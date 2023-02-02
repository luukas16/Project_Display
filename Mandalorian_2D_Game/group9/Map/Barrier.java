package ca.sfu.group9.Map;

public class Barrier extends MapTile {

    public Barrier() {

        this.isPassable = false;
        this.type = TileType.BARRIER;
    }

    public Barrier(TileType type) {
        this.isPassable = false;
        this.type = type;
    }
}
