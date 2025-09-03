package app.view.flashcardPanel;

public enum FlashcardPanelType {
    FLASHCARD("FlashcardPanel"),
    ADD("AddFlashcardPanel"),
    REMOVE("RemoveFlashcardPanel"),
    REMOVE_CONFIRM("RemoveConfirmationFlashcardPanel"),
    EDIT("EditFlashcardPanel"),
    TRAIL("CatTrailPanel"),
    INVENTORY("InventoyPaanel");

    private final String name;

    FlashcardPanelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
