package app.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Objects;

public class FolderController {
    private static String currentFolderPath = "./src/FlashcardStorage/Default.json";
    private static final PropertyChangeSupport support = new PropertyChangeSupport(FolderController.class);

    public static void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public static String getCurrentFolderPath() {
        return currentFolderPath;
    }

    public static void setCurrentFolderPath(String newFolderPath) {
        String defaultFolderPath = "./src/FlashcardStorage/Default.json";

        // If new and current folder path are the same then change to default folder path
        if(Objects.equals(currentFolderPath, newFolderPath)) currentFolderPath = defaultFolderPath;
        else currentFolderPath = newFolderPath;

        support.firePropertyChange("currentFolderPath", defaultFolderPath, newFolderPath);
    }
}

