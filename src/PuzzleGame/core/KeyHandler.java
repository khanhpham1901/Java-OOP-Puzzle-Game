package PuzzleGame.core;

import PuzzleGame.ui.GamePanel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.playState; 
            }
        } 
        else if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState; 
            }
            if (code == KeyEvent.VK_W) {
                gp.gameState = gp.winState; 
            }
        } 
        else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                gp.gameState = gp.playState; 
            }
        }
        else if (gp.gameState == gp.winState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.titleState; 
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}