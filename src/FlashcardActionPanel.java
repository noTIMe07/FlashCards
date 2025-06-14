import javax.swing.*;
import java.awt.*;

public class FlashcardActionPanel extends JPanel {

    private JActionButton addFlashcardButton;
    private JActionButton removeFlashcardButton;
    private JActionButton editFlashcardButton;
    private MainPanel mainPanel;


    public FlashcardActionPanel(MainPanel mainPanel_){
        mainPanel = mainPanel_;

        setLayout();

        addFlashcardButton.addActionListener(e -> {
            mainPanel.setFlashcardPanelType("AddFlashcardPanel");
        });
    }

    public void setLayout(){
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 0, Style.OUTLINE_COLOR),
                BorderFactory.createEmptyBorder(10, 0, 10,0 )
        ));
        setBackground(Style.SIDEBARPANEL_COLOR);
        setOpaque(true);
        setPreferredSize(new Dimension(340, 85));
        setMaximumSize(new Dimension(340, 85));
        setMaximumSize(new Dimension(340, 85));

        JPanel buttonHolderPanel = new JPanel();
        buttonHolderPanel.setOpaque(false);
        setLayout(new GridLayout(1, 3, 4, 0));

        addFlashcardButton = new JActionButton("Add", "/Icons/flashcard_add.png");
        removeFlashcardButton = new JActionButton("Remove", "/Icons/flashcard_remove.png");
        editFlashcardButton = new JActionButton("Edit", "/Icons/flashcard_edit.png");

        buttonHolderPanel.add(addFlashcardButton);
        buttonHolderPanel.add(removeFlashcardButton);
        buttonHolderPanel.add(editFlashcardButton);

        add(buttonHolderPanel);
    }
}
