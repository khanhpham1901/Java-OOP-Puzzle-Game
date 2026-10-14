package PuzzleGame.map;

/**
 * Lớp đại diện cho một ô (tile) trên bản đồ.
 * Thuộc phạm vi Người phụ trách Map & UI.
 */
public class Tile {
    // Hằng số loại tile
    public static final int FLOOR = 0;  // Sàn - đi được
    public static final int WALL = 1;   // Tường - không đi được

    // Giữ public để các thành viên khác trong nhóm (Người 2, 3, 5...) truy cập trực tiếp nếu cần
    public int type; 
    private boolean walkable;

    public Tile(int type) {
        this.type = type;
        this.walkable = (type != WALL);
    }

    // === GETTER / HELPER CHO NHÓM ===

    public int getType() {
        return type;
    }

    public boolean isWalkable() {
        return walkable;
    }
}