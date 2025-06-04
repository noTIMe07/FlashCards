import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppWindow window = new AppWindow();
            window.setVisible(true);
        });
    }
}

//when folder user input and focus lost submit file