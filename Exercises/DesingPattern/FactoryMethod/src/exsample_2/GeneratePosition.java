package exsample_2;
import java.util.Set;
import java.util.TreeSet;

//Creator

public abstract class GeneratePosition{
    private static final int MAX_SEAT = 100;
    private final Set<String> seats = new TreeSet<>(); //
    public Seat giveNumber(int number) {
        if (seats.size() == MAX_SEAT)
            return null;
        Seat seat;
        do {
            seat = getSeat(number);
        } while (seats.contains(seat.getPosition()));

        return seat;
    }

    public void printPlacesOccupied(){
      seats.forEach(e -> System.out.println(e));
    }

    public abstract Seat getSeat(int type);
}
