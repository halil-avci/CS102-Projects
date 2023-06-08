import javax.swing.JFrame;

/**
 * MovingBusViewer
 */
public class MovingBusViewer {

    public static void main(String[] args) {
        JFrame busFrame = new MovingBusFrame();

        busFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        busFrame.setVisible(true);     
    }
}