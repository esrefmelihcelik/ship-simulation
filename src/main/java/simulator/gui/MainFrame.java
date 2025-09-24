package simulator.gui;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import simulator.data.ShipData;
import simulator.generator.IdentityDataGenerator;
import simulator.generator.KinematicDataGenerator;
import simulator.service.DataStreamSimulator;
import simulator.service.ShipDataService;

public class MainFrame extends JFrame {

  private final ShipDataService shipDataService;
  private final DataStreamSimulator simulator;

  // UI Components
  private JTextField shipCountField;
  private JTextField updateIntervalField;
  private JTextField kinematicPercentField;
  private JTextField identityPercentField;
  private JButton startButton;
  private JButton stopButton;
  private JButton configButton;

  private JLabel statusLabel;
  private JLabel shipCountLabel;
  private JLabel updateCountLabel;
  private JLabel memoryLabel;

  private JTable shipTable;
  private DefaultTableModel tableModel;

  private ScheduledExecutorService uiUpdateExecutor;

  private final DecimalFormat decimalFormat = new DecimalFormat("#,##0");

  public MainFrame() {
    this.shipDataService = new ShipDataService(new KinematicDataGenerator(),
        new IdentityDataGenerator());
    this.simulator = new DataStreamSimulator(shipDataService);

    initializeUI();
    setupUIUpdate();

    // Add shutdown hook
    Runtime.getRuntime().addShutdownHook(new Thread(this::cleanup));
  }

