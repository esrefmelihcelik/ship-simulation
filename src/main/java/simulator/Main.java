package simulator;


import javax.swing.*;
import simulator.gui.MainFrame;

public class Main {

  public static void main(String[] args) {
    // Set modern look and feel
    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
    } catch (Exception e) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    // Launch the GUI
    SwingUtilities.invokeLater(() -> {
      MainFrame frame = new MainFrame();
      frame.setVisible(true);
    });
  }
}