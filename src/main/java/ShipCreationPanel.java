import data.ShipTypeCategory1;
import data.ShipTypeCategory2;
import data.ShipTypeCategory3;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import ui.textfield.NumericTextFieldFixedDigit;
import ui.textfield.NumericTextFieldFixedRange;
import ui.textfield.NumericTextFieldFixedRangeDecimal;
import ui.textfield.TextFieldMaxLength;

public class ShipCreationPanel extends JPanel {

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

  public ShipCreationPanel() {
    setLayout(new BorderLayout(15, 15));
    setBorder(new EmptyBorder(20, 20, 20, 20));
    setBackground(Color.WHITE);
    add(createShipSection(), BorderLayout.NORTH);
    add(createIdentitySection(), BorderLayout.CENTER);
    add(createKinematicSection(), BorderLayout.SOUTH);
    setupEventHandlers();
  }

  private JPanel createShipSection() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(createTitledBorder("Ship Information"));
    panel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    gbc.gridx = 0;
    gbc.gridy = 0;
    panel.add(new JLabel("Ship ID:"), gbc);
    gbc.gridx = 1;
    shipIdField = new NumericTextFieldFixedRange("shipIdField", 1, 99999);
    panel.add(shipIdField, gbc);
    return panel;
  }

  private JPanel createIdentitySection() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(createTitledBorder("Identity Information"));
    panel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    int row = 0;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Ship Name:"), gbc);
    gbc.gridx = 1;
    shipNameField = new TextFieldMaxLength("shipNameField", 50);
    panel.add(shipNameField, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("IMO Number:"), gbc);
    gbc.gridx = 1;
    imoNumberField = new NumericTextFieldFixedDigit("imoNumberField", 7);
    panel.add(imoNumberField, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("MMSI Number:"), gbc);
    gbc.gridx = 1;
    mmsiNumberField = new NumericTextFieldFixedDigit("mmsiNumberField", 9);
    panel.add(mmsiNumberField, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Call Sign:"), gbc);
    gbc.gridx = 1;
    callSignField = new TextFieldMaxLength("callSignField", 50);
    panel.add(callSignField, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Category 1:"), gbc);
    gbc.gridx = 1;
    category1Combo = new JComboBox<>(ShipTypeCategory1.values());
    category1Combo.setBackground(Color.WHITE);
    panel.add(category1Combo, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Category 2:"), gbc);
    gbc.gridx = 1;
    category2Combo = new JComboBox<>();
    category2Combo.setBackground(Color.WHITE);
    panel.add(category2Combo, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Category 3:"), gbc);
    gbc.gridx = 1;
    category3Combo = new JComboBox<>();
    category3Combo.setBackground(Color.WHITE);
    panel.add(category3Combo, gbc);
    return panel;
  }

  private JPanel createKinematicSection() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(createTitledBorder("Kinematic Information"));
    panel.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;
    int row = 0;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Speed (m/s):"), gbc);
    gbc.gridx = 1;
    speedField = new NumericTextFieldFixedRangeDecimal("speedField", 0, 20);
    panel.add(speedField, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Course (°):"), gbc);
    gbc.gridx = 1;
    courseField = new NumericTextFieldFixedRangeDecimal("courseField", 0, 360);
    panel.add(courseField, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Latitude (°):"), gbc);
    gbc.gridx = 1;
    latitudeField = new NumericTextFieldFixedRangeDecimal("latitudeField", 34, 44);
    panel.add(latitudeField, gbc);
    row++;
    gbc.gridx = 0;
    gbc.gridy = row;
    panel.add(new JLabel("Longitude (°):"), gbc);
    gbc.gridx = 1;
    longitudeField = new NumericTextFieldFixedRangeDecimal("longitudeField", 23, 42.2d);
    panel.add(longitudeField, gbc);
    return panel;
  }

  private TitledBorder createTitledBorder(String title) {
    TitledBorder border = BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(150, 150, 150)),
        title
    );
    border.setTitleFont(new Font("SansSerif", Font.BOLD, 12));
    border.setTitleColor(new Color(70, 70, 70));
    return border;
  }

  private void setupEventHandlers() {
    category1Combo.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        updateCategory2Options();
      }
    });

    category2Combo.addItemListener(e -> {
      if (e.getStateChange() == ItemEvent.SELECTED) {
        updateCategory3Options();
      }
    });
    updateCategory2Options();
    updateCategory3Options();
  }

  private void updateCategory2Options() {
    ShipTypeCategory1 selectedCategory1 = (ShipTypeCategory1) category1Combo.getSelectedItem();
    category2Combo.removeAllItems();
    if (selectedCategory1 != null) {
      Arrays.stream(ShipTypeCategory2.values())
          .filter(cat2 -> cat2.getShipTypeCategory1() == selectedCategory1)
          .forEach(category2Combo::addItem);
    }
  }

  private void updateCategory3Options() {
    ShipTypeCategory2 selectedCategory2 = (ShipTypeCategory2) category2Combo.getSelectedItem();
    category3Combo.removeAllItems();
    if (selectedCategory2 != null) {
      Arrays.stream(ShipTypeCategory3.values())
          .filter(cat3 -> cat3.getShipTypeCategory2() == selectedCategory2)
          .forEach(category3Combo::addItem);
    }
  }
}
