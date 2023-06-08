import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * MovingBusFrame
 */
public class MovingBusFrame extends JFrame 
{
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    
    private Label lengthLabel;
    private Label wheelLabel;
    private JTextField lengthField;
    private JTextField wheelField;
    private JButton updateButton;
    private JButton playButton;
    private JButton stopButton;
    private BusComponent bus;
    private Timer timer;
    private ActionListener timerListener;
    private ActionListener buttonListener;

    class TimerListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event) {
            if (bus.getXLeft() + bus.getBusLength() > FRAME_WIDTH) {
                bus.setBusDirection(-1);
            }
            else if (bus.getXLeft() <= 0) {
                bus.setBusDirection(1);
            }
            final int BUS_SPEED = 3;
            bus.moveBus(BUS_SPEED);  
        }
    }

    class ButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == playButton) {
                timer.start();
            }
            else if (event.getSource() == stopButton) {
                timer.stop();
            }
            else if (event.getSource() == updateButton) {
                int length = Integer.parseInt(lengthField.getText());
                int wheel = Integer.parseInt(wheelField.getText());

                bus.updateBus(length, wheel);
            }
        }
    }

    //Constructor
    public MovingBusFrame() {
        timerListener = new TimerListener();
        final int DELAY = 10;
        timer = new Timer(DELAY, timerListener);

        buttonListener = new ButtonListener();
        
        bus = new BusComponent();
        add(bus, BorderLayout.CENTER);
        createControlPanel();

        setTitle("Moving Bus");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
    }

    public void createControlPanel() {
        JPanel updatePanel = createUpdatePanel();
        JPanel buttonPanel = createButtonPanel();

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(1, 2, 100, 0));
        controlPanel.add(updatePanel);
        controlPanel.add(buttonPanel);

        add(controlPanel, BorderLayout.NORTH);
    }

    public JPanel createUpdatePanel() {
        lengthLabel = new Label("Length:");
        wheelLabel = new Label("Wheel:");

        final int FIELD_WIDTH = 4;
        lengthField = new JTextField(FIELD_WIDTH);
        lengthField.setText("200");
        wheelField = new JTextField(FIELD_WIDTH);
        wheelField.setText("2");

        updateButton = new JButton("Update");
        updateButton.addActionListener(buttonListener);

        JPanel textFieldPanel = new JPanel();
        textFieldPanel.setLayout(new GridLayout(2, 2));
        textFieldPanel.add(lengthLabel);
        textFieldPanel.add(lengthField);
        textFieldPanel.add(wheelLabel);
        textFieldPanel.add(wheelField);
        
        JPanel updatePanel = new JPanel();
        updatePanel.add(textFieldPanel, BorderLayout.NORTH);
        updatePanel.add(updateButton, BorderLayout.SOUTH);

        return updatePanel;
    }

    public JPanel createButtonPanel() {
        playButton = new JButton("Play");
        playButton.addActionListener(buttonListener);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(buttonListener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 50, 0));
        buttonPanel.add(playButton);
        buttonPanel.add(stopButton);

        return buttonPanel;
    }
    

}