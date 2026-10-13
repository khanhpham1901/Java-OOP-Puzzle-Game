package PuzzleGame.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Map {
    private Tile[][] grid; 
    private int rows;
    private int cols;
    private int tileSize; 

    // Biến lưu trữ hình ảnh
    private BufferedImage imageFloor;
    private BufferedImage imageWall;

    public Map(int rows, int cols, int tileSize) {
        this.rows = rows;
        this.cols = cols;
        this.tileSize = tileSize;
        this.grid = new Tile[rows][cols];
        
        loadImages();
    }

    // Hàm đọc file ảnh từ thư mục assets
    private void loadImages() {
        try {
            // Sử dụng đường dẫn tương đối tới thư mục assets của bạn
            // Dựa vào file tree, có vẻ bạn đổi tên floor thành tile.png
            imageFloor = ImageIO.read(new File("assets/images/map/tile.png"));
            imageWall = ImageIO.read(new File("assets/images/map/wall.png"));
        } catch (IOException e) {
            System.out.println("Lỗi: Không tìm thấy file ảnh Map trong thư mục assets/images/map/");
            e.printStackTrace();
        }
    }

    // Getter cho Người 2 (Player) gọi để kiểm tra va chạm
    public Tile getTile(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null; // Tránh lỗi vượt quá giới hạn mảng
        }
        return grid[row][col];
    }

    public int getTileSize() {
        return tileSize;
    }

    // Load dữ liệu map từ mảng 2D (Người 5 sẽ truyền vào)
    public void loadMapData(int[][] levelData) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Tile(levelData[r][c]);
            }
        }
    }

    // Hàm vẽ bản đồ ra màn hình
    public void draw(Graphics2D g2) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == null) continue;

                int x = c * tileSize;
                int y = r * tileSize;

                // Tự động scale ảnh vừa với tileSize
                if (grid[r][c].type == 1) {
                    if (imageWall != null) {
                        g2.drawImage(imageWall, x, y, tileSize, tileSize, null);
                    }
                } else { // type == 0
                    if (imageFloor != null) {
                        g2.drawImage(imageFloor, x, y, tileSize, tileSize, null);
                    }
                }
            }
        }
    }
}