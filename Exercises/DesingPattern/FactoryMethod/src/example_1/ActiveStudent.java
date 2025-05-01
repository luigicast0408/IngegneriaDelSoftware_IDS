package example_1;
import  java.util.*;

public class ActiveStudent  implements  Student{
    private List<Exam> exams = new ArrayList<>();

    @Override
    public boolean newExam(String subject, int vote) {
        exams.add(new Exam(subject,vote));
        System.out.println("Subject: "+subject + " " + "Vote: "+vote);
        return true;
    }

    @Override
    public float getAverage() {
        if (exams.isEmpty())
            return  0;
        float sum = 0;
        for (Exam exam : exams)
            sum += exam.getVote();
        return  sum/ exams.size();
    }
}
