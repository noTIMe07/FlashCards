import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        System.setProperty("sun.java2d.uiScale", "1.0");

        SwingUtilities.invokeLater(() -> {
            AppWindow window = new AppWindow();
            window.setVisible(true);
        });
    }
}

//delete folder function

//add edit remove cards

//dont add empty cards
//make selected folder more obvious even for two folders
//Flip card when clicked anywhere on the card

//Hide card when deck erased