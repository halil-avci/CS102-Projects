import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GamePanel
 */
public class GamePanel extends JPanel 
{
    Buttons button1;
    Buttons button2;
    Buttons button3;
    Buttons button4;

    GamePanel panel1;
    GamePanel panel2;
    GamePanel panel3;
    GamePanel panel4;
    
    ButtonListener buttonListener;

    public static int score = 0;
    public static int numberOfSquare = 0;
    public static int numberOfGraySquare = 0;
    public static int gameDepth = 0;
    public static boolean isFirstPanel = true;
    public static JFrame gameFrame = new JFrame();


    public GamePanel(int depth) {
        if (isFirstPanel) {
            gameDepth = depth;
        }
        isFirstPanel = false;
        
        buttonListener = new ButtonListener(); 
        this.setLayout(new GridLayout(2, 2));

        if (depth <= 0) {
            button1 = new Buttons();
            button2 = new Buttons();
            button3 = new Buttons();
            button4 = new Buttons();

            button1.addActionListener(buttonListener);
            button2.addActionListener(buttonListener);
            button3.addActionListener(buttonListener);
            button4.addActionListener(buttonListener);

            this.add(button1);
            this.add(button2);
            this.add(button3);
            this.add(button4);

            checkSquares();
            numberOfSquare += 1;
            gameFrame.setTitle(String.valueOf(score));
        }
        else if (depth > 0) {
            panel1 = new GamePanel(depth - 1);
            panel2 = new GamePanel(depth - 1);
            panel3 = new GamePanel(depth - 1);
            panel4 = new GamePanel(depth - 1);

            this.add(panel1);
            this.add(panel2);
            this.add(panel3);
            this.add(panel4);
        }
    }

    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            if ( ((Buttons) e.getSource()).getColor() != 3 ) {
                ((Buttons) e.getSource()).setRandomColor();
                score -= 1;
                checkSquares();
                gameFrame.setTitle(String.valueOf(score));
            }

            if (isGameFinished()) {
                int input = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Color Game", JOptionPane.YES_NO_OPTION);

                if (input == 0) {
                    gameFrame.dispose();
                    score = 0;
                    numberOfGraySquare = 0;
                    numberOfSquare = 0;

                    gameFrame = new JFrame();
                    GamePanel gamePanel = new GamePanel(gameDepth);
                    GamePanel.gameFrame.add(gamePanel);

                    GamePanel.gameFrame.setLocationRelativeTo(null);
                    GamePanel.gameFrame.setSize(400, 400);
                    GamePanel.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    GamePanel.gameFrame.setVisible(true);
                }
                else if (input == 1) {
                    gameFrame.dispose();
                }
            }
        }     
    }

    public int getScore() {
        return score;
    }
    
    public void checkSquares() {
        int color = button1.getColor();
            if (color == button2.getColor() && color == button3.getColor() && color == button4.getColor()) {
                button1.turnGray();
                button2.turnGray();
                button3.turnGray();
                button4.turnGray();

                score += 10;
                numberOfGraySquare += 1;
            }
    }

    public boolean isGameFinished() {
        if (numberOfGraySquare == numberOfSquare || score <= 0) {
            return true;
        } 
        return false;
    }
}