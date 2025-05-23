import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        System.out.print("Hello and welcome!\n");

        JFrame frame = new JFrame("Hello World");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel(" World small");
        frame.add(label);

        frame.pack();
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }
}