import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;

/**
 * Buttons
 */
public class Buttons extends JButton
{
    private int color;

    public Buttons() {
        setRandomColor();
    }

    public int getColor() {
        return color;
    }

    public void setRandomColor() {
        Random random = new Random();
        int randomInt = random.nextInt(3);
        if (randomInt == 0) {
            this.setBackground(Color.RED);
            color = 0;
        }
        else if (randomInt == 1) {
            this.setBackground(Color.GREEN);
            color = 1;
        }
        else if (randomInt == 2) {
            this.setBackground(Color.BLUE);
            color = 2;
        }
    }

    public void turnGray() {
        this.setBackground(Color.GRAY);
        color = 3;
    }
}