  private void initializeUI() {
    setTitle("Ship Data Stream Simulator");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));

    // Apply modern look and feel
    try {
      UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
    } catch (Exception e) {
      try {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    // Create panels
    add(createControlPanel(), BorderLayout.NORTH);
    add(createStatusPanel(), BorderLayout.SOUTH);
    add(createDataPanel(), BorderLayout.CENTER);

    pack();
    setSize(1200, 800);
    setLocationRelativeTo(null); // Center on screen
    setJMenuBar(createMenuBar());
  }

  private JPanel createControlPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));
    panel.setBackground(Color.WHITE);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Title
    gbc.gridwidth = 4;
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel titleLabel = new JLabel("Ship Data Stream Simulator", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    titleLabel.setForeground(new Color(0, 70, 132));
    panel.add(titleLabel, gbc);

    // Configuration fields
    gbc.gridwidth = 1;
    gbc.gridy = 1;

    // Row 1
    gbc.gridx = 0;
    panel.add(new JLabel("Number of Ships:"), gbc);

    gbc.gridx = 1;
    shipCountField = new JTextField("100", 10);
    panel.add(shipCountField, gbc);

    gbc.gridx = 2;
    panel.add(new JLabel("Update Interval (ms):"), gbc);

    gbc.gridx = 3;
    updateIntervalField = new JTextField("1000", 10);
    panel.add(updateIntervalField, gbc);

    // Row 2
    gbc.gridy = 2;
    gbc.gridx = 0;
    panel.add(new JLabel("Kinematic Update %:"), gbc);

    gbc.gridx = 1;
    kinematicPercentField = new JTextField("30", 10);
    panel.add(kinematicPercentField, gbc);

    gbc.gridx = 2;
    panel.add(new JLabel("Identity Update %:"), gbc);

    gbc.gridx = 3;
    identityPercentField = new JTextField("5", 10);
    panel.add(identityPercentField, gbc);

    // Buttons
    gbc.gridy = 3;
    gbc.gridx = 0;
    startButton = new JButton("Start Simulation");
    startButton.setBackground(new Color(34, 139, 34));
    startButton.setForeground(Color.WHITE);
    startButton.addActionListener(new StartButtonListener());
    panel.add(startButton, gbc);

    gbc.gridx = 1;
    stopButton = new JButton("Stop Simulation");
    stopButton.setBackground(new Color(178, 34, 34));
    stopButton.setForeground(Color.WHITE);
    stopButton.setEnabled(false);
    stopButton.addActionListener(e -> stopSimulation());
    panel.add(stopButton, gbc);

    gbc.gridx = 2;
    configButton = new JButton("Apply Configuration");
    configButton.addActionListener(e -> applyConfiguration());
    panel.add(configButton, gbc);

    return panel;
  }

  private JPanel createStatusPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.setBorder(new EmptyBorder(5, 10, 5, 10));
    panel.setBackground(new Color(240, 240, 240));

    statusLabel = new JLabel("Status: Ready");
    statusLabel.setFont(new Font("Arial", Font.BOLD, 12));

    shipCountLabel = new JLabel("Ships: 0");
    updateCountLabel = new JLabel("Updates: 0");
    memoryLabel = new JLabel("Memory: -");

    panel.add(statusLabel);
    panel.add(Box.createHorizontalStrut(20));
    panel.add(shipCountLabel);
    panel.add(Box.createHorizontalStrut(20));
    panel.add(updateCountLabel);
    panel.add(Box.createHorizontalStrut(20));
    panel.add(memoryLabel);

    return panel;
  }

  private JPanel createDataPanel() {
    JPanel panel = new JPanel(new BorderLayout(5, 5));
    panel.setBorder(new EmptyBorder(10, 10, 10, 10));

    // Create table
    String[] columns = {"Ship ID", "Ship Name", "Type", "Speed (m/s)", "Course (°)", "Latitude",
        "Longitude", "IMO", "Last Update"};
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false; // Make table non-editable
      }
    };

    shipTable = new JTable(tableModel);
    shipTable.setAutoCreateRowSorter(true);
    shipTable.setFillsViewportHeight(true);

    // Set column widths
    shipTable.getColumnModel().getColumn(0).setPreferredWidth(60);  // Ship ID
    shipTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Ship Name
    shipTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Type
    shipTable.getColumnModel().getColumn(3).setPreferredWidth(80);  // Speed
    shipTable.getColumnModel().getColumn(4).setPreferredWidth(80);  // Course
    shipTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Latitude
    shipTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Longitude
    shipTable.getColumnModel().getColumn(7).setPreferredWidth(100); // IMO
    shipTable.getColumnModel().getColumn(8).setPreferredWidth(120); // Last Update

    JScrollPane scrollPane = new JScrollPane(shipTable);
    scrollPane.setBorder(new TitledBorder("Ship Data (Real-time Updates)"));

    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private void setupUIUpdate() {
    uiUpdateExecutor = Executors.newSingleThreadScheduledExecutor();
    uiUpdateExecutor.scheduleAtFixedRate(this::updateUI, 0, 1000, TimeUnit.MILLISECONDS);
  }

  private void updateUI() {
    if (!SwingUtilities.isEventDispatchThread()) {
      SwingUtilities.invokeLater(this::updateUI);
      return;
    }

    // Update status labels
    shipCountLabel.setText("Ships: " + decimalFormat.format(shipDataService.getShipCount()));
    updateCountLabel.setText("Updates: " + decimalFormat.format(shipDataService.getTotalUpdates()));
    memoryLabel.setText("Memory: " + getMemoryInfo());

    // Update table data if simulation is running
    if (simulator.isRunning()) {
      updateTableData();
    }
  }

  private void updateTableData() {
    List<ShipData> ships = shipDataService.getAllShips();

    // Clear existing data
    tableModel.setRowCount(0);

    // Add sample data (first 100 ships to avoid performance issues)
    int displayLimit = Math.min(100, ships.size());
    for (int i = 0; i < displayLimit; i++) {
      ShipData ship = ships.get(i);
      Object[] row = {
          ship.getShipId(),
          ship.getIdentityData().getShipName(),
          ship.getIdentityData().getShipType(),
          String.format("%.2f", ship.getKinematicData().getSpeed()),
          String.format("%.1f", ship.getKinematicData().getCourse()),
          String.format("%.6f", ship.getKinematicData().getLatitude()),
          String.format("%.6f", ship.getKinematicData().getLongitude()),
          ship.getIdentityData().getImo(),
          formatTimestamp(ship.getLastUpdateTimestamp())
      };
      tableModel.addRow(row);
    }
  }

  private String formatTimestamp(long timestamp) {
    long secondsAgo = (System.currentTimeMillis() - timestamp) / 1000;
    if (secondsAgo < 60) {
      return secondsAgo + "s ago";
    } else {
      return (secondsAgo / 60) + "m ago";
    }
  }

  private String getMemoryInfo() {
    Runtime runtime = Runtime.getRuntime();
    long usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024);
    long maxMemory = runtime.maxMemory() / (1024 * 1024);
    return usedMemory + "MB / " + maxMemory + "MB";
  }

  private class StartButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      startSimulation();
    }
  }

  private void startSimulation() {
    try {
      int shipCount = Integer.parseInt(shipCountField.getText().trim());
      if (shipCount < 1 || shipCount > 100000) {
        JOptionPane.showMessageDialog(this,
            "Please enter a number between 1 and 100,000",
            "Invalid Input", JOptionPane.ERROR_MESSAGE);
        return;
      }

      applyConfiguration();
      simulator.startSimulation(shipCount);

      // Update UI state
      startButton.setEnabled(false);
      stopButton.setEnabled(true);
      statusLabel.setText("Status: Simulation Running (" + shipCount + " ships)");
      statusLabel.setForeground(Color.GREEN.darker());

    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this,
          "Please enter valid numbers in all fields",
          "Invalid Input", JOptionPane.ERROR_MESSAGE);
    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this,
          "Error starting simulation: " + ex.getMessage(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void stopSimulation() {
    simulator.stopSimulation();

    // Update UI state
    startButton.setEnabled(true);
    stopButton.setEnabled(false);
    statusLabel.setText("Status: Stopped");
    statusLabel.setForeground(Color.RED);

    // Clear table
    tableModel.setRowCount(0);
  }

  private void applyConfiguration() {
    try {
      int updateInterval = Integer.parseInt(updateIntervalField.getText().trim());
      int kinematicPercent = Integer.parseInt(kinematicPercentField.getText().trim());
      int identityPercent = Integer.parseInt(identityPercentField.getText().trim());

      if (updateInterval < 100) {
        updateInterval = 100;
      }
      if (kinematicPercent < 0) {
        kinematicPercent = 0;
      }
      if (kinematicPercent > 100) {
        kinematicPercent = 100;
      }
      if (identityPercent < 0) {
        identityPercent = 0;
      }
      if (identityPercent > 100) {
        identityPercent = 100;
      }

      simulator.setUpdateIntervalMs(updateInterval);
      simulator.setKinematicUpdatePercentage(kinematicPercent);
      simulator.setIdentityUpdatePercentage(identityPercent);

      JOptionPane.showMessageDialog(this,
          "Configuration applied successfully!\n" +
              "Update Interval: " + updateInterval + "ms\n" +
              "Kinematic Updates: " + kinematicPercent + "%\n" +
              "Identity Updates: " + identityPercent + "%",
          "Configuration Updated", JOptionPane.INFORMATION_MESSAGE);

    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this,
          "Please enter valid numbers in configuration fields",
          "Invalid Configuration", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void cleanup() {
    if (uiUpdateExecutor != null) {
      uiUpdateExecutor.shutdown();
    }
    simulator.cleanup();
  }

  private JMenuBar createMenuBar() {
    JMenuBar menuBar = new JMenuBar();

    // File menu
    JMenu fileMenu = new JMenu("File");
    JMenuItem exitItem = new JMenuItem("Exit");
    exitItem.addActionListener(e -> cleanupAndExit());
    fileMenu.add(exitItem);

    // View menu
    JMenu viewMenu = new JMenu("View");
    JMenuItem refreshItem = new JMenuItem("Refresh Data");
    refreshItem.addActionListener(e -> updateTableData());
    viewMenu.add(refreshItem);

    // Help menu
    JMenu helpMenu = new JMenu("Help");
    JMenuItem aboutItem = new JMenuItem("About");
    aboutItem.addActionListener(e -> new AboutDialog(this).setVisible(true));
    helpMenu.add(aboutItem);

    menuBar.add(fileMenu);
    menuBar.add(viewMenu);
    menuBar.add(helpMenu);

    return menuBar;
  }

  private void cleanupAndExit() {
    cleanup();
    System.exit(0);
  }

}