import java.time.LocalDate;

public class Flashcard {
    private String front;
    private String back;
    private int learnedCounter;
    private String dateStudied;

    public Flashcard(String front, String back) {
        this.front = front;
        this.back = back;

        learnedCounter = 0;
        // yyyy-mm-dd
        dateStudied = LocalDate.now().toString();
    }

    public Flashcard(String front, String back, Integer learnedCounter, String dateStudied) {
        this.front = (front != null) ? front : "";
        this.back = (back != null) ? back : "";
        this.learnedCounter = (learnedCounter != null) ? learnedCounter : 0;
        this.dateStudied = (dateStudied != null) ? dateStudied : LocalDate.now().toString();
    }

    public String getFront(){return front;}
    public String getBack(){return back;}
    public int getLearnedCounter(){return learnedCounter;}
    public LocalDate getDateStudied(){
        return (dateStudied != null) ? LocalDate.parse(dateStudied) : LocalDate.now();
    }
    public boolean isLearned(){
        // Calculate due date by squaring learnedCounter and adding it onto last time studied
        LocalDate dueDate = (LocalDate.parse(dateStudied).plusDays(learnedCounter * learnedCounter));
        // Retrun false if due date is not after today
        if (!dueDate.isAfter(LocalDate.now())) return false;
        else return true;
    }

    public void setFront(String front_){front = front_;}
    public void setBack(String back_){back = back_;}
    public void setLearnedCounter(int learnedCounter){this.learnedCounter = learnedCounter;}
    public void setDateStudied(String dateStudied){this.dateStudied = dateStudied;}


    @Override
    public String toString() {
        return "Question: " + front + " | Answer: " + back + " | LearnedCounter: " + learnedCounter +
                " | DateStudied: " + dateStudied;
    }
}