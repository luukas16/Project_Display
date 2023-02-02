package ca.sfu.group9;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font arial_40;
    double playTime;
    Graphics2D g2;
    Font mandalore;

    final static int uiHeight = 48;

    /**
     * Current menu option on the current menu screen.
     */
    public int MenuOptionNum = 0;
    /**
     * Current menu screen.
     */
    public int MenuScreenNum = 0;
    int numLevels;
    /**
     * The number of options on each menu screen.
     */
    public int MenuSizes[];

    /**
     * ID of the Main Menu MenuScreen.
     */
    public final int MainMenu = 0;
    /**
     * ID of the Level Select MenuScreen
     */
    public final int LevelSelect = 1;

    public static int getUiHeight() {
        return uiHeight;
    }

    //Used to limit the decimal places on time
    DecimalFormat dFormat = new DecimalFormat("0.00");
    /**
     * Sets the font of the Graphics2D object g2 to mandalore
     * @param Graphics2D g2 from the UI class
     */
    public void Mandalore_font_setter(Graphics2D g2){
        g2.setFont(mandalore);

    }

    public UI(GamePanel gp){
        this.gp = gp;

        numLevels = gp.getNumLevels();
        MenuSizes = new int[] {3, numLevels + 1};

        arial_40 = new Font("French Script MT", Font.PLAIN, 30);

        try {
            InputStream is = getClass().getResourceAsStream("/Fonts/mandalore.ttf");
            mandalore = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(30f);
            //mandalore = new Font("French Script MT", Font.PLAIN, 30);
        }catch(FontFormatException e){
            e.printStackTrace();
            mandalore = new Font("French Script MT", Font.PLAIN, 30);

        }catch(IOException e){
            e.printStackTrace();
            mandalore = new Font("French Script MT", Font.PLAIN, 30);
        }
    }

    /**
     * Gets the current play time in a level.
     * @return play time in seconds
     */
    public double getPlayTime(){ return playTime; }

    /**
     * Sets the current play time in a level.
     * @param playTime Number of seconds
     */
    public void setPlayTime(double playTime) {
        this.playTime = playTime;
    }

    /**
     * Draws the correct UI based on the game state
     * @param g2 Screen graphics object
     * @throws IOException
     */
    public void draw(Graphics2D g2) throws IOException {
        this.g2 = g2;
        //set fonts outside the draw function or it will have to instantiate a new one every tick


        //PLAY SCREEN
        if(gp.game_state == gp.play_state){
            //DRAW UI BACKGROUND
            BufferedImage uiTile = null;
            try {
                uiTile = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/new_UI.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            int numTiles = gp.maxScreenCol;
            for(int i = 0; i < numTiles; i++) {
                g2.drawImage(uiTile, i * GamePanel.getTileSize(), 0, GamePanel.getTileSize(), GamePanel.getTileSize(), null);
            }

            //SCORE AND HEALTH
            Mandalore_font_setter(g2);
            g2.setColor(Color.white);
            g2.drawString("Player score = " + gp.player.getScore(), 10, 33);
            g2.drawString("Player health = " + gp.player.getHealth(), 250, 33);

            //TIME
            playTime += (double)1/gp.getFPS();
            g2.drawString("Time: " + dFormat.format(playTime),gp.tileSize*11,33);

        }
        //PAUSE GAME
        else if(gp.game_state == gp.pause_state){
            drawPauseScreen();

        }

        //GAME OVER
        else if(gp.game_state == gp.game_over_state){
            drawGameOverScreen();

        }

        //GAME WIN
        else if (gp.game_state == gp.game_won_state){
            drawGameWonScreen();

        }
        //MENU SCREEN
        else if (gp.game_state == gp.menu_screen_state) {

            String title = "The Mandalorian";

            //g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            g2.setFont(mandalore.deriveFont(Font.BOLD, 96F));
            int x = getXforCentre(title);


            //DRAW MANDO

            try {
                BufferedImage title_image = ImageIO.read(getClass().getResourceAsStream("/sprites/tile/tattoine.png"));
                g2.drawImage(title_image, 0, 0, gp.screenWidth, gp.screenHeight, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedImage mando = ImageIO.read(getClass().getResourceAsStream("/sprites/player/mando_head.png"));
                g2.drawImage(mando, gp.screenWidth/2 -330, -110, 700, 700, null);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //DRAW TITLE
            g2.setColor(Color.orange);
            g2.drawString(title, x + 5, gp.screenHeight / 2 + 5);
            //draws the main title
            g2.setColor(Color.white);
            g2.drawString(title, x, gp.screenHeight / 2);

            //DRAW MENU
            if (MenuScreenNum == MainMenu) {
                String text = "Play Game";
                //g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
                Mandalore_font_setter(g2);
                x = getXforCentre(text);
                int y = gp.screenHeight / 2 + gp.tileSize * 4;
                g2.drawString(text, x, y);

                text = "Level Select";
                g2.drawString(text, x, y + gp.tileSize);

                text = "Quit Game";
                g2.drawString(text, x, y + gp.tileSize * 2);

                g2.drawString(">", x - gp.tileSize, y + MenuOptionNum * gp.tileSize);
            } else if (MenuScreenNum == LevelSelect) {
                //g2.setFont(g2.getFont().deriveFont(Font.BOLD, 46F));
                Mandalore_font_setter(g2);
                x = getXforCentre("Play Game");
                int y = gp.screenHeight / 2 + gp.tileSize * 4;

                for (int i = 0; i < numLevels; i++) {
                    String text = "Level " + (i + 1);
                    g2.drawString(text, x, y + gp.tileSize * i);
                }

                g2.drawString("Back", x, y + numLevels * gp.tileSize);

                g2.drawString(">", x - gp.tileSize, y + MenuOptionNum * gp.tileSize);
            }


            //will be used in the future to select a menu item
            //if(MenuOptionNum == 0){
            //g2.drawString(">",x-gp.tileSize,y);

            //}


        }
    }

    /**
     * Draws the pause screen.
     */
    public void drawPauseScreen(){
        Mandalore_font_setter(g2);
        String pause = "Game Paused";
        String score =  "Current score: " + gp.player.getScore();
        String resume = "Press [P] to resume";
        String goMenu = "Press [Enter] to return to the menu";
        //no need to set y as it will change with the text provided

        Font titleFont = new Font("Arial", Font.TRUETYPE_FONT, 50);

        // transparent background
        g2.setColor(Color.black);
        int i = 7; // OPACITY
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,i * 0.1f));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        Mandalore_font_setter(g2);
        g2.setColor(Color.white);

        //Center X values for text
        int pauseX =  getXforCentre(pause);
        int scoreX = getXforCentre(score);
        int resumeX = getXforCentre(resume);
        int goMenuX = getXforCentre(goMenu);

        g2.drawString(pause, pauseX, (gp.screenHeight-1) * 3/8);
        g2.drawString(score, scoreX, (gp.screenHeight - 1) /2 );
        g2.drawString(resume, resumeX, ( gp.screenHeight-1) * 5/8);
        g2.drawString(goMenu, goMenuX,  ( gp.screenHeight-1) * 3/4);

    }

    public void drawGameOverScreen(){
        Mandalore_font_setter(g2);
        String gameOver = "Game Over!";
        String score = "Player score: " + gp.player.getScore();
        String goMenu = "Press [Enter] to return to the menu";
        String goExit = "Press [Escape] to exit the application";

        int startingX = 100;
        int startingY = 100;

        int borderX = gp.screenWidth-1;
        int borderY = gp.screenHeight-1;
        Font titleFont = new Font("Arial", Font.TRUETYPE_FONT, 50);

        // transparent background
        g2.setColor(Color.black);
        int i = 7; // OPACITY
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,i * 0.1f));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // inner rectangle
        Rectangle rec = new Rectangle(startingX,startingY,borderX - (2*startingX),borderY - (2*startingY));
        g2.fill(rec);

        // middle of inner rectangle
       /* int width = borderX - (2*startingX);
        int middleX =  getXforCentreScreen(gameOver, width);*/

        // Game over, player score, time text options
        Mandalore_font_setter(g2);
        g2.setColor(Color.white);

        // Game over text, y unit placement
        int gameOverY = (gp.screenHeight-1) * 1/4;

        // x position for text in smaller rectangle
        int gameOverX =  getXforCentre(gameOver);
        int scoreMiddleX = getXforCentre(score);
        int goMenuMiddleX = getXforCentre(goMenu);
        int goExitMiddleX = getXforCentre(goExit);

        //draw "Game Over!" and Player score
        g2.drawString(gameOver, gameOverX, gameOverY);
        g2.drawString(score, scoreMiddleX, gameOverY *3/2);
        g2.drawString(goMenu, goMenuMiddleX,  ( gp.screenHeight-1) * 5/8);
        g2.drawString(goExit, goExitMiddleX, ( gp.screenHeight-1) * 3/4);

    }

    public void drawGameWonScreen(){
        String winner = "Level Complete!";
        String score = "Player score: " + gp.player.getScore();
        String enter = "Press [Enter] to go to the next level";
        String exit = "Press [Escape] to exit the application";

        if(gp.getCurrentLevel() == gp.getNumLevels() - 1) {
            winner = "You have successfully completed the game!";
            enter = "Press [Enter] to return to the menu";
        }

        int startingX = 100;
        int startingY = 100;

        int borderX = gp.screenWidth-1;
        int borderY = gp.screenHeight-1;
        Font titleFont = new Font("Arial", Font.TRUETYPE_FONT, 50);

        // transparent background
        g2.setColor(Color.black);
        int i = 7; // OPACITY
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,i * 0.1f));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // inner rectangle
        Rectangle rec = new Rectangle(startingX,startingY,borderX - (2*startingX),borderY - (2*startingY));
        g2.fill(rec);

        // Game over, player score, time text options
        Mandalore_font_setter(g2);
        g2.setColor(Color.white);

        // Game over text, y unit placement
        int gameOverY = (gp.screenHeight-1) * 1/4;

        // x position for text in smaller rectangle
        int winnerMiddleX =  getXforCentre(winner);
        int scoreMiddleX = getXforCentre(score);
        int goMenuMiddleX = getXforCentre(enter);
        int goExitMiddleX = getXforCentre(exit);

        //draw "Game Over!" and Player score
        g2.drawString(winner, winnerMiddleX, gameOverY);
        g2.drawString(score, scoreMiddleX, gameOverY *3/2);
        g2.drawString(enter, goMenuMiddleX,  ( gp.screenHeight-1) * 5/8);
        g2.drawString(exit, goExitMiddleX, ( gp.screenHeight-1) * 3/4);

    }

    /**
     * Finds the X coordinate to place the given text at for it to appear at the center of the screen.
     * @param text Text to center
     * @return X coordinate that the text should be placed at
     */
    public int getXforCentre(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    public int getXforCentreScreen(String text, int width){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = width/2 - length/2;
        return x;
    }
}

