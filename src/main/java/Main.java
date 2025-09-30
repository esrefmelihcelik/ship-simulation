import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

  public static void main(String[] args) {
    JFrame frame = new JFrame("Ship Data Management System");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout(10, 10));
    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
    } catch (Exception e) {
      e.printStackTrace();
    }
    frame.add(new ShipCreationPanel(), BorderLayout.CENTER);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setMinimumSize(new Dimension(600, 700));
    SwingUtilities.invokeLater(() -> frame.setVisible(true));

  }

}
