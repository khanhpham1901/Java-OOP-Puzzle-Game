package PuzzleGame.ui;

import PuzzleGame.map.Map;
import PuzzleGame.player.Player;
import PuzzleGame.objects.Box;
import PuzzleGame.objects.Switch;
import PuzzleGame.core.KeyHandler;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GamePanel extends JPanel implements Runnable {
    final int originalTileSize = 16; 
    final int scale = 4; 
    public final int tileSize = originalTileSize * scale; // 64x64 pixel

    public final int maxScreenCol = 12;
    public final int maxScreenRow = 9;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    public Map map;
    public UI ui = new UI(this); 
    
    Thread gameThread;
    int fps = 60;

    // CÁC BIẾN CHỨA ẢNH (Khai báo ở đây để sửa lỗi cannot find symbol)
    public BufferedImage imagePlayer, imageBox, imageSwitch;
    public BufferedImage imageKey, imageDoor, imageTeleport;
    
    // TẠO OBJECT ĐỂ TEST VẼ
    public Player testPlayer = new Player(1, 1);
    public Box testBox = new Box(2, 1);
    public Switch testSwitch = new Switch(3, 1);

    // TRẠNG THÁI GAME
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int winState = 3;

    // BỘ XỬ LÝ BÀN PHÍM
    KeyHandler keyH = new KeyHandler(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true); 
        
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        gameState = titleState; // Bắt đầu mở lên là màn hình Menu
        
        map = new Map(maxScreenRow, maxScreenCol, tileSize);
        
        int[][] testLevel = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1},
            {1, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
        map.loadMapData(testLevel);

        loadObjectImages();
    }

    private void loadObjectImages() {
        try {
            imagePlayer = ImageIO.read(new File("assets/images/player/player_down_idle.png"));
            imageBox = ImageIO.read(new File("assets/images/puzzle objects/box.png"));
            imageSwitch = ImageIO.read(new File("assets/images/puzzle objects/switch.png"));
            imageKey = ImageIO.read(new File("assets/images/puzzle objects/key.png"));
            imageDoor = ImageIO.read(new File("assets/images/puzzle objects/door closed.png"));
            imageTeleport = ImageIO.read(new File("assets/images/teleporter/teleporter_1.png"));
        } catch (IOException e) {
            System.out.println("Lỗi load ảnh nhân vật/đồ vật!");
            e.printStackTrace();
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint(); 
                delta--;
            }
        }
    }

    public void update() {
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (gameState == titleState) {
            ui.draw(g2); // Chỉ vẽ Menu
        } else {
            // 1. Vẽ Map
            map.draw(g2);

            // 2. Vẽ Đồ vật
            if (imageSwitch != null) {
                g2.drawImage(imageSwitch, testSwitch.getX() * tileSize, testSwitch.getY() * tileSize, tileSize, tileSize, null);
            }
            if (imageBox != null) {
                g2.drawImage(imageBox, testBox.getX() * tileSize, testBox.getY() * tileSize, tileSize, tileSize, null);
            }
            
            // Tạm thời vẽ Key và Door ở tọa độ cố định để test hiển thị ảnh
            if (imageKey != null) {
                g2.drawImage(imageKey, 5 * tileSize, 5 * tileSize, tileSize, tileSize, null);
            }
            if (imageDoor != null) {
                g2.drawImage(imageDoor, 6 * tileSize, 1 * tileSize, tileSize, tileSize, null);
            }

            // 3. Vẽ Player (Vẽ sau cùng lớp giữa để đè lên các vật phẩm dưới đất)
            if (imagePlayer != null) {
                g2.drawImage(imagePlayer, testPlayer.getX() * tileSize, testPlayer.getY() * tileSize, tileSize, tileSize, null);
            }

            // 4. Vẽ UI (HUD, Pause, Win) đè lên trên cùng
            ui.draw(g2);
        }

        g2.dispose(); 
    }
}