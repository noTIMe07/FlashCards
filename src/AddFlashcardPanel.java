import javax.swing.*;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.io.StringWriter;

public class AddFlashcardPanel extends JPanel {
    private MainPanel mainPanel;
    private JPanel cardContainer;
    private JPanel titleBar;
    private JButton closeButton;
    private JPanel editorPanel;
    private JTextPane frontTextPane;
    private JTextPane backTextPane;
    private JButton submitButton;

    public AddFlashcardPanel(MainPanel mainPanel_) {
        mainPanel = mainPanel_;

        setLayout();
        setupButtons();
    }

    private String styledDocumentToHTML(StyledDocument doc) throws Exception {
        HTMLEditorKit kit = new HTMLEditorKit();
        StringWriter writer = new StringWriter();
        kit.write(writer, doc, 0, doc.getLength());
        return writer.toString();
    }

    private void setupButtons(){
        // When close button is pressed, then update FlashcardPanel and set Panel to FlashcardPanel
        closeButton.addActionListener(e -> {
            mainPanel.study();
            mainPanel.setFlashcardPanelType("FlashcardPanel");
        });

        // When submit button is pressed, then save input and reset Text Panes
        submitButton.addActionListener(e -> {
            try {
                // If front or back empty, then return
                if(frontTextPane.getText().isEmpty()||backTextPane.getText().isEmpty()) return;
                // Get styled document from frontTextPane
                StyledDocument frontDoc = frontTextPane.getStyledDocument();
                String front = styledDocumentToHTML(frontDoc);

                // Get styled document for backTextPane
                StyledDocument backDoc = backTextPane.getStyledDocument();
                String back = styledDocumentToHTML(backDoc);

                // Save flashcard
                mainPanel.addFlashcard(front, back);

                // Reset text panes
                frontTextPane.setText("");
                backTextPane.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private void setLayout(){
        setLayout(new BorderLayout());
        setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        setPreferredSize(new Dimension(750, 500));

        // Create card container to hold title bar, submit button and editor panel
        cardContainer = new JPanel();
        cardContainer.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        cardContainer.setLayout(new BorderLayout());
        cardContainer.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Style.OUTLINE_COLOR, 4, true)
        ));
        add(cardContainer);

        // Custom title bar
        titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(Style.CONFIRM_TOP);
        titleBar.setPreferredSize(new Dimension(400, 40));

        // Close Button to close Panel
        closeButton = new JButton("X");
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setBackground(Style.CONFIRM_TOP);
        closeButton.setForeground(Color.BLACK);
        closeButton.setFont(new Font("Arial", Font.PLAIN, 25));
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.setPreferredSize(new Dimension(55, 40));

        // Add title bar components
        titleBar.add(closeButton, BorderLayout.EAST);
        cardContainer.add(titleBar, BorderLayout.NORTH);

        // Editor panel to hold the text panes
        editorPanel = new JPanel();
        editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));
        editorPanel.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        // Question Label
        JLabel frontLabel = new JLabel("Front:");
        frontLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        frontLabel.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        frontLabel.setOpaque(true);
        frontLabel.setFont(new Font("Arial", Font.BOLD, 20));
        frontLabel.setForeground(Color.BLACK);
        frontLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        editorPanel.add(frontLabel);

        // Scrollable Question TextPane
        frontTextPane = new JTextPane();
        frontTextPane.setFont(new Font("Arial", Font.PLAIN, 20));
        frontTextPane.setForeground(Color.black);
        frontTextPane.setBackground(Color.lightGray);

        JScrollPane frontScroll = new JScrollPane(frontTextPane);
        frontScroll.setPreferredSize(new Dimension(300, 100));
        frontScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        frontScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        frontScroll.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        editorPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        editorPanel.add(frontScroll);

        // Answer Label
        JLabel backLabel = new JLabel("Back:");
        backLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backLabel.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);
        backLabel.setOpaque(true);
        backLabel.setFont(new Font("Arial", Font.BOLD, 20));
        backLabel.setForeground(Color.BLACK);
        backLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 0));
        editorPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        editorPanel.add(backLabel);

        // Scrollable Answer TextPane
        backTextPane = new JTextPane();
        backTextPane.setFont(new Font("Arial", Font.PLAIN, 20));
        backTextPane.setForeground(Color.BLACK);
        backTextPane.setBackground(Color.LIGHT_GRAY);

        JScrollPane backScroll = new JScrollPane(backTextPane);
        backScroll.setPreferredSize(new Dimension(300, 100));
        backScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        backScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 10, 0, 10),
                BorderFactory.createLineBorder(Color.BLACK)
        ));
        backScroll.setBackground(Style.FLASHCARD_BACKGROUND_COLOR);

        // Add editor components
        editorPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        editorPanel.add(backScroll);
        editorPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        cardContainer.add(editorPanel, BorderLayout.CENTER);

        //Submit Button
        submitButton = new JButton("Submit");
        submitButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        submitButton.setBackground(new Color(255, 149, 128));
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        cardContainer.add(submitButton, BorderLayout.SOUTH);
    }
}
