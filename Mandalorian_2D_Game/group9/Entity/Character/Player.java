package ca.sfu.group9.Entity.Character;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import ca.sfu.group9.Entity.Character.Enemy.Enemy;
import ca.sfu.group9.Entity.Entity;
import ca.sfu.group9.Entity.Reward.Reward;
import ca.sfu.group9.GamePanel;
import ca.sfu.group9.KeyHandler;
import ca.sfu.group9.GamePanel.ControlButton;
import ca.sfu.group9.Map.MapTile;
import ca.sfu.group9.Position;

import javax.imageio.ImageIO;

public class Player extends Character {

	GamePanel gp;
	KeyHandler key;

	private int health;
	public int score;
	private boolean playerMoved;
	private int animationFrameNumber;
	private boolean playerHit;
	private int tickCounter;
	private int playerHitDuration;

	BufferedImage up1, down1, left1, right1;
	BufferedImage up2, down2, left2, right2;
	BufferedImage hit_up1, hit_down1, hit_left1, hit_right1;
	BufferedImage hit_up2, hit_down2, hit_left2, hit_right2;


	public int getScore(){
		return score;
	}

	public void setScore(int score){
		this.score = score;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}


	public Player(GamePanel gp, KeyHandler key) {

		this.gp = gp;
		this.key = key;
		this.type = "Player";
		defaultVals();
		//getEntityImage();
	}

	/**
	 * Sets the initial values for the player like health.
	 */
	public void defaultVals(){

		health = 100;
		score = 0;
		facingDirection = FacingDirection.DOWN;
		playerMoved = false;
		animationFrameNumber = 1;
		tickCounter = 0;
		playerHitDuration = 3;

	}

