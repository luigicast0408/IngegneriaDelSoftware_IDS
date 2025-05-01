package example_1;

public class Main {
    public static void main(String[] args) {
        // create a creator that manage the creation of students
        StudentCreator studentCreator = new StudentCreator();

        // create an active student
        Student activeStudent = studentCreator.createStudent();
        activeStudent.newExam("IDS",30);
        activeStudent.newExam("Algorithm",30);
        System.out.println("Average of student active: " +activeStudent.getAverage());

        // Suppose that the student stay and begin blocked
        Student blockedStudent = studentCreator.stayStudent(activeStudent);
        blockedStudent.newExam("Chemistry", 26);
        System.out.println("Average of student blocked: " +blockedStudent.getAverage());

        // Restore the student blocked (active)
        Student restoreStudent = studentCreator.restoreStudent(blockedStudent);
        restoreStudent.newExam("Prog2",28);
        System.out.println("Average of student restore: " +restoreStudent.getAverage());
    }
}