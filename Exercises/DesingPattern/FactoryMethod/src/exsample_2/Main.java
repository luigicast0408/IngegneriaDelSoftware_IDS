package exsample_2;

// This class use ConcreteCreator
public class Main {
    private static final Position pos = new Position();

    public static void main(String[] args) {
        Seat seat = pos.giveNumber(0);
        Ticket ticket = new Ticket(seat);

        ticket.headerTicket("Luigi");
        System.out.println(ticket.printTicket());
        System.out.println("Cost: "+ticket.getCost());

        new Ticket(pos.giveNumber(0));
        new Ticket(pos.giveNumber(0));
        pos.printPlacesOccupied();
    }
}