	// gets entity image
	public void getEntityImage() {

		try {

			up1 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_up_1.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_down_1.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_left_1.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_right_1.png"));

			up2 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_up_2.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_down_2.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_left_2.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_right_2.png"));

			hit_up1 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_up_hit_1.png"));
			hit_down1 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_down_hit_1.png"));
			hit_left1 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_left_hit_1.png"));
			hit_right1 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_right_hit_1.png"));

			hit_up2 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_up_hit_2.png"));
			hit_down2 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_down_hit_2.png"));
			hit_left2 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_left_hit_2.png"));
			hit_right2 = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_right_hit_2.png"));


		} catch(IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Increments the player's animation frame.
	 */
	private void nextAnimationFrame() {
		if(animationFrameNumber == 1) {
			animationFrameNumber = 2;
		}
		else {
			animationFrameNumber = 1;
		}
	}


	private void tileCollison() {
		if(playerMoved) {
			nextAnimationFrame();

			MapTile currentTile = gp.gameMap.getMapTile(positionX, positionY);

			if(currentTile.type == MapTile.TileType.OBJECTIVE) {

				gp.gameMap.collectObjective(positionX, positionY);
			}
			else if(currentTile.type == MapTile.TileType.EXITOPEN | currentTile.type == MapTile.TileType.EXITCLOSED) {
				if(gp.gameMap.getObjectiveCollected()) {
					gp.winGame();
				}
			}
		}
	}


	private void move(ControlButton direction) {

		if(direction == ControlButton.UP) {
			if((positionY - 1 >= 0) && gp.gameMap.getMapTile(positionX, positionY - 1).isPassable() == true)
			{
				positionY -= 1;
				playerMoved = true;
			}
		}

		else if((positionY + 1 < gp.gameMap.getMapSizeY()) && direction == ControlButton.DOWN) {
			if(gp.gameMap.getMapTile(positionX, positionY + 1).isPassable() == true)
			{
				positionY += 1;
				playerMoved = true;
			}
		}

		else if((positionX - 1 >= 0) && direction == ControlButton.LEFT) {
			if(gp.gameMap.getMapTile(positionX - 1, positionY).isPassable() == true)
			{
				positionX -= 1;
				playerMoved = true;
			}
		}

		else if((positionX + 1 < gp.gameMap.getMapSizeX()) && direction == ControlButton.RIGHT) {
			if(gp.gameMap.getMapTile(positionX + 1, positionY).isPassable() == true)
			{
				positionX += 1;
				playerMoved = true;
			}
		}

		tileCollison();
	}

	// set position of the player manually (ex: set starting position on the map)
	public void setPositionX(int x) {
		positionX = x;
	}

	public void setPositionY(int y) {
		positionY = y;
	}

	/**
	 * Gets the player's current animation sprite.
	 */
	protected void getFrameImage() {

		if (playerHit) {
			if (this.animationFrameNumber == 1){
				switch (facingDirection) {
					case UP:
						image = hit_up1;
						break;
					case DOWN:
						image = hit_down1;
						break;
					case LEFT:
						image = hit_left1;
						break;
					case RIGHT:
						image = hit_right1;
						break;
					default:
						image = down1;
						break;
				}
			}
			else {
				switch (facingDirection) {
					case UP:
						image = hit_up2;
						break;
					case DOWN:
						image = hit_down2;
						break;
					case LEFT:
						image = hit_left2;
						break;
					case RIGHT:
						image = hit_right2;
						break;
					default:
						image = down1;
						break;
				}
			}
		}
		else {
			if (this.animationFrameNumber == 1) {
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
			} else {
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
						image = down1;
						break;
				}
			}
		}

	}


	private void entityCollision() {
		Iterator<Entity> itr = gp.gameMap.getEntitiesAtPos(this.getPositionX(), this.getPositionY()).iterator();

		while(itr.hasNext()){
			Entity entity = itr.next();
			if(entity.getIsDead() == false) {
				if (entity instanceof Reward) {
					this.setScore(this.score + ((Reward) entity).getScoreValue());
					entity.killEntity();
				} else if (entity instanceof Enemy) {
					playerHit = true;
					tickCounter = 0;
					this.setHealth(this.getHealth() - ((Enemy) entity).getContactDamageAmount());
					if (entity.getType().equals("Gunner")) {
						// do nothing
					} else {
						entity.killEntity();
					}
				}
			}
		}

		// check for position swap edge case
		Iterator<Entity> itr2 = gp.gameMap.getEntitiesAtPos(lastPosition.getX(), lastPosition.getY()).iterator();
		while(itr2.hasNext()) {
			Entity entity = itr2.next();
			Position entityLastPos = entity.getLastPosition();
			if(entityLastPos.getX() == getPositionX() && entityLastPos.getY() == getPositionY() && lastPosition.getX() == entity.getPositionX() && lastPosition.getY() == entity.getPositionY()) {
				if(entity.getIsDead() == false) {
					playerHit = true;
					tickCounter = 0;
					this.setHealth(this.getHealth() - ((Enemy) entity).getContactDamageAmount());
					entity.killEntity();
				}
			}
		}
	}


	public void update() {

		tickCounter++;

		updateLastPosition();

		if(gp.controlButton == ControlButton.UP) {
			facingDirection = FacingDirection.UP;
		}

		else if(gp.controlButton == ControlButton.DOWN) {
			facingDirection = FacingDirection.DOWN;
		}

		else if(gp.controlButton == ControlButton.LEFT) {
			facingDirection = FacingDirection.LEFT;
		}

		else if(gp.controlButton == ControlButton.RIGHT) {
			facingDirection = FacingDirection.RIGHT;
		}

		if(gp.controlButton == ControlButton.UP || gp.controlButton == ControlButton.DOWN || gp.controlButton == ControlButton.LEFT || gp.controlButton == ControlButton.RIGHT) {
			move(gp.controlButton);
		}

		if (playerHit && tickCounter % playerHitDuration == 0){
			playerHit = false;
			tickCounter = 0;
		}

		entityCollision();

		// check if the game is lost
		if (this.getHealth() <= 0) {
			gp.loseGame();
		}
	}
}
