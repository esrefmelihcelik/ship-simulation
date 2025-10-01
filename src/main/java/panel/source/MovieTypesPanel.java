package panel.source;

import com.formdev.flatlaf.FlatDarculaLaf;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MovieTypesPanel extends JPanel {

  private JCheckBox novelCheckBox;
  private JCheckBox romanceCheckBox;
  private JCheckBox horrorCheckBox;
  private JCheckBox comedyCheckBox;
  private JCheckBox scienceCheckBox;

  private JButton saveButton;
  private JButton clearButton;

  private MovieTypes currentMovieTypes;

  public MovieTypesPanel() {
    initializeUI();
    setupEventHandlers();
    currentMovieTypes = null;
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBorder(new EmptyBorder(20, 20, 20, 20));
    try {
      UIManager.setLookAndFeel(new FlatDarculaLaf());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Header
    JLabel headerLabel = new JLabel("Movie Types Selection", JLabel.CENTER);
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
    add(headerLabel, BorderLayout.NORTH);

    // Main content panel
    JPanel mainPanel = new JPanel(new BorderLayout(15, 15));

    // Checkbox panel
    mainPanel.add(createCheckboxPanel(), BorderLayout.CENTER);

    // Button panel
    mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

    add(mainPanel, BorderLayout.CENTER);
  }

  private JPanel createCheckboxPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(null, 1, true),
        new EmptyBorder(25, 30, 25, 30)
    ));

    // Title for the panel
    JLabel titleLabel = new JLabel("Select Movie Genres");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Separator
    JSeparator separator = new JSeparator();
    separator.setAlignmentX(Component.LEFT_ALIGNMENT);
    separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

    // Add title and separator
    panel.add(titleLabel);
    panel.add(Box.createVerticalStrut(15));
    panel.add(separator);
    panel.add(Box.createVerticalStrut(20));

    // Create checkboxes and add them vertically
    novelCheckBox = createCheckBox("Novel");
    romanceCheckBox = createCheckBox("Romance");
    horrorCheckBox = createCheckBox("Horror");
    comedyCheckBox = createCheckBox("Comedy");
    scienceCheckBox = createCheckBox("Science Fiction");

    // Add checkboxes with consistent spacing
    panel.add(novelCheckBox);
    panel.add(Box.createVerticalStrut(12));
    panel.add(romanceCheckBox);
    panel.add(Box.createVerticalStrut(12));
    panel.add(horrorCheckBox);
    panel.add(Box.createVerticalStrut(12));
    panel.add(comedyCheckBox);
    panel.add(Box.createVerticalStrut(12));
    panel.add(scienceCheckBox);

    return panel;
  }

  private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    panel.setBorder(new EmptyBorder(10, 0, 0, 0));

    saveButton = createButton("Save Movie Types", new Color(76, 175, 80)); // Green
    clearButton = createButton("Clear Selection", new Color(244, 67, 54)); // Red

    panel.add(saveButton);
    panel.add(clearButton);

    return panel;
  }

  private void setupEventHandlers() {
    saveButton.addActionListener(e -> saveMovieTypes());

    clearButton.addActionListener(e -> clearSelection());
  }

  private void saveMovieTypes() {
    try {
      // Create MovieTypes object with current checkbox states
      currentMovieTypes = new MovieTypes(
          novelCheckBox.isSelected(),
          romanceCheckBox.isSelected(),
          horrorCheckBox.isSelected(),
          comedyCheckBox.isSelected(),
          scienceCheckBox.isSelected()
      );

      // Show success message with selected genres
      StringBuilder selectedGenres = new StringBuilder();
      if (currentMovieTypes.isNovel()) {
        selectedGenres.append("• Novel\n");
      }
      if (currentMovieTypes.isRomance()) {
        selectedGenres.append("• Romance\n");
      }
      if (currentMovieTypes.isHorror()) {
        selectedGenres.append("• Horror\n");
      }
      if (currentMovieTypes.isComedy()) {
        selectedGenres.append("• Comedy\n");
      }
      if (currentMovieTypes.isScience()) {
        selectedGenres.append("• Science Fiction\n");
      }

      String genresText = selectedGenres.toString();
      if (genresText.isEmpty()) {
        genresText = "No genres selected";
      }

      JOptionPane.showMessageDialog(this,
          "Movie types saved successfully!\n\nSelected genres:\n" + genresText,
          "Success",
          JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this,
          "Error saving movie types: " + ex.getMessage(),
          "Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void clearSelection() {
    novelCheckBox.setSelected(false);
    romanceCheckBox.setSelected(false);
    horrorCheckBox.setSelected(false);
    comedyCheckBox.setSelected(false);
    scienceCheckBox.setSelected(false);
    currentMovieTypes = null;

    JOptionPane.showMessageDialog(this,
        "All selections cleared!",
        "Cleared",
        JOptionPane.INFORMATION_MESSAGE);
  }

  // Getter for the current MovieTypes object
  public MovieTypes getCurrentMovieTypes() {
    return currentMovieTypes;
  }

  // Method to load existing MovieTypes into the form
  public void loadMovieTypes(MovieTypes movieTypes) {
    if (movieTypes != null) {
      novelCheckBox.setSelected(movieTypes.isNovel());
      romanceCheckBox.setSelected(movieTypes.isRomance());
      horrorCheckBox.setSelected(movieTypes.isHorror());
      comedyCheckBox.setSelected(movieTypes.isComedy());
      scienceCheckBox.setSelected(movieTypes.isScience());
      currentMovieTypes = movieTypes;
    }
  }

  // UI Helper methods
  private JCheckBox createCheckBox(String text) {
    JCheckBox checkBox = new JCheckBox(text);
    checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    checkBox.setFocusPainted(false);
    checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Custom checkbox styling
    checkBox.setIcon(createCheckBoxIcon(false));
    checkBox.setSelectedIcon(createCheckBoxIcon(true));
    checkBox.setPressedIcon(createCheckBoxIcon(true));

    return checkBox;
  }

  private Icon createCheckBoxIcon(boolean selected) {
    return new Icon() {
      @Override
      public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (selected) {
          // Draw filled checkbox with checkmark
          g2.setColor(new Color(46, 128, 46));
          g2.fillRoundRect(x, y, 16, 16, 4, 4);

          g2.setColor(Color.WHITE);
          g2.setStroke(new BasicStroke(2));
          // Draw checkmark
          g2.drawLine(x + 3, y + 8, x + 6, y + 11);
          g2.drawLine(x + 6, y + 11, x + 13, y + 4);
        } else {
          // Draw empty checkbox
//          g2.setColor(new Color(180, 180, 180));
          g2.setStroke(new BasicStroke(1.5f));
          g2.drawRoundRect(x, y, 16, 16, 4, 4);
        }

        g2.dispose();
      }

      @Override
      public int getIconWidth() {
        return 16;
      }

      @Override
      public int getIconHeight() {
        return 16;
      }
    };
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

  // Main method for testing the panel independently
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame frame = new JFrame("Movie Types Selector");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setLayout(new BorderLayout());
      try {
        UIManager.setLookAndFeel(new FlatDarculaLaf());
      } catch (Exception e) {
        e.printStackTrace();
      }
      MovieTypesPanel movieTypesPanel = new MovieTypesPanel();
      frame.add(movieTypesPanel, BorderLayout.CENTER);

      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setMinimumSize(new Dimension(400, 500));
      frame.setVisible(true);
    });
  }
}