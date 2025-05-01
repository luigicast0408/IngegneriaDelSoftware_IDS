package exsample_2;

// Ticket  is  an client of Product Seat
public class Ticket {
    private String name;
    private final Seat seat;

    public Ticket(Seat seat) {
        this.seat = seat;
    }

    public void headerTicket(String name){
        this.name = name;
    }
    public String printTicket(){
        return name.concat(" ").concat(seat.getPosition());
    }

    public String getName(){
        return name;
    }

    public int getCost() {
        return seat.getCost();
    }
}
