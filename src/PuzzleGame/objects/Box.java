package PuzzleGame.objects;

import PuzzleGame.map.Map;
import PuzzleGame.player.Direction;
import PuzzleGame.player.Player;

public class Box {
    private int x;
    private int y;

    // Constructor public
    public Box(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Các hàm Get/Set tọa độ
    public int getX() { 
        return x; 
    }
    
    public int getY() { 
        return y; 
    }
    
    public void setX(int x) { 
        this.x = x; 
    }
    
    public void setY(int y) { 
        this.y = y; 
    }

    // --- CÁC HÀM PLACEHOLDER CHO NGƯỜI 3 LÀM LOGIC ---
    
    // Hàm xử lý đẩy hộp (Dựa theo logic code cũ của nhóm)
    public boolean push(Player player, Direction direction, Map map) {
        // TODO (Người 3): Viết logic check đẩy hộp ở đây
        // Trả về true nếu đẩy thành công, false nếu bị kẹt tường
        return false; 
    }
}