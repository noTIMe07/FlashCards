import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Globals {
    private static String currentFolderPath = "./src/FlashcardStorage/Default.json";
    private static final PropertyChangeSupport support = new PropertyChangeSupport(Globals.class);

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public static String getCurrentFolderPath() {
        return currentFolderPath;
    }

    public static void setCurrentFolderPath(String newFolderPath) {
        String oldFolderPath = "./src/FlashcardStorage/Default.json";
        currentFolderPath = newFolderPath;
        support.firePropertyChange("currentFolderPath", oldFolderPath, newFolderPath);
    }
}

