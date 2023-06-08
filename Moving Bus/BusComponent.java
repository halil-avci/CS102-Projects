import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This component displays a bus that can be moved.
 */
public class BusComponent extends JComponent
{  
    private final int WINDOW_SIDE = 40;
    private final int RECT_HEIGHT = 55;
    private final int HOOD_LENGTH = 40;
    private final int WINDOW_GAP = 10;

    private int busLength;
    private int numberOfWheels;
    private double wheelRadius;
    private int busDirection;

    private int xLeft;
    private int xTop;

    //Constructor
    public BusComponent() {
        busLength = 200;
        numberOfWheels = 2;
        wheelRadius = 10;
        busDirection = 1;

        xLeft = 225;
        xTop = 250;
    }

    //Getters
    public int getBusDirection() {
        return busDirection;
    }

    public int getBusLength() {
        return busLength;
    }

    public int getXLeft() {
        return xLeft;
    }

    //Setters
    public void setBusLength(int busLength) {
        this.busLength = busLength;
    }

    public void setNumberOfWheels(int numberOfWheels) {
        this.numberOfWheels = numberOfWheels;
    }

    public void setBusDirection(int number) {
        this.busDirection = number;
    }

    public void paintComponent(Graphics g) {
        //Paints floor
        g.setColor(new Color(129,184,154));
        g.fillRect(0, 325, 800, 200);
        
        //Paints rectangles of bus
        g.setColor(new Color(253, 218, 13));
        g.fillRect(xLeft, xTop, busLength - HOOD_LENGTH, RECT_HEIGHT);
        g.fillRect(xLeft, xTop + RECT_HEIGHT, busLength, RECT_HEIGHT);

        //Paints windows
        int remaining = busLength - HOOD_LENGTH - WINDOW_GAP;
        int counter = 0;
        g.setColor(new Color(204, 255, 255));
        while (remaining >= WINDOW_SIDE) {
            g.fillRect(xLeft + WINDOW_GAP + counter * (WINDOW_GAP + WINDOW_SIDE), xTop + WINDOW_GAP, WINDOW_SIDE, WINDOW_SIDE);
            remaining -= WINDOW_GAP + WINDOW_SIDE;
            counter++;
        }
        
        double wheelSpace = (double) (busLength - 30) / numberOfWheels;
        g.setColor(new Color(102, 51, 0));
        for (int i = 0; i < numberOfWheels; i++) {
            wheelRadius = wheelSpace * 2 / 3;
            g.fillOval((int) (xLeft + wheelSpace / 6 + i * wheelSpace), (int) (xTop + RECT_HEIGHT * 2 - wheelRadius/2), (int) wheelRadius, (int) wheelRadius);
        }
    }

    public void moveBus(int speed) {
        xLeft += busDirection * speed;
        repaint(); 
    }

    public void updateBus(int length, int wheel) {
        busLength = length;
        numberOfWheels = wheel;
        repaint();
    }
    
}
