import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    public MainPanel() {
        setBackground(new Color(232, 220, 200));
        setLayout(new GridBagLayout());

       FlashcardPanel flashCard = new FlashcardPanel ("What is the capital of France" , "Paris");
               add(flashCard);
    }
}