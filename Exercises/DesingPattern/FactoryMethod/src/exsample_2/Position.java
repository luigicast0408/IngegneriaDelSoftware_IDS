package exsample_2;

// ConcreteCreator with a method factory()
public class Position extends GeneratePosition{
    @Override
    public Seat getSeat(int type) {
        return  type == 1 ? new Stage() : new Audience();
    }
}