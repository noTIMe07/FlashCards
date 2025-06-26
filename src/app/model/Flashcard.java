package app.model;

public class Flashcard {
    private String front;
    private String back;
    private boolean learned;

    public Flashcard(String front, String back, boolean learned) {
        this.front = front;
        this.back = back;
        this.learned = learned;
    }

    public String getFront(){return front;}
    public String getBack(){return back;}
    public Boolean getLearned(){return learned;}

    public void setFront(String front_){front = front_;}
    public void setBack(String back_){back = back_;}
    public void setLearned(Boolean learned_){learned=learned_;}


    @Override
    public String toString() {
        return "Question: " + front + " | Answer: " + back + " | Learned: " + learned;
    }
}
