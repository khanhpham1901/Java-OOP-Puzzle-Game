package PuzzleGame.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {
    private GamePanel gp;
    private Font titleFont;
    private Font normalFont;
    private Font smallFont;

    public UI(GamePanel gp) {
        this.gp = gp;
        titleFont = new Font("Arial", Font.BOLD, 60);
        normalFont = new Font("Arial", Font.BOLD, 24);
        smallFont = new Font("Arial", Font.PLAIN, 20);
    }

    public void draw(Graphics2D g2) {
        if (gp.gameState == gp.titleState) {
            drawMainMenu(g2);
        } else if (gp.gameState == gp.playState) {
            drawHUD(g2);
        } else if (gp.gameState == gp.pauseState) {
            drawHUD(g2); 
            drawPauseScreen(g2);
        } else if (gp.gameState == gp.winState) {
            drawWinScreen(g2);
        }
    }

    private void drawMainMenu(Graphics2D g2) {
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(titleFont);
        String text = "PUZZLE GAME";
        int x = getXForCenteredText(text, g2);
        int y = gp.screenHeight / 2 - 50;

        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 4, y + 4);
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        g2.setFont(normalFont);
        String startText = "Press ENTER to Start";
        int startX = getXForCenteredText(startText, g2);
        int startY = gp.screenHeight / 2 + 80;
        g2.drawString(startText, startX, startY);
    }

    private void drawHUD(Graphics2D g2) {
        g2.setFont(smallFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Level: 1", 15, 30); 
    }

    private void drawPauseScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(titleFont);
        g2.setColor(Color.WHITE);
        String text = "PAUSED";
        int x = getXForCenteredText(text, g2);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    private void drawWinScreen(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setFont(titleFont);
        g2.setColor(Color.YELLOW);
        String text = "YOU WIN!";
        int x = getXForCenteredText(text, g2);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);

        g2.setFont(normalFont);
        g2.setColor(Color.WHITE);
        String text2 = "Press ENTER to Restart";
        int x2 = getXForCenteredText(text2, g2);
        int y2 = gp.screenHeight / 2 + 80;
        g2.drawString(text2, x2, y2);
    }

    private int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}