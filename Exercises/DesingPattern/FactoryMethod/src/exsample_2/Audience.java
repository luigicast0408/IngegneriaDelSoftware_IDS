package exsample_2;

import java.util.Random;

//ConcreteProduct
public class Audience implements Seat {
    private final String[] audienceNames = {"A","B","C","D","E","F"};
    private final int number;
    private final int row;

    public Audience(){
        number = new Random().nextInt(10) + 1;
        row = new Random().nextInt(5) + 1;
    }

    @Override
    public String getPosition() {
        return audienceNames[row].concat(Integer.toString(number));
    }

    @Override
    public int getCost() {
        if (number > 5 && rowMax())
            return  100;
        if (number > 5 && rowMin())
            return 80;
        return 60;
    }

    @Override
    public String getSector() {
       return row == 0 ? "Reserved" : "Normal";
    }

    private boolean rowMin(){
        return  (row == 0 || row == 5);
    }

    private boolean rowMax(){
        return  (row >= 1 &&  row <= 5);
    }
}
