package app.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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
        String oldFolderPath = currentFolderPath;
        currentFolderPath = newFolderPath;
        support.firePropertyChange("currentFolderPath", oldFolderPath, newFolderPath);
    }

}

