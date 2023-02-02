package ca.sfu.group9;

import javax.swing.JPanel;

import ca.sfu.group9.Entity.*;
import ca.sfu.group9.Entity.Character.Enemy.*;
import ca.sfu.group9.Entity.Character.Character;
import ca.sfu.group9.Entity.Character.Player;
import ca.sfu.group9.Entity.Reward.NormalReward;
import ca.sfu.group9.Entity.Reward.Reward;
import ca.sfu.group9.Entity.Reward.SpecialReward;
import ca.sfu.group9.Map.*;

import java.awt.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    private static GamePanel gp = null;

    /**
     * Creates a single GamePanel object if one doesn't exist, then returns it.
     * @return The single instance of GamepPanel
     */
    public static GamePanel getGamePanel() {
        if(gp == null) {
            gp = new GamePanel();
        }
        return gp;
    }
    // SCREEN SETTINGS
    static final int originalTileSize = 16;    // size of tiles without scaling
    static final int scale = 3;

    public static final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 28;
    final int maxScreenRow = 15;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow + UI.getUiHeight();;

    final int FPS = 60;   // how many times graphics and animations are updated every second
    final int TPS = 6;    // Function ticks per second
    final int inputTPS = 60;

    public LoadMap loadMap;

    KeyHandler keyHandler;
    Thread gameThread;
    Player player;
    public GameMap gameMap;
    public UI ui;

    // GAME STATE
    //is set to play_state (1) at the end of the loadmap function
    public int game_state;
    public final int play_state = 1;
    public final int pause_state = 2;
    public final int game_over_state = 3;
    public final int game_won_state = 4;
    public final int menu_screen_state = 5;

    int tickCounter; // tracks game ticks
    public ArrayList<Entity>  entityAddArray; // temp array of entities

    int pauseWaitCount = 0;
    int pauseDelay = 2;

    int menuEnterCount = 0;
    int menuEnterDelay = 2;

    int menuMoveCount = 0;
    int menuMoveDelay = 2;

    //boolean objectiveCollected;
    int numLevels;
    int currentLevel;


    // used for debug and testing
    public int levelTickCounter;

    public int getTPS() {
        return TPS;
    }

    public int getFPS() {
        return FPS;
    }

    public Player getPlayer() { return player; }


    /**
     * The current button that is being pressed (automatically held until next tick).
     */
    public enum ControlButton {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        PAUSE,
        ENTER,
        EXIT,
        NONE
    }

    /**
     * Current button type being pressed on this tick.
     */
    public ControlButton controlButton = ControlButton.NONE;



    private GamePanel() {

        keyHandler = new KeyHandler(this);
        game_state = menu_screen_state;

        numLevels = 3;

        loadMap = new LoadMap();

        ui = new UI(this);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public int getPlayerPositionX(){
        return player.getPositionX();
    }

    public int getPlayerPositionY(){
        return player.getPositionY();
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start();
    }

    public int getNumLevels() {
        return numLevels;
    }

    public int getCurrentLevel() { return currentLevel; }

    public KeyHandler getKeyHandler() { return keyHandler; }

    public void setPlayer(Player player) { this.player = player; }

    public void resetEntityAddArray() { this.entityAddArray = new ArrayList<>(); }

    public void setTickCounter(int tick) { this.tickCounter = tick; }
    public void setLevelTickCounter(int tick) { this.levelTickCounter = tick; }

    //public void resetBonusCount() { bonusCount = minimumBonusDelay; }

    /**
     * Resets the player and loads the given level.
     * @param level number corresponding to map file
     */
    private void startGame(int level) {
        currentLevel = level;
        gp.player = new Player(this, keyHandler);
        gp.gameMap = loadMap.loadGameMap("map" + level);
    }

    /**
     * Increments current level number and starts the game
     * @return
     */
    private boolean startNextLevel() {
        int nextLevel = currentLevel + 1;
        if(nextLevel < getNumLevels()) {
            startGame(nextLevel);
            return true;
        }
        return false;
    }

    /**
     * Sets the game state to menu and initializes the menu options.
     */
    public void gotoMainMenu() {
        game_state = menu_screen_state;
        menuEnterCount = 0;
        menuMoveCount = 0;
        gp.ui.MenuScreenNum = 0;
        gp.ui.MenuOptionNum = 0;
    }

    /**
     * Sets the game state to win screen.
     */
    public void winGame() {
        game_state = game_won_state;
    }

    /**
     * Sets the game state to lose screen.
     */
    public void loseGame() {
        game_state = game_over_state;
    }


    /**
     * Handles ticks, inputs, and frame timings.
     */
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double tickInterval = 1000000000/TPS;
        double inputInterval = 1000000000/inputTPS;
        double drawDelta = 0;
        double tickDelta = 0;
        double inputDelta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            drawDelta += (currentTime - lastTime) / drawInterval;
            tickDelta += (currentTime - lastTime) / tickInterval;
            inputDelta += (currentTime - lastTime) / inputInterval;

            lastTime = currentTime;


            if(tickDelta >= 1) {     // ticks may catch up very quickly if a breakpoint is hit since its technically been sitting there for a while
                update();
                tickDelta--;
            }

            if(drawDelta >= 1) {
                //update();
                repaint();
                drawDelta--;
            }

            // sets what control to consider on the next tick
            if(inputDelta >= 1) {
                if(tickDelta >= 0) {
                    if(keyHandler.pausePressed == true) {
                        controlButton = ControlButton.PAUSE;
                    }
                    else if(keyHandler.enterPressed == true) {
                        controlButton = ControlButton.ENTER;
                    }
                    else if(keyHandler.upPressed == true) {
                        //controlDirection = ControlDirection.UP;
                        controlButton = ControlButton.UP;
                    }
                    else if(keyHandler.downPressed == true) {
                        //controlDirection = ControlDirection.DOWN;
                        controlButton = ControlButton.DOWN;
                    }
                    else if(keyHandler.leftPressed == true) {
                        //controlDirection = ControlDirection.LEFT;
                        controlButton = ControlButton.LEFT;
                    }
                    else if(keyHandler.rightPressed == true) {
                        //controlDirection = ControlDirection.RIGHT;
                        controlButton = ControlButton.RIGHT;
                    }
                    else if(keyHandler.exitPressed == true) {
                        controlButton = ControlButton.EXIT;
                    }

                    if(game_state == play_state && tickDelta < 0.5) {
                        if(controlButton == ControlButton.UP || controlButton == ControlButton.DOWN || controlButton == ControlButton.LEFT || controlButton == ControlButton.RIGHT) {
                            controlButton = ControlButton.NONE;
                        }
                    }
                }
                inputDelta--;
            }

        }
    }

    public static int getTileSize() {
        return tileSize;
    }

    /**
     * Runs every tick. Handles controls in each game state. Updates player and Entities every tick while in game.
     */
    public synchronized void update() {

        //System.out.println(game_state);

        if(game_state == play_state){
            tickCounter++;

            // pause the game
            if(pauseWaitCount >= pauseDelay && controlButton == ControlButton.PAUSE) {
                game_state = pause_state;
                pauseWaitCount = 0;
            }
            else {
                // update every entity
                for(Entity entity : gameMap.entities) {
                    entity.update();
                    //entity.draw(g2);
                }

                Iterator<Entity> itr1 = entityAddArray.iterator();

                // add entities that were requested to be added
                while(itr1.hasNext()) {
                    Entity entity = itr1.next();
                    gameMap.addEntity(entity);
                    }

                entityAddArray.clear();

                Iterator<Entity> itr = gameMap.entities.iterator();

                // remove entities that requested to be removed
                while(itr.hasNext()) {
                    Entity entity = itr.next();
                    if(entity.removeOnNextTick == true) {
                        //gameMap.removeEntity(entity);
                        itr.remove();
                    }
                }

                // add bonus rewards to the map
                if(gameMap.bonusCount >= gameMap.minimumBonusDelay) {
                    if(!gameMap.bonusPositions.isEmpty()) {
                        Random rand = new Random();
                        Position spawnPosition = gameMap.bonusPositions.get(rand.nextInt(gameMap.bonusPositions.size()));
                        SpecialReward sr = new SpecialReward();
                        gameMap.addEntity(sr, spawnPosition.getX(), spawnPosition.getY());
                    }
                    gameMap.bonusCount = 0;
                }


                player.update();
                pauseWaitCount++;
                gameMap.bonusCount++;

                // reset control buffers
                //controlDirection = ControlDirection.NONE;
                // clear other control buffer ArrayList
            }

            levelTickCounter++;

        }
        // pause menu button handling
        else if(game_state == pause_state){
            if(controlButton == ControlButton.ENTER) {
                gotoMainMenu();
            }

            if(pauseWaitCount >= pauseDelay && controlButton == ControlButton.PAUSE) {
                game_state = play_state;
                pauseWaitCount = 0;
            }
            pauseWaitCount++;
        }

        // main menu button handling
        else if(game_state == menu_screen_state) {
            if(controlButton == ControlButton.EXIT) {
                System.exit(0);
            }

            if(menuMoveCount >= menuMoveDelay) {
                if (controlButton == ControlButton.UP) {
                    gp.ui.MenuOptionNum--;
                    if (gp.ui.MenuOptionNum < 0) {
                        gp.ui.MenuOptionNum = gp.ui.MenuSizes[gp.ui.MenuScreenNum] - 1;
                    }
                    menuMoveCount = 0;
                }
                if (controlButton == ControlButton.DOWN) {
                    gp.ui.MenuOptionNum++;
                    if (gp.ui.MenuOptionNum >= gp.ui.MenuSizes[gp.ui.MenuScreenNum]) {
                        gp.ui.MenuOptionNum = 0;
                    }
                    menuMoveCount = 0;
                }
            }

            if(menuEnterCount >= menuEnterDelay) {
                if(controlButton == ControlButton.ENTER) {
                    //will be more logic once more menu items are made
                    if (gp.ui.MenuScreenNum == ui.MainMenu) {
                        if (gp.ui.MenuOptionNum == 0) {
                            startGame(0);
                        } else if (gp.ui.MenuOptionNum == 1) {
                            gp.ui.MenuScreenNum = ui.LevelSelect;
                            gp.ui.MenuOptionNum = 0;
                        } else if (gp.ui.MenuOptionNum == 2) {
                            System.exit(0);
                        }
                    } else if (gp.ui.MenuScreenNum == ui.LevelSelect) {
                        if (gp.ui.MenuOptionNum == ui.MenuSizes[gp.ui.LevelSelect] - 1) {
                            // go back to main menu
                            ui.MenuScreenNum = 0;
                            gp.ui.MenuOptionNum = 0;
                        } else {
                            startGame(ui.MenuOptionNum);
                        }

                    }
                    menuEnterCount = 0;
                    menuMoveCount = 0;
                }
            }


            menuEnterCount++;
            menuMoveCount++;
        }

        else if(game_state == game_won_state) {
            if(controlButton == ControlButton.ENTER) {
                boolean newLevelStarted = startNextLevel();
                if(newLevelStarted == false) {
                    gotoMainMenu();
                }
            }
            if(controlButton == ControlButton.EXIT) {
                System.exit(0);
            }
        }

        else if(game_state == game_over_state) {
            if(controlButton == ControlButton.ENTER) {
                gotoMainMenu();
            }
            if(controlButton == ControlButton.EXIT) {
                System.exit(0);
            }
        }

        controlButton = ControlButton.NONE;
    }

    /**
     * Draws tiles, Entities and player every frame
     * @param g Screen graphics object
     */
    public synchronized void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        if(game_state == menu_screen_state){
            try {
                ui.draw(g2);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else{

            // draw map
            gameMap.draw(g2);

            // draw each entity
            for(Entity entity : gameMap.entities) {
                //entity.update();
                entity.draw(g2);
            }

            // draw the player
            player.draw(g2);

            try {
                ui.draw(g2);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        g2.dispose();
    }
}
