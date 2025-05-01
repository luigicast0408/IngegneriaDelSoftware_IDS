package example_1;

public class BlockedStudent  implements  Student{
    private  float average;

    public BlockedStudent(float average){
        this.average = average;
    }

    @Override
    public boolean newExam(String subject, int vote) {
        printMessage();
        return false;
    }

    @Override
    public float getAverage() {
        return average;
    }

    private  void printMessage(){
        System.out.println("The student are blocked carer");
    }
}
