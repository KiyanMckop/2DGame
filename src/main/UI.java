package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    Font arial_40;
    GamePanel gp;
    BufferedImage image;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        OBJ_Key key = new OBJ_Key();
        image = key.image;
    }

    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2){

        if (gameFinished){

            String text;
            int textLength;
            int x;
            int y;

            text = "Game Over";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 - (gp.tileSize * 3);

            g2.setFont(arial_40);
            g2.setColor(Color.ORANGE);
            g2.drawString(text, x, y);

            g2.drawString("Congradulations!!", x, y);

            //end game
            gp.gameThread = null;

        }else {

            g2.setFont(arial_40);
            g2.setColor(Color.WHITE);
            g2.drawImage(image, gp.tileSize / 2, gp.tileSize / 2, gp.tileSize, gp.tileSize, null);
            g2.drawString(" x " + gp.player.hasKey, 74, 60);

            if (messageOn) {
                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize / 2, gp.tileSize);

                messageCounter++;

                if (messageCounter > 120) {
                    messageCounter = 0;
                    messageOn = false;
                }
            }
        }

    }

}
