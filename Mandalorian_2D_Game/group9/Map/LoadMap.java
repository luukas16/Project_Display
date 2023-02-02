package ca.sfu.group9.Map;

import ca.sfu.group9.Entity.Character.Character;
import ca.sfu.group9.Entity.Character.Enemy.*;
import ca.sfu.group9.Entity.Entity;
import ca.sfu.group9.Entity.Reward.NormalReward;
import ca.sfu.group9.Entity.Reward.Reward;
import ca.sfu.group9.Entity.Reward.SpecialReward;
import ca.sfu.group9.GamePanel;
import ca.sfu.group9.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class LoadMap {

    /**
     * Gets a new MapTile with type corresponding to to an integer value
     * @param type MapTile ID from map file corresponding to a MapTile type
     * @return A MapTile with the specified type
     */
    public MapTile getMapDataType(int type) {
        MapTile tile;

        switch(type) {
            case 0:
                tile = new EmptyTile();
                break;
            case 1:
                tile = new Barrier();
                break;
            case 2:
                tile = new UI_tile();
                break;
            case 3:
                tile = new ObjectiveTile();
                break;
            case 4:
                tile = new ExitTile();
                break;
            case 5:
                tile = new GunnerStandTile();
                break;
            case 6:
                tile = new Barrier(MapTile.TileType.BUILDING1);
                break;
            case 7:
                tile = new Barrier(MapTile.TileType.BUILDING2);
                break;
            case 8:
                tile = new Barrier(MapTile.TileType.BUILDING3);
                break;
            case 9:
                tile = new Barrier(MapTile.TileType.BUILDING4);
                break;
            case 10:
                tile = new Barrier(MapTile.TileType.MARKETLEFT1);
                break;
            case 11:
                tile = new Barrier(MapTile.TileType.MARKETLEFT2);
                break;
            case 12:
                tile = new Barrier(MapTile.TileType.MARKETRIGHT1);
                break;
            case 13:
                tile = new Barrier(MapTile.TileType.MARKETRIGHT2);
                break;
            case 14:
                tile = new Barrier(MapTile.TileType.ROCKS);
                break;
            case 15:
                tile = new Barrier(MapTile.TileType.BRICKS);
                break;
            default:
                tile = new EmptyTile();
        }

        return tile;
    }

    /**
     * Gets a new Entity based off of given type and facing direction.
     * @param type type of Entity to create
     * @param direction initial direction the new Entity will face
     * @return New Entity specified by type and direction
     */
    public Entity getEntityType(String type, Character.FacingDirection direction) {
        Entity entity;

        switch(type) {
            case "NormalReward":
                entity = new NormalReward();
                break;
            case "SpecialReward":
                entity = new SpecialReward();
                break;
            case "Reward":
                entity = new Reward();
                break;
            case "Enemy":
                entity = new Enemy();
                break;
            case "Gunner":
                entity = new Gunner(direction);
                break;
            case "MovingEnemy":
                entity = new MovingEnemy();
                break;
            case "Projectile":
                entity = new Projectile(direction);
                break;
            case "Trap":
                entity = new Trap();
                break;
            case "Character":
                entity = new Character();
                break;
            default:
                entity = new NormalReward();
        }

        return entity;
    }


    /**
     * Creates a GameMap from a json map file based of level number.
     * @param level level number of the map file to load
     * @return New GameMap created from file
     */
    public GameMap loadGameMap(String level) {

        GamePanel gp = GamePanel.getGamePanel();

        JSONParser parser = new JSONParser();

        // gets the file name of the requested level
        StringBuilder levelPath = new StringBuilder();
        levelPath.append("/maps/");
        levelPath.append(level);
        levelPath.append(".json");


        try {
            InputStream mapFile = getClass().getResourceAsStream(levelPath.toString());

            // converts InputStream to StringBuilder does not support unicode characters in mapFile
            StringBuilder sb = new StringBuilder();
            for (int ch; (ch = mapFile.read()) != -1; ) {
                sb.append((char) ch);
            }

            Object obj = parser.parse(sb.toString());
            JSONObject jsonObj = (JSONObject) obj;

            // read the size of the map
            Long mapX = (Long) jsonObj.get("MapX");
            Long mapY = (Long) jsonObj.get("MapY");

            JSONArray mapData = (JSONArray) jsonObj.get("MapData");

            MapTile[][] map;
            map = new MapTile[Math.toIntExact(mapY)][Math.toIntExact(mapX)];

            // read MapTiles from file
            for(int y = 0; y < mapY; y++) {
                JSONArray tileRow = (JSONArray) mapData.get(y);
                for(int x = 0; x < mapX; x++) {
                    Long tile = (Long) tileRow.get(x);
                    map[y][x] = getMapDataType(Math.toIntExact(tile));
                    System.out.print(tile + " ");
                }
                System.out.println();
            }

            // get player data from file (currently no out of bounds check)
            Long playerStartX = (Long) jsonObj.get("PlayerX");
            Long playerStartY = (Long) jsonObj.get("PlayerY");


            GameMap newGameMap = new GameMap(Math.toIntExact(mapX), Math.toIntExact(mapY), gp.getPlayer(), gp, map, Math.toIntExact(playerStartX), Math.toIntExact(playerStartY));

            // get entity data from array of entities
            JSONArray entityArray = (JSONArray) jsonObj.get("Entities");

            for(int i = 0; i < entityArray.size(); i++)
            {
                JSONObject entity = (JSONObject) entityArray.get(i);
                String entityType = (String) entity.get("EntityType");
                int entityX = Math.toIntExact((Long) (entity.get("EntityX")));
                int entityY = Math.toIntExact((Long) (entity.get("EntityY")));
                String entityDirection;
                Character.FacingDirection direction = Character.FacingDirection.DOWN;
                if(entity.get("EntityDirection") != null) {
                    //System.out.println("getting entity direction");
                    entityDirection = (String) (entity.get("EntityDirection"));
                    //System.out.println(entityDirection);
                    if(entityDirection.toLowerCase().equals("left")) {
                        direction = Character.FacingDirection.LEFT;
                        //System.out.println("left");
                    }
                    else if(entityDirection.toLowerCase().equals("right")) {
                        direction = Character.FacingDirection.RIGHT;
                    }
                    else if(entityDirection.toLowerCase().equals("up")) {
                        direction = Character.FacingDirection.UP;
                        //System.out.println("up");
                    }
                    else if(entityDirection.toLowerCase().equals("down")) {
                        direction = Character.FacingDirection.DOWN;
                    }
                }
                newGameMap.addEntity(getEntityType(entityType, direction), entityX, entityY);
            }

            try {
                Long bonusRate = (Long) jsonObj.get("BonusRate");
                newGameMap.setMinimumBonusDelay(Math.toIntExact(bonusRate));
            }
            catch (NullPointerException e) {
                newGameMap.setMinimumBonusDelay(60);
            }

            ArrayList<Position> newBonusPositions = new ArrayList<Position>();

            // get bonus reward spawn locations
            try {
                JSONArray bonusArray = (JSONArray) jsonObj.get("BonusLocations");

                for(int i = 0; i < bonusArray.size(); i++) {
                    JSONObject bonus = (JSONObject) bonusArray.get(i);
                    int bonusX = Math.toIntExact((Long) (bonus.get("BonusX")));
                    int bonusY = Math.toIntExact((Long) (bonus.get("BonusY")));

                    Position p = new Position(bonusX, bonusY);
                    newBonusPositions.add(p);
                }
            }
            catch (NullPointerException e) {
                // leave array blank if there are no spawning locations
            }

            newGameMap.setBonusPositions(newBonusPositions);

            // reset game settings for the new level then return the new map
            gp.game_state = gp.play_state;
            newGameMap.setObjectiveCollected(false);
            gp.resetEntityAddArray();
            gp.setTickCounter(0);
            newGameMap.resetBonusCount();
            gp.setLevelTickCounter(0);
            gp.ui.setPlayTime(0);
            return newGameMap;


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }





        return null;
    }
}
