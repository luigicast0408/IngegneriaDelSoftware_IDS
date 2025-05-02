package easyExaple;

public class Main {
    public static void main(String[] args) {
        InitExam director = new InitExam();
        Exam exam = director.makeExam();
        System.out.println(exam);
    }
}
