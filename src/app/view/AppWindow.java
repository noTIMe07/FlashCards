package app.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AppWindow extends JFrame {
    private final CenterLayoutLP centerLayoutLP;
    private final SidebarPanel sidebarPanel;


    public AppWindow() {
        super("Flashcard App");

        // Set default settings for JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // Create mainPanel and sidebar
        centerLayoutLP = new CenterLayoutLP();
        sidebarPanel = new SidebarPanel(centerLayoutLP);

        // Add the components
        add(sidebarPanel, BorderLayout.WEST);
        add(centerLayoutLP, BorderLayout.CENTER);

        setupShortcuts();
    }

    private void setupShortcuts() {
        // Remove Space bar activation for all JButtons
        InputMap buttonInputMap = (InputMap) UIManager.get("Button.focusInputMap");
        if (buttonInputMap != null) {
            buttonInputMap.put(KeyStroke.getKeyStroke("SPACE"), "none");
        }

        // Get the root pane of this JFrame
        JRootPane rootPane = this.getRootPane();

        // Get the input map for WHEN_IN_FOCUSED_WINDOW condition
        InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Get the action map
        ActionMap actionMap = rootPane.getActionMap();

        // Bind the keys to the action names
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "flipCard");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), "cardNotLearned");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), "cardLearned");

        // Define what action names do
        actionMap.put("flipCard", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        centerLayoutLP.flipCard();
                    }
                }
        );
        actionMap.put("cardNotLearned", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        centerLayoutLP.setLearned(false);
                    }
                }
        );
        actionMap.put("cardLearned", new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        centerLayoutLP.setLearned(true);
                    }
                }
        );
    }
}