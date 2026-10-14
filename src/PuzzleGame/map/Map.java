package PuzzleGame.map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Lớp quản lý bản đồ game.
 * Hỗ trợ vẽ bản đồ với hiệu ứng bóng đổ cho tường,
 * đường lưới mỏng, và kiểm tra va chạm.
 */
public class Map {
    private Tile[][] grid;
    private int rows;
    private int cols;
    private int tileSize;

    // Biến lưu trữ hình ảnh tile
    private BufferedImage imageFloor;
    private BufferedImage imageWall;

    // Màu sắc cho hiệu ứng vẽ
    private static final Color GRID_LINE_COLOR = new Color(0, 0, 0, 40);        // Đường lưới mờ
    private static final Color WALL_SHADOW_COLOR = new Color(0, 0, 0, 80);      // Bóng đổ tường
    private static final Color WALL_HIGHLIGHT_COLOR = new Color(255, 255, 255, 30); // Viền sáng tường
    private static final Color FLOOR_TINT_DARK = new Color(0, 0, 0, 15);        // Tô đậm sàn (checkerboard)

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
            imageFloor = ImageIO.read(new File("assets/images/map/tile.png"));
            imageWall = ImageIO.read(new File("assets/images/map/wall.png"));
        } catch (IOException e) {
            System.out.println("Lỗi: Không tìm thấy file ảnh Map trong thư mục assets/images/map/");
            e.printStackTrace();
        }
    }

    // === GETTER ===

    /**
     * Lấy tile tại vị trí (row, col). Trả về null nếu ngoài biên.
     */
    public Tile getTile(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return null; // Tránh lỗi vượt quá giới hạn mảng
        }
        return grid[row][col];
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Kiểm tra ô tại (row, col) có đi được không.
     * Trả về false nếu ngoài biên hoặc là tường.
     */
    public boolean isWalkable(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false; // Ngoài biên = không đi được
        }
        if (grid[row][col] == null) {
            return false;
        }
        return grid[row][col].isWalkable();
    }

    // Load dữ liệu map từ mảng 2D
    public void loadMapData(int[][] levelData) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Tile(levelData[r][c]);
            }
        }
    }

    /**
     * Vẽ bản đồ ra màn hình với hiệu ứng đẹp:
     * 1. Vẽ tất cả ô sàn (floor) trước
     * 2. Vẽ tường (wall) với hiệu ứng bóng đổ
     * 3. Vẽ đường lưới mỏng phía trên
     */
    public void draw(Graphics2D g2) {
        // Bật anti-aliasing cho chất lượng render tốt hơn
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // === BƯỚC 1: Vẽ tất cả ô sàn (Floor) ===
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == null) continue;

                int x = c * tileSize;
                int y = r * tileSize;

                // Vẽ sàn cho TẤT CẢ ô (kể cả dưới tường) để không có khoảng trống
                if (imageFloor != null) {
                    g2.drawImage(imageFloor, x, y, tileSize, tileSize, null);
                }

                // Hiệu ứng checkerboard nhẹ cho sàn (xen kẽ tối/sáng)
                if (grid[r][c].getType() == Tile.FLOOR && (r + c) % 2 == 0) {
                    g2.setColor(FLOOR_TINT_DARK);
                    g2.fillRect(x, y, tileSize, tileSize);
                }
            }
        }

        // === BƯỚC 2: Vẽ tường với hiệu ứng bóng đổ ===
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == null) continue;
                if (grid[r][c].getType() != Tile.WALL) continue;

                int x = c * tileSize;
                int y = r * tileSize;

                // Vẽ bóng đổ phía dưới và bên phải tường (offset 4px)
                int shadowOffset = 4;
                g2.setColor(WALL_SHADOW_COLOR);
                g2.fillRect(x + shadowOffset, y + shadowOffset, tileSize, tileSize);

                // Vẽ hình ảnh tường
                if (imageWall != null) {
                    g2.drawImage(imageWall, x, y, tileSize, tileSize, null);
                }

                // Vẽ viền sáng nhẹ ở cạnh trên và trái (tạo hiệu ứng 3D)
                g2.setColor(WALL_HIGHLIGHT_COLOR);
                g2.fillRect(x, y, tileSize, 2);          // Viền trên
                g2.fillRect(x, y, 2, tileSize);          // Viền trái

                // Vẽ viền tối ở cạnh dưới và phải (tạo chiều sâu)
                g2.setColor(new Color(0, 0, 0, 50));
                g2.fillRect(x, y + tileSize - 2, tileSize, 2);  // Viền dưới
                g2.fillRect(x + tileSize - 2, y, 2, tileSize);  // Viền phải
            }
        }

        // === BƯỚC 3: Vẽ đường lưới mỏng ===
        g2.setColor(GRID_LINE_COLOR);
        g2.setStroke(new BasicStroke(1));

        // Đường kẻ ngang
        for (int r = 0; r <= rows; r++) {
            g2.drawLine(0, r * tileSize, cols * tileSize, r * tileSize);
        }
        // Đường kẻ dọc
        for (int c = 0; c <= cols; c++) {
            g2.drawLine(c * tileSize, 0, c * tileSize, rows * tileSize);
        }
    }
}