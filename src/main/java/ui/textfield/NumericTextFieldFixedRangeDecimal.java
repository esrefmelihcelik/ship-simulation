package ui.textfield;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import ui.textfield.documentfilter.DecimalDigitDocumentFilter;

public class NumericTextFieldFixedRangeDecimal extends JTextField {

  private static final int MAX_LENGTH = 4;
  private final double minValue;
  private final double maxValue;

  public NumericTextFieldFixedRangeDecimal(String name, double minValue, double maxValue) {
    this.minValue = minValue;
    this.maxValue = maxValue;
    this.setName(name);
    this.setColumns(20);
    this.setToolTipText("Must be a number between [" + this.minValue + ", " + this.maxValue + "]");
    PlainDocument doc = (PlainDocument) this.getDocument();
    doc.setDocumentFilter(new DecimalDigitDocumentFilter(MAX_LENGTH, this.maxValue));
    attachListeners();
  }

  private void attachListeners() {
    getDocument().addDocumentListener(new DocumentListener() {
      @Override
      public void insertUpdate(DocumentEvent e) {
        updateTextFieldColor();
      }

      @Override
      public void removeUpdate(DocumentEvent e) {
        updateTextFieldColor();
      }

      @Override
      public void changedUpdate(DocumentEvent e) {
        updateTextFieldColor();
      }
    });

  }

  private void updateTextFieldColor() {
    String text = getText();
    if (text.isEmpty()) {
      setBackground(Color.WHITE);
      return;
    }
    try {
      double value = Double.parseDouble(text);
      if (value >= minValue && value <= maxValue) {
        setBackground(new Color(151, 255, 154));
      } else {
        setBackground(new Color(255, 151, 151));
      }
    } catch (NumberFormatException e) {
      setBackground(Color.WHITE);
    }
  }

}
