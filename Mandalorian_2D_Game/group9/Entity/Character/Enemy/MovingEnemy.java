package ca.sfu.group9.Entity.Character.Enemy;


import ca.sfu.group9.GamePanel;

import java.util.List;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class MovingEnemy extends Enemy{

    private final int VISIBILITY; // visibility of the ememy in tiles
    private int moveDelay; // moving delay of the enemy
    private int moveCounter; // counts the number of ticks
    private int moveAnimationFrame; // animation frame
    private boolean alerted; // checks if player is close

    // Moving enemy images
    BufferedImage up1, down1,left1, right1;
    BufferedImage up2, down2,left2, right2;
    BufferedImage alert_up1, alert_down1, alert_left1, alert_right1;
    BufferedImage alert_up2, alert_down2, alert_left2, alert_right2;


    /**
     * Increments the enemy's animation frame.
     */
    private void setNextEnemyImage(){

        if(moveAnimationFrame == 1) {
            moveAnimationFrame = 2;
        }
        else {
            moveAnimationFrame = 1;
        }
    }

    // constructor
    public MovingEnemy() {

        this.type = "MovingEnemy";
        setContactDamageAmount(100);
        this.facingDirection = FacingDirection.DOWN;
        this.moveDelay = 10;
        this.moveCounter = 0;
        this.VISIBILITY = 2;
        this.moveAnimationFrame = 1;
        alerted = false;

    }

    // get enemy image
    public void getEntityImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_up_1.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_down_1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_left_1.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_right_1.png"));

            up2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_up_2.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_down_2.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_left_2.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_right_2.png"));

            alert_up1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_up_alert.png"));
            alert_down1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_down_alert.png"));
            alert_left1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_left_alert.png"));
            alert_right1 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_right_alert.png"));

            alert_up2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_up_alert_2.png"));
            alert_down2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_down_alert_2.png"));
            alert_left2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_left_alert_2.png"));
            alert_right2 = ImageIO.read(getClass().getResourceAsStream("/sprites/mobs/trooper_right_alert_2.png"));

        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get enemy's image based on direction, animation frame, and alerted status.
     */
    protected void getFrameImage(){

        if(alerted) {
            if(moveAnimationFrame == 1) {
                switch (facingDirection) {
                    case UP:
                        image = alert_up1;
                        break;
                    case DOWN:
                        image = alert_down1;
                        break;
                    case LEFT:
                        image = alert_left1;
                        break;
                    case RIGHT:
                        image = alert_right1;
                        break;
                    default:
                        image = down1;
                        break;
                }
            }
            else {
                switch (facingDirection) {
                    case UP:
                        image = alert_up2;
                        break;
                    case DOWN:
                        image = alert_down2;
                        break;
                    case LEFT:
                        image = alert_left2;
                        break;
                    case RIGHT:
                        image = alert_right2;
                        break;
                    default:
                        image = down1;
                        break;
                }
            }
        }
        else {
            if(moveAnimationFrame == 1) {
                switch (facingDirection) {
                    case UP:
                        image = up1;
                        break;
                    case DOWN:
                        image = down1;
                        break;
                    case LEFT:
                        image = left1;
                        break;
                    case RIGHT:
                        image = right1;
                        break;
                    default:
                        image = down1;
                        break;

                }
            }
            else {
                switch (facingDirection) {
                    case UP:
                        image = up2;
                        break;
                    case DOWN:
                        image = down2;
                        break;
                    case LEFT:
                        image = left2;
                        break;
                    case RIGHT:
                        image = right2;
                        break;
                    default:
                        image = down2;
                        break;
                }
            }
        }

    }

    public boolean checkPassableTile(GamePanel gp, int  checkX, int checkY){

        try{
            if (gp.gameMap.getMapTile(checkX, checkY).isPassable() == true){
                return true;
            }
            else {
                return false;
            }

        } catch (Exception e){

            return false;

        }
    }

    public boolean checkMapBounds(GamePanel gp, int checkX, int checkY, FacingDirection dir){

        if (dir == FacingDirection.UP && checkY >= 0) {
            return true;
        }
        else if(dir == FacingDirection.DOWN && checkY < gp.gameMap.getMapSizeY()){
            return true;
        }
        else if (dir == FacingDirection.LEFT && checkX >= 0){
            return true;
        }
        else if(dir == FacingDirection.RIGHT && checkX < gp.gameMap.getMapSizeX()){
            return true;
        }
        return false;
    }

    public boolean playerClose(GamePanel gp){

        int playerX = gp.getPlayerPositionX();
        int playerY = gp.getPlayerPositionY();

        if (Math.abs(playerX - this.positionX) <= VISIBILITY && Math.abs(playerY - this.positionY) <= VISIBILITY){
            return true;
        }

        return false;
    }

    public boolean move(GamePanel gp,FacingDirection dir){

        switch (dir){

            case UP:
                if(checkMapBounds(gp,this.positionX,this.positionY - 1,dir) && checkPassableTile(gp, positionX,positionY-1))
                {
                    this.facingDirection = FacingDirection.UP;
                    setNextEnemyImage();
                    positionY -= 1;
                    return true;
                }
                break;
            case DOWN:
                if (checkMapBounds(gp,this.positionX,this.positionY + 1,dir) && checkPassableTile(gp, positionX,positionY + 1)){
                    this.facingDirection = FacingDirection.DOWN;
                    setNextEnemyImage();
                    this.positionY += 1;
                    return true;

                }
                break;
            case LEFT:
                if (checkMapBounds(gp,this.positionX - 1,this.positionY,dir) && checkPassableTile(gp, positionX - 1 ,positionY)){
                    this.facingDirection = FacingDirection.LEFT;
                    setNextEnemyImage();
                    this.positionX -= 1;
                    return true;

                }
                break;
            case RIGHT:
                if (checkMapBounds(gp,this.positionX + 1,this.positionY,dir) && checkPassableTile(gp, positionX + 1 ,positionY)){
                    this.facingDirection = FacingDirection.RIGHT;
                    setNextEnemyImage();
                    this.positionX += 1;
                    return true;

                }
                break;
            default:
                this.facingDirection = FacingDirection.DOWN;
                setNextEnemyImage();
                return false;
        }
        return false;
    }

    /**
     * Ai for when the player is not close
     * @param gp
     */
    public void randomMoveEnemy(GamePanel gp){

        int randomNumber = 0 + (int)(Math.random() * 4);

        switch (randomNumber){
            case 0:
                move(gp,FacingDirection.UP);
                break;

            case 1:
                move(gp,FacingDirection.DOWN);
                break;

            case 2:

                move(gp,FacingDirection.LEFT);
                break;

            case 3:

                move(gp,FacingDirection.RIGHT);
                break;

            default:
                this.facingDirection = FacingDirection.DOWN;
                break;
        }
    }


    private static boolean isPassible(int[][] map, Point point, int mapX, int mapY) {
        if(point.x < 0 || point.x >= mapX) {
            return false;
        }
        if(point.y < 0 || point.y >= mapY) {
            return false;
        }
        return map[point.y][point.x] == 1;
    }

    private static List<Point> findAdjacentPoints(int[][] map, Point point, int mapX, int mapY) {
        List<Point> neighbors = new ArrayList<>();
        Point up = new Point(point.x + 0, point.y - 1, point);
        Point down = new Point(point.x + 0, point.y + 1, point);
        Point left = new Point(point.x - 1, point.y + 0, point);
        Point right = new Point(point.x + 1, point.y + 0, point);

        if(isPassible(map, up, mapX, mapY)) {
            neighbors.add(up);
        }
        if(isPassible(map, down, mapX, mapY)) {
            neighbors.add(down);
        }
        if(isPassible(map, left, mapX, mapY)) {
            neighbors.add(left);
        }
        if(isPassible(map, right, mapX, mapY)) {
            neighbors.add(right);
        }

        return neighbors;
    }

    private static class Point {
        public int x;
        public int y;
        public Point previous;

        public Point(int x, int y, Point previous) {
            this.x = x;
            this.y = y;
            this.previous = previous;
        }

        @Override
        public boolean equals(Object o) {
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
    }

    /**
     * New Ai for finding the player
     * @param map a 2d array representing the passable tiles in the game map
     * @param enemy point where this enemy is
     * @param player point where the player is
     * @param mapX width of the game map
     * @param mapY height of the game map
     */
    private static List<Point> findPathToPlayer(int[][] map, Point enemy, Point player, int mapX, int mapY) {
        boolean done = false;
        List<Point> visited = new ArrayList<>();    // list of points on the map that have been processed
        visited.add(enemy);

        while(!done) {
            List<Point> newPassable = new ArrayList<>();    // list of passable tiles next to the visited tiles
            for(int i = 0; i < visited.size(); ++i) {
                Point point = visited.get(i);
                for(Point neighbor : findAdjacentPoints(map, point, mapX, mapY)) {
                    if(!visited.contains(neighbor) && !newPassable.contains(neighbor)) {    // if the tile is passable and hasn't been processed
                        newPassable.add(neighbor);
                    }
                }
            }

            for(Point point : newPassable) {    // process new tiles
                visited.add(point);
                if(player.x == point.x && player.y == point.y) {    // if the player is found, stop
                    done = true;
                    break;
                }
            }

            if(!done && newPassable.isEmpty()) {    // if there are now new passable tiles and the player is not found, then there is no path to the player
                return null;
            }
        }

        List<Point> path = new ArrayList<>();   // path to the player
        Point point = visited.get(visited.size() - 1);  // last point visited (the location of the player)
        while(point.previous != null) { // add the point to the front of the path and move backwards
            path.add(0, point);
            point = point.previous;
        }
        return path;
    }

    /**
     * Gets the path to the player and walks towards the first tile on that path.
     * @param gp
     */
    public void moveTowardsPlayerAI(GamePanel gp) {
        int mapX = gp.gameMap.getMapSizeX();
        int mapY = gp.gameMap.getMapSizeY();

        int[][] map = new int[mapY][mapX];

        for(int y = 0; y < mapY; y++) {
            for(int x = 0; x < mapX; x++) {
                if(gp.gameMap.getMapTile(x, y).isPassable()) {
                    // set array value to 1 if passable
                    map[y][x] = 1;
                }
                else {
                    // set array value to 0 if not passable
                    map[y][x] = 0;
                }
            }
        }

        Point enemy = new Point(this.getPositionX(), this.getPositionY(), null);

        Point player = new Point(gp.getPlayerPositionX(), gp.getPlayerPositionY(), null);

        List<Point> path = findPathToPlayer(map, enemy, player, mapX, mapY);


        Point nextMove;

        // if there is no path, don't move the enemy
        if(path == null) {
            nextMove = enemy;
        }
        else {
            nextMove = path.get(0);
        }

        int moveX = nextMove.x - this.getPositionX();
        int moveY = nextMove.y - this.getPositionY();

        if(moveX == -1) { // move left
            move(gp, FacingDirection.LEFT);
        }
        else if(moveX == 1) { // move right
            move(gp, FacingDirection.RIGHT);
        }
        else if(moveY == -1) { // move up
            move(gp, FacingDirection.UP);
        }
        else if(moveY == 1) { // move down
            move(gp, FacingDirection.DOWN);
        }

    }

    public void update(){

        GamePanel gp = GamePanel.getGamePanel();

        this.moveCounter++;

        updateLastPosition();


        // increases enemy speed and changes sprite when the player is close
        if (playerClose(gp)){
            alerted = true;
            this.moveDelay = 3;
        }
        else {
            alerted = false;
            this.moveDelay = 10;
        }

        if (this.moveCounter >= this.moveDelay)
        {
            // Moves toward the player if it is close. Moves randomly otherwise.
            if (playerClose(gp)){
                //moveTowardsPlayer(gp);
                moveTowardsPlayerAI(gp);
            }
            else {
                randomMoveEnemy(gp);
                //moveTowardsPlayerAI(gp);
            }
            moveCounter = 0;
        }

    }
}
