package PuzzleGame;

import PuzzleGame.ui.GamePanel;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        // Tạo cửa sổ game (Frame)
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Puzzle Game - PTIT OOP");

        // Khởi tạo GamePanel (Nơi chứa Map và giao diện)
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // Tự động căn chỉnh kích thước cửa sổ vừa khít với GamePanel
        window.pack(); 

        // Hiển thị cửa sổ ở chính giữa màn hình
        window.setLocationRelativeTo(null); 
        window.setVisible(true);

        // Bắt đầu chạy vòng lặp game (Game Loop)
        gamePanel.startGameThread();
    }
}