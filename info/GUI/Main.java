package GUI;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        int width = 1024;
        int height = 768;
        int gridSize = 100;

        TerrainGenerator terrain = new TerrainGenerator(width, height, gridSize);
        BufferedImage image = terrain.drawToImage();

        try {
            ImageIO.write(image, "png", new File("terrain.png"));
            System.out.println("Terrain image saved as terrain.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}