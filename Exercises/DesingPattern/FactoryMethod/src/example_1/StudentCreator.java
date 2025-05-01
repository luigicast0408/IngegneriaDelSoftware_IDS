package example_1;
import java.util.*;

public class StudentCreator extends Creator{
    private boolean active = true;
    private Map<Student,Student> blockedStudents = new HashMap<>();


    @Override
    public Student getStudent() {
        return active ? new ActiveStudent() : new BlockedStudent(0);
    }

    @Override
    public Student restoreStudent(Student student) {
        return blockedStudents.remove(student);
    }

    @Override
    public Student stayStudent(Student student) {
        Student stayStudent = new BlockedStudent(student.getAverage());
        blockedStudents.put(stayStudent,student);
        return stayStudent;
    }
}
