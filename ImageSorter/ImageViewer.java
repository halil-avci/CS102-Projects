import javax.swing.JFrame;

public class ImageViewer {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Sorter");
        ImageSorter imageSorter = new ImageSorter("CreedJoker.png");

        frame.add(imageSorter);
        frame.setSize(imageSorter.getImageWidth(), imageSorter.getImageHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
