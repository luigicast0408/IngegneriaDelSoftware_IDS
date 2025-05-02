package easyExaple;

import java.util.Date;

public class InitExam {
    public Exam makeExam(){
        return new Exam.ExamBuilder()
                .setCourseName("IDS")
                .setProfessor("Prof Tramontana")
                .setCFU(9)
                .setDate(new Date())
                .setVote(30)
                .setLaude(true)
                .build();
    }
}
