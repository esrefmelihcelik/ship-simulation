package panel;

import com.formdev.flatlaf.FlatDarculaLaf;
import data.Identity;
import data.Kinematic;
import data.Ship;
import data.ShipTypeCategory1;
import data.ShipTypeCategory2;
import data.ShipTypeCategory3;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import ui.textfield.NumericTextFieldFixedDigit;
import ui.textfield.NumericTextFieldFixedRange;
import ui.textfield.NumericTextFieldFixedRangeDecimal;
import ui.textfield.TextFieldMaxLength;

public class ShipInfoSavePanel extends JFrame {

  private NumericTextFieldFixedRange shipIdField;
  private TextFieldMaxLength shipNameField;
  private NumericTextFieldFixedDigit imoNumberField;
  private NumericTextFieldFixedDigit mmsiNumberField;
  private TextFieldMaxLength callSignField;
  private JComboBox<ShipTypeCategory1> category1Combo;
  private JComboBox<ShipTypeCategory2> category2Combo;
  private JComboBox<ShipTypeCategory3> category3Combo;
  private NumericTextFieldFixedRangeDecimal speedField;
  private NumericTextFieldFixedRangeDecimal courseField;
  private NumericTextFieldFixedRangeDecimal latitudeField;
  private NumericTextFieldFixedRangeDecimal longitudeField;

  private JButton saveButton;
  private JButton clearButton;

  private Ship currentShip;

  public ShipInfoSavePanel() {
    initializeUI();
    setupEventHandlers();
    currentShip = null;
  }

