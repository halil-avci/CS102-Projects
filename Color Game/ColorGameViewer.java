import javax.swing.JFrame;

/**
 * ColorGameViewer
 */
public class ColorGameViewer {

    public static void main(String[] args) {
        int depth = 1;

        GamePanel gamePanel = new GamePanel(depth);
        GamePanel.gameFrame.add(gamePanel);

        GamePanel.gameFrame.setLocationRelativeTo(null);
        GamePanel.gameFrame.setSize(500, 500);
        GamePanel.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel.gameFrame.setVisible(true);

        double a = 0.2;
        System.out.println(a);
    }
}