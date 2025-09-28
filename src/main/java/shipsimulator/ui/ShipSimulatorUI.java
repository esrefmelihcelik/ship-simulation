package shipsimulator.ui;

import com.shipsimulator.data.Ship;
import com.shipsimulator.service.ShipManager;
import com.shipsimulator.util.FileUtil;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ShipSimulatorUI extends JFrame {

  private ShipManager shipManager;
  private Timer simulationTimer;

  // UI Components
  private JSpinner shipCountSpinner;
  private JSlider kinematicUpdateSlider;
  private JSlider identityUpdateSlider;
  private JButton startButton;
  private JButton stopButton;
  private JLabel statusLabel;
  private JTable shipTable;
  private DefaultTableModel tableModel;
  private JTextArea logArea;

  public ShipSimulatorUI() {
    initializeUI();
    loadShipNames();
  }

  private void initializeUI() {
    setTitle("Advanced Ship Simulator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(1200, 800));

    // Apply modern look and feel
    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
    } catch (Exception e) {
      System.err.println("Failed to set modern look and feel");
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception ex) {
        ex.printStackTrace();
      }

    }
    createControlPanel();
    createTablePanel();
    createLogPanel();
    pack();
    setLocationRelativeTo(null);
  }

  private void createControlPanel() {
    JPanel controlPanel = new JPanel(new GridBagLayout());
    controlPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(5, 5, 5, 5);

    // Ship count
    gbc.gridx = 0;
    gbc.gridy = 0;
    controlPanel.add(new JLabel("Number of Ships:"), gbc);

    gbc.gridx = 1;
    shipCountSpinner = new JSpinner(new SpinnerNumberModel(1000, 1, 100000, 1000));
    controlPanel.add(shipCountSpinner, gbc);

    // Kinematic update percentage
    gbc.gridx = 0;
    gbc.gridy = 1;
    controlPanel.add(new JLabel("Kinematic Update %:"), gbc);

    gbc.gridx = 1;
    kinematicUpdateSlider = new JSlider(0, 100, 10);
    kinematicUpdateSlider.setMajorTickSpacing(20);
    kinematicUpdateSlider.setMinorTickSpacing(5);
    kinematicUpdateSlider.setPaintTicks(true);
    kinematicUpdateSlider.setPaintLabels(true);
    controlPanel.add(kinematicUpdateSlider, gbc);

    // Identity update percentage
    gbc.gridx = 0;
    gbc.gridy = 2;
    controlPanel.add(new JLabel("Identity Update %:"), gbc);

    gbc.gridx = 1;
    identityUpdateSlider = new JSlider(0, 100, 2);
    identityUpdateSlider.setMajorTickSpacing(20);
    identityUpdateSlider.setMinorTickSpacing(5);
    identityUpdateSlider.setPaintTicks(true);
    identityUpdateSlider.setPaintLabels(true);
    controlPanel.add(identityUpdateSlider, gbc);

    // Buttons
    gbc.gridx = 0;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    JPanel buttonPanel = new JPanel(new FlowLayout());

    startButton = new JButton("Start Simulation");
    stopButton = new JButton("Stop Simulation");
    stopButton.setEnabled(false);

    startButton.addActionListener(this::startSimulation);
    stopButton.addActionListener(this::stopSimulation);

    buttonPanel.add(startButton);
    buttonPanel.add(stopButton);
    controlPanel.add(buttonPanel, gbc);

    // Status label
    gbc.gridx = 0;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    statusLabel = new JLabel("Ready to simulate");
    statusLabel.setBorder(new EmptyBorder(10, 0, 0, 0));
    controlPanel.add(statusLabel, gbc);

    add(controlPanel, BorderLayout.NORTH);
  }

  private void createTablePanel() {
    String[] columnNames = {"Ship ID", "Name", "IMO", "MMSI", "Call Sign", "Type", "Speed",
        "Course", "Latitude", "Longitude"};
    tableModel = new DefaultTableModel(columnNames, 0);
    shipTable = new JTable(tableModel);
    shipTable.setAutoCreateRowSorter(true);

    JScrollPane scrollPane = new JScrollPane(shipTable);
    scrollPane.setPreferredSize(new Dimension(800, 400));
    add(scrollPane, BorderLayout.CENTER);
  }

  private void createLogPanel() {
    logArea = new JTextArea(10, 80);
    logArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(logArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Simulation Log"));
    add(scrollPane, BorderLayout.SOUTH);
  }

  private void loadShipNames() {
    List<String> shipNames = FileUtil.readShipNames("ship_names.txt");
    shipManager = new ShipManager(shipNames);
    log("Loaded " + shipNames.size() + " ship names");
  }

  private void startSimulation(ActionEvent e) {
    int shipCount = (Integer) shipCountSpinner.getValue();
    double kinematicUpdatePercent = kinematicUpdateSlider.getValue();
    double identityUpdatePercent = identityUpdateSlider.getValue();

    log("Generating " + shipCount + " ships...");
    shipManager.generateInitialShips(shipCount);
    log("Generated " + shipCount + " ships with initial data");

    updateTable();

    // Start simulation timer
    simulationTimer = new Timer(1000, evt -> {
      shipManager.updateShips(kinematicUpdatePercent, identityUpdatePercent);
      updateTable();
      statusLabel.setText("Simulating: " + shipManager.getShipCount() + " ships - " +
          "Kinematic: " + kinematicUpdatePercent + "% - " +
          "Identity: " + identityUpdatePercent + "%");
    });

    simulationTimer.start();

    startButton.setEnabled(false);
    stopButton.setEnabled(true);
    log("Simulation started");
  }

  private void stopSimulation(ActionEvent e) {
    if (simulationTimer != null) {
      simulationTimer.stop();
    }

    startButton.setEnabled(true);
    stopButton.setEnabled(false);
    statusLabel.setText("Simulation stopped");
    log("Simulation stopped");
  }

  private void updateTable() {
    SwingUtilities.invokeLater(() -> {
      tableModel.setRowCount(0);

      // Show only a sample of ships in the table for performance
      Map<Long, Ship> ships = shipManager.getShips();
      ships.values().stream()
          .limit(1000) // Limit display for performance
          .forEach(ship -> {
            Object[] row = {
                ship.getShipId(),
                ship.getIdentity().getShipName(),
                ship.getIdentity().getImoNumber(),
                ship.getIdentity().getMmsiNumber(),
                ship.getIdentity().getCallSign(),
                ship.getIdentity().getShipTypeCategory1(),
                String.format("%.2f m/s", ship.getKinematic().getSpeed()),
                String.format("%.1f°", ship.getKinematic().getCourse()),
                String.format("%.4f", ship.getKinematic().getLatitude()),
                String.format("%.4f", ship.getKinematic().getLongitude())
            };
            tableModel.addRow(row);
          });

      log("Updated table with " + Math.min(ships.size(), 1000) + " ships (showing first 1000)");
    });
  }

  private void log(String message) {
    SwingUtilities.invokeLater(() -> {
      logArea.append("[" + new Date() + "] " + message + "\n");
      logArea.setCaretPosition(logArea.getDocument().getLength());
    });
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ShipSimulatorUI().setVisible(true));
  }
}