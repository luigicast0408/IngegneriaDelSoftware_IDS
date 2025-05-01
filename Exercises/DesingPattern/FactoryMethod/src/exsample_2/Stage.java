package exsample_2;

import java.util.Random;

// ConcreteProduct
public class Stage implements Seat{

    private  int number;

    public Stage() {
        number = new Random().nextInt(20)+ 1;
    }


    @Override
    public String getPosition() {
       return  Integer.toString(number);
    }

    @Override
    public int getCost() {
        return number > 10 ? 50 : 40;
    }

    @Override
    public String getSector() {
        if (number == 20){
            return "Middle";
        } if (number > 10){
            return "Green";
        }
        return "Blue";
    }
}
