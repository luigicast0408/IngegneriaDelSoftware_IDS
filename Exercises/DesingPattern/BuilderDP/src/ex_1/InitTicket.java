package ex_1;

public class InitTicket {
    public Ticket makeConcreteTicket(){
        return new Ticket.TicketBuilder()
                .setEventName("Uscita")
                .setDate("08-06-2025")
                .setPrice(20.0)
                .build();
    }
}