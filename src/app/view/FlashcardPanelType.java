package app.view;

public enum FlashcardPanelType {
    FLASHCARD("FlashcardPanel"),
    ADD("AddFlashcardPanel"),
    REMOVE("RemoveFlashcardPanel"),
    REMOVE_CONFIRM("RemoveConfirmationFlashcardPanel"),
    EDIT("EditFlashcardPanel");

    private final String name;

    FlashcardPanelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
