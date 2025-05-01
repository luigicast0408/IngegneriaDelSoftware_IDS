package example_1;

public class Exam {
    private String subject;
    private int vote;

    public Exam(String subject, int vote){
        this.subject = subject;
        this.vote = vote;
    }

    public  int getVote(){
        return vote;
    }
}
