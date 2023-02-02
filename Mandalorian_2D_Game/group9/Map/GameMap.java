package ca.sfu.group9.Map;

import ca.sfu.group9.Entity.Character.Player;
import ca.sfu.group9.Entity.Entity;
import ca.sfu.group9.GamePanel;
import ca.sfu.group9.Position;
import ca.sfu.group9.UI;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class GameMap {

    private final int mapSizeX;
    private final int mapSizeY;
    private int playerStartX;
    private int playerStartY;
    private boolean objectiveCollected;

    Player player;
    GamePanel gp;

    public int minimumBonusDelay;
    public int bonusCount;
    public ArrayList<Position> bonusPositions;

    MapTile[][] map;
    HashMap<MapTile.TileType, MapTile> tiles;

    /**
     * List of all entities on the map.
     */
    public ArrayList<Entity> entities;

    public GameMap(int mapSizeX, int mapSizeY, Player player, GamePanel gp, MapTile[][] map, int playerStartX, int playerStartY) {
        this.mapSizeX = mapSizeX;
        this.mapSizeY = mapSizeY;

        this.player = player;
        this.gp = gp;

        this.map = map;

        entities = new ArrayList<Entity>();

        getTileImage();

        // set position of the player (this along with map creation process will be read from a file)
        this.playerStartX = playerStartX;
        this.playerStartY = playerStartY;
        player.setPositionX(playerStartX);
        player.setPositionY(playerStartY);
    }

    /**
     * Loads the sprites for all tile types.
     */
    public void getTileImage() {

        try {
            // tiles have one image associated with their type

            this.tiles = new HashMap<MapTile.TileType, MapTile>();

            tiles.put(MapTile.TileType.EMPTY, new MapTile());
            tiles.get(MapTile.TileType.EMPTY).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/sand.png"));

            tiles.put(MapTile.TileType.BARRIER, new MapTile());
            tiles.get(MapTile.TileType.BARRIER).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/tile_barrier.png"));

            tiles.put(MapTile.TileType.UI, new MapTile());
            tiles.get(MapTile.TileType.UI).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/new_UI.png"));

            tiles.put(MapTile.TileType.OBJECTIVE, new MapTile());
            tiles.get(MapTile.TileType.OBJECTIVE).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/objective_closed.png"));

            tiles.put(MapTile.TileType.EXITCLOSED, new MapTile());
            tiles.get(MapTile.TileType.EXITCLOSED).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/exit_closed.png"));

            tiles.put(MapTile.TileType.EXITOPEN, new MapTile());
            tiles.get(MapTile.TileType.EXITOPEN).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/exit_open.png"));

            tiles.put(MapTile.TileType.GUNNERSTAND, new MapTile());
            tiles.get(MapTile.TileType.GUNNERSTAND).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/sand.png"));

            tiles.put(MapTile.TileType.BUILDING1, new MapTile());
            tiles.get(MapTile.TileType.BUILDING1).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/building_1.png"));

            tiles.put(MapTile.TileType.BUILDING2, new MapTile());
            tiles.get(MapTile.TileType.BUILDING2).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/building_2.png"));

            tiles.put(MapTile.TileType.BUILDING3, new MapTile());
            tiles.get(MapTile.TileType.BUILDING3).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/building_3.png"));

            tiles.put(MapTile.TileType.BUILDING4, new MapTile());
            tiles.get(MapTile.TileType.BUILDING4).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/building_4.png"));

            tiles.put(MapTile.TileType.MARKETLEFT1, new MapTile());
            tiles.get(MapTile.TileType.MARKETLEFT1).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/market_left_1.png"));

            tiles.put(MapTile.TileType.MARKETLEFT2, new MapTile());
            tiles.get(MapTile.TileType.MARKETLEFT2).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/market_left_2.png"));

            tiles.put(MapTile.TileType.MARKETRIGHT1, new MapTile());
            tiles.get(MapTile.TileType.MARKETRIGHT1).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/market_right_1.png"));

            tiles.put(MapTile.TileType.MARKETRIGHT2, new MapTile());
            tiles.get(MapTile.TileType.MARKETRIGHT2).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/market_right_2.png"));

            tiles.put(MapTile.TileType.ROCKS, new MapTile());
            tiles.get(MapTile.TileType.ROCKS).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/rocks.png"));

            tiles.put(MapTile.TileType.BRICKS, new MapTile());
            tiles.get(MapTile.TileType.BRICKS).image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/brick_wall.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    public MapTile getMapTile(int x, int y) {
        return map[y][x];
    }

    public void setMapTile(int x, int y, MapTile mapTile) {
        map[y][x] = mapTile;
    }

    public int getMapSizeX() {
        return mapSizeX;
    }

    public int getMapSizeY() {
        return mapSizeY;
    }

    public ArrayList<Entity> getEntitiesAtPos(int x, int y) {
        Iterator<Entity> itr = gp.gameMap.entities.iterator();

        ArrayList<Entity> entitiesAtPos = new ArrayList<>();

        while(itr.hasNext()) {
            Entity entity = itr.next();
            if (entity.getPositionY() == y && entity.getPositionX() == x) {
                entitiesAtPos.add(entity);
            }
        }

        return entitiesAtPos;
    }

    /**
     * Adds an Entity to the GameMap entity list.
     * @param entity Entity to add
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Adds an Entity to a GameMap and sets its position.
     * @param entity Entity to add to the GameMap
     * @param entityX X position to give the Entity
     * @param entityY Y position to give the Entity
     */
    public void addEntity(Entity entity, int entityX, int entityY) {
        entity.setPositionX(entityX);
        entity.setPositionY(entityY);
        this.addEntity(entity);
    }

    /**
     * Draws every tile in the Map.
     * @param g2 Screen graphics object
     */
    public void draw(Graphics2D g2) {

        // loops through the map, getting each tile image then drawing it
        for(int y = 0; y < mapSizeY; y++) {
            for (int x = 0; x < mapSizeX; x++) {
                g2.drawImage(tiles.get(map[y][x].type).image, x * GamePanel.getTileSize(), y * GamePanel.getTileSize() + UI.getUiHeight(), GamePanel.getTileSize(), GamePanel.getTileSize(), null);
            }
        }

    }
    public boolean getObjectiveCollected() {

        return objectiveCollected;
    }

    public void setObjectiveCollected(boolean objective) { this.objectiveCollected = objective; }
    /**
     * Opens all exit tiles and removes the objective from the GameMap.
     * @param x X coordinate of the objective that was collected
     * @param y Y coordinate of the objective that was collected
     */
    public void collectObjective(int x, int y) {
        objectiveCollected = true;
        this.setMapTile(x, y, new EmptyTile());

        // sets all closed exit doors to open exit doors
        for(int mapY = 0; mapY < this.getMapSizeY(); mapY++) {
            for (int mapX = 0; mapX < this.getMapSizeX(); mapX++) {
                MapTile currentMapTile = this.getMapTile(mapX, mapY);
                if(currentMapTile.type == MapTile.TileType.EXITCLOSED) {
                    currentMapTile.type = MapTile.TileType.EXITOPEN;
                }
            }
        }
    }
    public void setBonusPositions(ArrayList<Position> bonusPositions) { this.bonusPositions = bonusPositions; }

    public void setMinimumBonusDelay(int delay) { this.minimumBonusDelay = delay; }

    public int getMinimumBonusDelay() { return minimumBonusDelay; }

    public void resetBonusCount() { bonusCount = minimumBonusDelay; }
}