  private void initializeUI() {
    setTitle("Ship Data Entry System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout(10, 10));
    // Set modern look and feel
    try {
      UIManager.setLookAndFeel(new FlatDarculaLaf());
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Create main container with padding
    JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    // Header
    JLabel headerLabel = new JLabel("Ship Data Management", JLabel.CENTER);
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
    mainPanel.add(headerLabel, BorderLayout.NORTH);
    // Create the vertical parent panel for the three sections
    JPanel parentPanel = new JPanel();
    parentPanel.setLayout(new BoxLayout(parentPanel, BoxLayout.Y_AXIS));
    parentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
    // Add the three panels to the parent panel
    parentPanel.add(createShipIdPanel());
    parentPanel.add(Box.createVerticalStrut(15)); // Spacing between panels
    parentPanel.add(createIdentityPanel());
    parentPanel.add(Box.createVerticalStrut(15)); // Spacing between panels
    parentPanel.add(createKinematicPanel());
    // Wrap in scroll pane in case content is too long
    JScrollPane scrollPane = new JScrollPane(parentPanel);
    scrollPane.setBorder(BorderFactory.createEmptyBorder());
    scrollPane.getVerticalScrollBar().setUnitIncrement(16);
    mainPanel.add(scrollPane, BorderLayout.CENTER);
    // Button panel
    mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);
    add(mainPanel);
    pack();
    setLocationRelativeTo(null);
    setMinimumSize(new Dimension(700, 800));
  }

  private JPanel createShipIdPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(null, 1, true),
        new EmptyBorder(15, 15, 15, 15)
    ));
    // Title for the panel
    JLabel titleLabel = new JLabel("Ship Identification");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 8, 5, 8);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    // Title
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(titleLabel, gbc);
    // Separator
    gbc.gridy = 1;
    gbc.insets = new Insets(10, 0, 15, 0);
    panel.add(createSeparator(), gbc);
    gbc.gridwidth = 1;
    gbc.insets = new Insets(8, 8, 8, 8);
    // Ship ID
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(createLabel("Ship ID:"), gbc);
    gbc.gridx = 1;
    shipIdField = new NumericTextFieldFixedRange("shipIdField", 1000, 99999);
    panel.add(shipIdField, gbc);
    gbc.gridx = 2;
    panel.add(createInfoLabel(
        "Between " + shipIdField.getMinValue() + " and " + shipIdField.getMaxValue()), gbc);
    return panel;
  }

  private JPanel createIdentityPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(null, 1, true),
        new EmptyBorder(15, 15, 15, 15)
    ));
    // Title for the panel
    JLabel titleLabel = new JLabel("Ship Identity Information");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 8, 5, 8);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    // Title
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    panel.add(titleLabel, gbc);
    // Separator
    gbc.gridy = 1;
    gbc.insets = new Insets(10, 0, 15, 0);
    panel.add(createSeparator(), gbc);
    gbc.gridwidth = 1;
    gbc.insets = new Insets(8, 8, 8, 8);
    // Ship Name
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(createLabel("Ship Name:"), gbc);
    gbc.gridx = 1;
    shipNameField = new TextFieldMaxLength("shipNameField", 50);
    panel.add(shipNameField, gbc);
    gbc.gridx = 2;
    panel.add(createInfoLabel("Max " + shipNameField.getMaxLength() + " characters"), gbc);
    // IMO Number
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(createLabel("IMO Number:"), gbc);
    gbc.gridx = 1;
    imoNumberField = new NumericTextFieldFixedDigit("imoNumberField", 7);
    panel.add(imoNumberField, gbc);
    gbc.gridx = 2;
    panel.add(createInfoLabel(imoNumberField.getRequiredLength() + " digits"), gbc);
    // MMSI Number
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(createLabel("MMSI Number:"), gbc);
    gbc.gridx = 1;
    mmsiNumberField = new NumericTextFieldFixedDigit("mmsiNumberField", 9);
    panel.add(mmsiNumberField, gbc);
    gbc.gridx = 2;
    panel.add(createInfoLabel(mmsiNumberField.getRequiredLength() + " digits"), gbc);
    // Call Sign
    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(createLabel("Call Sign:"), gbc);
    gbc.gridx = 1;
    callSignField = new TextFieldMaxLength("callSignField", 5);
    panel.add(callSignField, gbc);
    gbc.gridx = 2;
    panel.add(createInfoLabel("Max " + callSignField.getMaxLength() + " characters"), gbc);
    // Ship Type Category 1
    gbc.gridx = 0;
    gbc.gridy = 6;
    panel.add(createLabel("Category 1:"), gbc);
    gbc.gridx = 1;
    category1Combo = new JComboBox<>(ShipTypeCategory1.values());
    styleComboBox(category1Combo);
    panel.add(category1Combo, gbc);
    // Ship Type Category 2
    gbc.gridx = 0;
    gbc.gridy = 7;
    panel.add(createLabel("Category 2:"), gbc);
    gbc.gridx = 1;
    category2Combo = new JComboBox<>();
    styleComboBox(category2Combo);
    panel.add(category2Combo, gbc);
    // Ship Type Category 3
    gbc.gridx = 0;
    gbc.gridy = 8;
    panel.add(createLabel("Category 3:"), gbc);
    gbc.gridx = 1;
    category3Combo = new JComboBox<>();
    styleComboBox(category3Combo);
    panel.add(category3Combo, gbc);
    return panel;
  }

  private JPanel createKinematicPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(null, 1, true),
        new EmptyBorder(15, 15, 15, 15)
    ));
    // Title for the panel
    JLabel titleLabel = new JLabel("Kinematic Data");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 8, 5, 8);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    // Title
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    panel.add(titleLabel, gbc);
    // Separator
    gbc.gridy = 1;
    gbc.insets = new Insets(10, 0, 15, 0);
    panel.add(createSeparator(), gbc);
    gbc.gridwidth = 1;
    gbc.insets = new Insets(8, 8, 8, 8);
    // Speed
    gbc.gridx = 0;
    gbc.gridy = 2;
    panel.add(createLabel("Speed (m/s):"), gbc);
    gbc.gridx = 1;
    speedField = new NumericTextFieldFixedRangeDecimal("speedField", 0, 30);
    panel.add(speedField, gbc);
    gbc.gridx = 2;
    panel.add(createInfoLabel(speedField.getMinValue() + "-" + speedField.getMaxValue() + " m/s"),
        gbc);
    // Course
    gbc.gridx = 0;
    gbc.gridy = 3;
    panel.add(createLabel("Course (°):"), gbc);
    gbc.gridx = 1;
    courseField = new NumericTextFieldFixedRangeDecimal("courseField", 0, 360);
    panel.add(courseField, gbc);
    gbc.gridx = 2;
    panel.add(createInfoLabel(courseField.getMinValue() + "-" + courseField.getMaxValue() + " °"),
        gbc);
    // Latitude
    gbc.gridx = 0;
    gbc.gridy = 4;
    panel.add(createLabel("Latitude:"), gbc);
    gbc.gridx = 1;
    latitudeField = new NumericTextFieldFixedRangeDecimal("latitudeField", 34.0000, 54.0000);
    panel.add(latitudeField, gbc);
    gbc.gridx = 2;
    panel.add(
        createInfoLabel(latitudeField.getMinValue() + "-" + latitudeField.getMaxValue() + " °"),
        gbc);
    // Longitude
    gbc.gridx = 0;
    gbc.gridy = 5;
    panel.add(createLabel("Longitude:"), gbc);
    gbc.gridx = 1;
    longitudeField = new NumericTextFieldFixedRangeDecimal("latitudeField", 13.0000, 42.2000);
    panel.add(longitudeField, gbc);
    gbc.gridx = 2;
    panel.add(
        createInfoLabel(longitudeField.getMinValue() + "-" + longitudeField.getMaxValue() + " °"),
        gbc);
    return panel;
  }

  private JSeparator createSeparator() {
    return new JSeparator();
  }

  private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    panel.setBorder(new EmptyBorder(10, 0, 0, 0));
    saveButton = createButton("Save Ship Data", new Color(76, 175, 80)); // Green
    clearButton = createButton("Clear Form", new Color(244, 67, 54)); // Red
    panel.add(saveButton);
    panel.add(clearButton);
    return panel;
  }

  private void setupEventHandlers() {
    // Category 1 change listener to update Category 2
    category1Combo.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        updateCategory2Combo();
      }
    });
    // Category 2 change listener to update Category 3
    category2Combo.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        updateCategory3Combo();
      }
    });
    saveButton.addActionListener(_ -> saveShipData());
    clearButton.addActionListener(_ -> clearForm());
    // Initialize category combos
    updateCategory2Combo();
  }

  private void updateCategory2Combo() {
    ShipTypeCategory1 selectedCategory1 = (ShipTypeCategory1) category1Combo.getSelectedItem();
    category2Combo.removeAllItems();
    if (selectedCategory1 != null) {
      Arrays.stream(ShipTypeCategory2.values())
          .filter(cat2 -> cat2.getShipTypeCategory1() == selectedCategory1)
          .forEach(category2Combo::addItem);
    }
    updateCategory3Combo();
  }

  private void updateCategory3Combo() {
    ShipTypeCategory2 selectedCategory2 = (ShipTypeCategory2) category2Combo.getSelectedItem();
    category3Combo.removeAllItems();
    if (selectedCategory2 != null) {
      Arrays.stream(ShipTypeCategory3.values())
          .filter(cat3 -> cat3.getShipTypeCategory2() == selectedCategory2)
          .forEach(category3Combo::addItem);
    }
  }

  private void saveShipData() {
    try {
      // Validate and create objects
      Identity identity = validateAndCreateIdentity();
      Kinematic kinematic = validateAndCreateKinematic();
      long shipId = validateAndGetShipId();
      // Create Ship object
      currentShip = new Ship(shipId, identity, kinematic);
      JOptionPane.showMessageDialog(this,
          "Ship data saved successfully!\nShip ID: " + shipId +
              "\nShip Name: " + (identity.getShipName() != null ? identity.getShipName() : "N/A") +
              "\nType: " + identity.getShipTypeCategory1() + " / " +
              identity.getShipTypeCategory2() + " / " +
              identity.getShipTypeCategory3(),
          "Success",
          JOptionPane.INFORMATION_MESSAGE);

    } catch (ValidationException ex) {
      JOptionPane.showMessageDialog(this,
          ex.getMessage(),
          "Validation Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private Identity validateAndCreateIdentity() throws ValidationException {
    // Validate required fields
    if (category1Combo.getSelectedItem() == null) {
      throw new ValidationException("Ship Type Category 1 is required");
    }
    if (category2Combo.getSelectedItem() == null) {
      throw new ValidationException("Ship Type Category 2 is required");
    }
    if (category3Combo.getSelectedItem() == null) {
      throw new ValidationException("Ship Type Category 3 is required");
    }
    String shipName = shipNameField.getText().trim();
    if (shipName.length() > shipNameField.getMaxLength()) {
      throw new ValidationException(
          "Ship name cannot exceed " + shipNameField.getMaxLength() + " characters");
    }
    // Validate IMO number
    Integer imoNumber = null;
    String imoText = imoNumberField.getText().trim();
    if (!imoText.isEmpty()) {
      try {
        imoNumber = Integer.parseInt(imoText);
        if (String.valueOf(imoNumber).length() != imoNumberField.getRequiredLength()) {
          throw new ValidationException(
              "IMO number must be exactly " + imoNumberField.getRequiredLength() + " digits");
        }
      } catch (NumberFormatException e) {
        throw new ValidationException(
            "IMO number must be a valid " + imoNumberField.getRequiredLength() + "-digit number");
      }
    }
    // Validate MMSI number
    Integer mmsiNumber = null;
    String mmsiText = mmsiNumberField.getText().trim();
    if (!mmsiText.isEmpty()) {
      try {
        mmsiNumber = Integer.parseInt(mmsiText);
        if (String.valueOf(mmsiNumber).length() != mmsiNumberField.getRequiredLength()) {
          throw new ValidationException(
              "MMSI number must be exactly " + mmsiNumberField.getRequiredLength() + " digits");
        }
      } catch (NumberFormatException e) {
        throw new ValidationException(
            "MMSI number must be a valid " + mmsiNumberField.getRequiredLength() + "-digit number");
      }
    }
    String callSign = callSignField.getText().trim();
    if (callSign.length() > callSignField.getMaxLength()) {
      throw new ValidationException(
          "Call sign cannot exceed " + callSignField.getMaxLength() + " characters");
    }
    return new Identity(
        shipName.isEmpty() ? null : shipName,
        imoNumber != null ? imoNumber : 0,
        mmsiNumber != null ? mmsiNumber : 0,
        callSign.isEmpty() ? null : callSign,
        (ShipTypeCategory1) category1Combo.getSelectedItem(),
        (ShipTypeCategory2) category2Combo.getSelectedItem(),
        (ShipTypeCategory3) category3Combo.getSelectedItem()
    );
  }

  private Kinematic validateAndCreateKinematic() throws ValidationException {
    double speed = parseDoubleField(speedField.getText().trim(), "Speed");
    if (speed < speedField.getMinValue() || speed > speedField.getMaxValue()) {
      throw new ValidationException(
          "Speed must be between " + speedField.getMinValue() + " and " + speedField.getMaxValue()
              + " m/s");
    }
    double course = parseDoubleField(courseField.getText().trim(), "Course");
    if (course < courseField.getMinValue() || course > courseField.getMaxValue()) {
      throw new ValidationException("Course must be between " + courseField.getMinValue() + " and "
          + courseField.getMaxValue() + " degrees");
    }
    double latitude = parseDoubleField(latitudeField.getText().trim(), "Latitude");
    if (latitude < latitudeField.getMinValue() || latitude > latitudeField.getMaxValue()) {
      throw new ValidationException(
          "Latitude must be between " + latitudeField.getMinValue() + " and "
              + latitudeField.getMaxValue());
    }
    double longitude = parseDoubleField(longitudeField.getText().trim(), "Longitude");
    if (longitude < longitudeField.getMinValue() || longitude > longitudeField.getMaxValue()) {
      throw new ValidationException(
          "Longitude must be between " + longitudeField.getMinValue() + " and "
              + longitudeField.getMaxValue());
    }
    return new Kinematic(speed, course, latitude, longitude);
  }

  private long validateAndGetShipId() throws ValidationException {
    String shipIdText = shipIdField.getText().trim();
    if (shipIdText.isEmpty()) {
      throw new ValidationException("Ship ID is required");
    }
    try {
      long shipId = Long.parseLong(shipIdText);
      if (shipId < shipIdField.getMinValue() || shipId > shipIdField.getMaxValue()) {
        throw new ValidationException(
            "Ship ID must be between " + shipIdField.getMinValue() + " and "
                + shipIdField.getMaxValue());
      }
      return shipId;
    } catch (NumberFormatException e) {
      throw new ValidationException("Ship ID must be a valid number between 1000 and 100000");
    }
  }

  private double parseDoubleField(String text, String fieldName) throws ValidationException {
    if (text.isEmpty()) {
      throw new ValidationException(fieldName + " is required");
    }
    try {
      return Double.parseDouble(text);
    } catch (NumberFormatException e) {
      throw new ValidationException(fieldName + " must be a valid number");
    }
  }

  private void clearForm() {
    shipIdField.setText("");
    shipNameField.setText("");
    imoNumberField.setText("");
    mmsiNumberField.setText("");
    callSignField.setText("");
    category1Combo.setSelectedIndex(0);
    speedField.setText("");
    courseField.setText("");
    latitudeField.setText("");
    longitudeField.setText("");
    currentShip = null;
  }

  // UI Helper methods
  private JLabel createLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    return label;
  }

  private JLabel createInfoLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Segoe UI", Font.ITALIC, 11));
    return label;
  }

  private void styleComboBox(JComboBox<?> comboBox) {
    comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    comboBox.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(null),
        BorderFactory.createEmptyBorder(5, 8, 5, 8)
    ));
  }

  private JButton createButton(String text, Color backgroundColor) {
    JButton button = new JButton(text);
    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    button.setBackground(backgroundColor);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    // Hover effect
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(backgroundColor.darker());
      }

      @Override
      public void mouseExited(java.awt.event.MouseEvent evt) {
        button.setBackground(backgroundColor);
      }
    });
    return button;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new ShipInfoSavePanel().setVisible(true));
  }

  // Custom exception for validation errors
  private static class ValidationException extends Exception {

    public ValidationException(String message) {
      super(message);
    }
  }
}