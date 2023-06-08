import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * ImageSorter
 */
public class ImageSorter extends JPanel {

    private int timerDelay;
    private ActionListener timerListener;
    private Timer timer;

    private String source;
    private BufferedImage image;
    private JLabel imageComponent;

    private int imageWidth;
    private int imageHeight;

    private boolean isFinishedHorizontal;
    private boolean isFinishedVertical;



    //Constructor
    public ImageSorter(String fileName) {
        try {
            loadImage(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        source = fileName;

        timerDelay = 300;
        timerListener = new TimerListener();
        timer = new Timer(timerDelay, timerListener);

        addKeyListener(new KeyboardListener());
        setFocusable(true);

        isFinishedHorizontal = false;
        isFinishedVertical = false;
        displayImage();
        timer.start();
    }

    class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {
            diagonalStep();
            displayImage();

            if (isFinishedHorizontal && isFinishedVertical) {
                timer.stop();
            }
        }
    }

    class KeyboardListener implements KeyListener
    {
        public void keyPressed(KeyEvent e) {
            String key = KeyStroke.getKeyStrokeForEvent(e).toString().replace("pressed ", "");

            if (key.equals("UP")) {
                timer.stop();
                timerDelay -= 50;
                timer = new Timer(timerDelay, timerListener);
                timer.start();
            }
            else if (key.equals("DOWN")) {
                timer.stop();
                timerDelay += 50;
                timer = new Timer(timerDelay, timerListener);
                timer.start();
            }
            else if (key.equals("R")) {
                try {
                    restart();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        public void keyReleased(KeyEvent e) {}
        public void keyTyped(KeyEvent e) {}
        
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void horizontalStep() {
        boolean pixelsChanged = false;
        int currentPosition = 0;
        while (currentPosition < imageWidth - 1) {
            for (int row = 0; row < imageHeight; row++) {
                int pixel1 = image.getRGB(currentPosition, row);
                int pixel2 = image.getRGB(currentPosition + 1, row);

                if (!isBrighter(pixel1, pixel2)) {
                    image.setRGB(currentPosition, row, pixel2);
                    image.setRGB(currentPosition + 1, row, pixel1);
                    pixelsChanged = true;
                }
            }
            currentPosition++;
        }
        currentPosition = 0;

        if (!pixelsChanged) {
            isFinishedHorizontal = true;
        }
    }

    public void verticalStep() {
        boolean pixelsChanged = false;
        int currentPosition = 0;
        while (currentPosition < imageHeight - 1) {
            for (int column = 0; column < imageWidth; column++) {
                int pixel1 = image.getRGB(column, currentPosition);
                int pixel2 = image.getRGB(column, currentPosition + 1);

                if (!isBrighter(pixel1, pixel2)) {
                    image.setRGB(column, currentPosition, pixel2);
                    image.setRGB(column, currentPosition + 1, pixel1);
                    pixelsChanged = true;
                }
            }
            currentPosition++;
        }
        currentPosition = 0;

        if (!pixelsChanged) {
            isFinishedVertical = true;
        }
    }

    public void diagonalStep() {
        horizontalStep();
        verticalStep();
    }

    public void loadImage(String fileName) throws IOException {
        Image img = ImageIO.read(new File(fileName));
        imageWidth = img.getWidth(null);
        imageHeight = img.getHeight(null);
        
        BufferedImage bfImage = new BufferedImage(img.getWidth(null), 
            img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g = bfImage.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();

        image = bfImage;
    }

    public void displayImage() {
        imageComponent = new JLabel(new ImageIcon(image));
        add(imageComponent);
        revalidate();
        repaint();
    }

    public boolean isBrighter(int pixel1, int pixel2) {
        Color color1 = new Color(pixel1);
        Color color2 = new Color(pixel2);

        if (calculateBrightness(color1) >= calculateBrightness(color2)) {
            return true;
        }
        return false;
    }

    public double calculateBrightness(Color color) {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        return 0.2126 * r + 0.7152 * g + 0.0722 * b;
    }
    
    public void restart() throws IOException {
        removeAll();
        loadImage(source);

        isFinishedHorizontal = false;
        isFinishedVertical = false;

        displayImage();

        timer.start();
    }
}