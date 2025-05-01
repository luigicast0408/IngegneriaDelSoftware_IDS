package example_1;

public  abstract class  Creator {
    public int numberInstances;

    public abstract Student getStudent();

    public  Student createStudent(){
        numberInstances++;
        return  getStudent();
    }

    public int getNumberInstances(){
        return numberInstances;
    }

    public abstract Student restoreStudent(Student student);

    public abstract Student stayStudent(Student student);
}
