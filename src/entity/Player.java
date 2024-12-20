package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;


public class Player extends Entity {

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PlayerMoveUp-1.png")));
            up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PlayerMoveUp-2.png")));
            down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PlayerMoveDown-1.png")));
            down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PlayerMoveDown-2.png")));
            left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PlayerMoveLeft-1.png")));
            left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PlayerMoveLeft-2.png")));
            right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PlayerMoveRight-1.png")));
            right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/PlayerMoveRight-2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update() {

        if(keyH.leftPressed || keyH.downPressed || keyH.upPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
                y -= speed;
            }
            else if(keyH.downPressed) {
                direction = "down";
                y += speed;
            }
            else if(keyH.rightPressed) {
                direction = "right";
                x += speed;
            }
            else if(keyH.leftPressed) {
                direction = "left";
                x -= speed;
            }
            spriteCounter++;
            if(spriteCounter > 15) {
                spriteNum = (spriteNum+1)%2;
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
//        g2.setColor(Color.WHITE);
//        g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch (direction) {
            case "up" :
                if(spriteNum == 1) {
                    image = up1;
                } else {
                    image = up2;
                }
                break;
            case "down" :
                if(spriteNum == 1) {
                    image = down1;
                } else {
                    image = down2;
                }
                break;
            case "right":
                if(spriteNum == 1) {
                    image = right1;
                } else {
                    image = right2;
                }
                break;
            case "left":
                if(spriteNum == 1) {
                    image = left1;
                } else {
                    image = left2;
                }
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
