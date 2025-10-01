package panel.source;

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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class MovieTypesPanelFirst extends JPanel {
  private JCheckBox novelCheckBox;
  private JCheckBox romanceCheckBox;
  private JCheckBox horrorCheckBox;
  private JCheckBox comedyCheckBox;
  private JCheckBox scienceCheckBox;

  private JButton saveButton;
  private JButton clearButton;

  private MovieTypes currentMovieTypes;

  public MovieTypesPanelFirst() {
    initializeUI();
    setupEventHandlers();
    currentMovieTypes = null;
  }

  private void initializeUI() {
    setLayout(new BorderLayout(10, 10));
    setBackground(Color.WHITE);
    setBorder(new EmptyBorder(20, 20, 20, 20));

    // Header
    JLabel headerLabel = new JLabel("Movie Types Selection", JLabel.CENTER);
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    headerLabel.setForeground(new Color(128, 0, 128)); // Purple theme
    headerLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
    add(headerLabel, BorderLayout.NORTH);

    // Main content panel
    JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
    mainPanel.setBackground(Color.WHITE);

    // Checkbox panel
    mainPanel.add(createCheckboxPanel(), BorderLayout.CENTER);

    // Button panel
    mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

    add(mainPanel, BorderLayout.CENTER);
  }

  private JPanel createCheckboxPanel() {
    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBackground(new Color(250, 245, 255)); // Light purple background
    panel.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(new Color(220, 210, 230), 1, true),
        new EmptyBorder(20, 20, 20, 20)
    ));

    // Title for the panel
    JLabel titleLabel = new JLabel("Select Movie Genres");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
    titleLabel.setForeground(new Color(128, 0, 128));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Title
    gbc.gridx = 0; gbc.gridy = 0;
    gbc.gridwidth = 2;
    panel.add(titleLabel, gbc);

    // Separator
    gbc.gridy = 1;
    gbc.insets = new Insets(10, 0, 20, 0);
    panel.add(createSeparator(), gbc);

    gbc.gridwidth = 1;
    gbc.insets = new Insets(12, 10, 12, 10);

    // Novel Checkbox
    gbc.gridx = 0; gbc.gridy = 2;
    novelCheckBox = createCheckBox("Novel");
    panel.add(novelCheckBox, gbc);

    // Romance Checkbox
    gbc.gridx = 0; gbc.gridy = 3;
    romanceCheckBox = createCheckBox("Romance");
    panel.add(romanceCheckBox, gbc);

    // Horror Checkbox
    gbc.gridx = 0; gbc.gridy = 4;
    horrorCheckBox = createCheckBox("Horror");
    panel.add(horrorCheckBox, gbc);

    // Comedy Checkbox
    gbc.gridx = 0; gbc.gridy = 5;
    comedyCheckBox = createCheckBox("Comedy");
    panel.add(comedyCheckBox, gbc);

    // Science Checkbox
    gbc.gridx = 0; gbc.gridy = 6;
    scienceCheckBox = createCheckBox("Science Fiction");
    panel.add(scienceCheckBox, gbc);

    return panel;
  }

  private JPanel createButtonPanel() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
    panel.setBackground(Color.WHITE);
    panel.setBorder(new EmptyBorder(10, 0, 0, 0));

    saveButton = createButton("Save Movie Types", new Color(76, 175, 80)); // Green
    clearButton = createButton("Clear Selection", new Color(244, 67, 54)); // Red

    panel.add(saveButton);
    panel.add(clearButton);

    return panel;
  }

  private void setupEventHandlers() {
    saveButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        saveMovieTypes();
      }
    });

    clearButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        clearSelection();
      }
    });
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
      if (currentMovieTypes.isNovel()) selectedGenres.append("Novel, ");
      if (currentMovieTypes.isRomance()) selectedGenres.append("Romance, ");
      if (currentMovieTypes.isHorror()) selectedGenres.append("Horror, ");
      if (currentMovieTypes.isComedy()) selectedGenres.append("Comedy, ");
      if (currentMovieTypes.isScience()) selectedGenres.append("Science Fiction, ");

      String genresText = selectedGenres.toString();
      if (genresText.isEmpty()) {
        genresText = "No genres selected";
      } else {
        genresText = genresText.substring(0, genresText.length() - 2);
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
    checkBox.setBackground(new Color(250, 245, 255));
    checkBox.setForeground(Color.DARK_GRAY);
    checkBox.setFocusPainted(false);
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
          g2.setColor(new Color(128, 0, 128));
          g2.fillRoundRect(x, y, 16, 16, 4, 4);

          g2.setColor(Color.WHITE);
          g2.setStroke(new BasicStroke(2));
          // Draw checkmark
          g2.drawLine(x + 3, y + 8, x + 6, y + 11);
          g2.drawLine(x + 6, y + 11, x + 13, y + 4);
        } else {
          // Draw empty checkbox
          g2.setColor(new Color(180, 180, 180));
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

  private JSeparator createSeparator() {
    JSeparator separator = new JSeparator();
    separator.setForeground(new Color(200, 200, 200));
    return separator;
  }

  private JButton createButton(String text, Color backgroundColor) {
    JButton button = new JButton(text);
    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    button.setBackground(backgroundColor);
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Hover effect
    button.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent evt) {
        button.setBackground(backgroundColor.darker());
      }
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

      MovieTypesPanelFirst movieTypesPanelFirst = new MovieTypesPanelFirst();
      frame.add(movieTypesPanelFirst, BorderLayout.CENTER);

      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setMinimumSize(new Dimension(500, 400));
      frame.setVisible(true);
    });
  }
}