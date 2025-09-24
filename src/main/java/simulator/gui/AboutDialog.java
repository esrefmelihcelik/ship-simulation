package simulator.gui;

import javax.swing.*;
import java.awt.*;

public class AboutDialog extends JDialog {
  public AboutDialog(JFrame parent) {
    super(parent, "About Ship Data Stream Simulator", true);
    initializeUI();
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setSize(400, 300);
    setLocationRelativeTo(getParent());

    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Title
    JLabel titleLabel = new JLabel("Ship Data Stream Simulator", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Version
    JLabel versionLabel = new JLabel("Version 1.0.0", JLabel.CENTER);
    versionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
    versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Description
    JTextArea descriptionArea = new JTextArea();
    descriptionArea.setText("A real-time ship data simulation application that generates:\n\n" +
        "• Kinematic Data: Speed, Course, Latitude, Longitude\n" +
        "• Identity Data: IMO, MMSI, Call Sign, Ship Name, Ship Type\n\n" +
        "Features:\n" +
        "• Real-time data stream simulation\n" +
        "• Configurable update rates\n" +
        "• Memory-efficient design\n" +
        "• High-performance concurrent updates");
    descriptionArea.setFont(new Font("Arial", Font.PLAIN, 12));
    descriptionArea.setEditable(false);
    descriptionArea.setBackground(getBackground());
    descriptionArea.setAlignmentX(Component.CENTER_ALIGNMENT);

    // Close button
    JButton closeButton = new JButton("Close");
    closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    closeButton.addActionListener(e -> dispose());

    contentPanel.add(titleLabel);
    contentPanel.add(Box.createVerticalStrut(10));
    contentPanel.add(versionLabel);
    contentPanel.add(Box.createVerticalStrut(20));
    contentPanel.add(descriptionArea);
    contentPanel.add(Box.createVerticalStrut(20));
    contentPanel.add(closeButton);

    add(contentPanel, BorderLayout.CENTER);
  }
}