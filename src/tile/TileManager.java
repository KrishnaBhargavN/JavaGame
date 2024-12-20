package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    GamePanel gp;
    Tile[] tile;
    int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol];

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void loadMap(String location) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(location);
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            int col = 0, row = 0;
            while(row < gp.maxScreenRow) {
                String line = bufferedReader.readLine();
                String[] numbers = line.split(" ");
                while (col < gp.maxScreenCol) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[row][col++] = num;
                }
                row++;
                col = 0;
            }
            bufferedReader.close();
        } catch (Exception e) {

        }
    }

    public void getTileImage() {

        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/grass.png")));
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/wall.png")));
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tiles/water.png")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int col = 0, row = 0;
        int x = 0, y = 0;

        while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
            g2.drawImage(tile[mapTileNum[row][col]].image, x, y, gp.tileSize, gp.tileSize, null);
            col++;
            x += gp.tileSize;
            if(col == gp.maxScreenCol) {
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }

//        for(int i = 0; i < gp.maxScreenRow; i++) {
//            for(int j = 0; j < gp.maxScreenCol; j++) {
//                g2.drawImage(tile[background[i][j]].image, j * gp.tileSize, i * gp.tileSize, gp.tileSize, gp.tileSize, null);
//            }
//        }
    }
}
