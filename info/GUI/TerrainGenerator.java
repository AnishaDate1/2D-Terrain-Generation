package GUI;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class TerrainGenerator {
    private final int width;
    private final int height;
    private final int gridSize;
    private final double[][] noise;

    public TerrainGenerator(int width, int height, int gridSize) {
        this.width = width;
        this.height = height;
        this.gridSize = gridSize;
        this.noise = generateSmoothNoise(gridSize, gridSize);
    }

    private double[][] generateSmoothNoise(int rows, int cols) {
        double[][] baseNoise = new double[rows][cols];
        Random random = new Random();

        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                baseNoise[y][x] = random.nextDouble();

        double[][] smoothNoise = new double[rows][cols];
        for (int y = 0; y < rows; y++)
            for (int x = 0; x < cols; x++)
                smoothNoise[y][x] = averageSurrounding(baseNoise, x, y);

        return smoothNoise;
    }

    private double averageSurrounding(double[][] noise, int x, int y) {
        int count = 0;
        double sum = 0.0;
        int size = noise.length;

        for (int dy = -1; dy <= 1; dy++)
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && ny >= 0 && nx < size && ny < size) {
                    sum += noise[ny][nx];
                    count++;
                }
            }
        return sum / count;
    }

    private Color elevationToColor(double value) {
        if (value < 0.4) return new Color(0, 0, 150);
        if (value < 0.5) return new Color(0, 100, 255);
        if (value < 0.6) return new Color(50, 200, 50);
        if (value < 0.75) return new Color(100, 255, 100);
        if (value < 0.9) return new Color(150, 150, 150);
        return Color.WHITE;
    }

    public BufferedImage drawToImage() {
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        int cellWidth = width / gridSize;
        int cellHeight = height / gridSize;

        for (int y = 0; y < gridSize; y++)
            for (int x = 0; x < gridSize; x++) {
                g.setColor(elevationToColor(noise[y][x]));
                g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
            }

        g.dispose();
        return img;
    }
}