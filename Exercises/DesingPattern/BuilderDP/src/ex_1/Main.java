package ex_1;

public class Main {
    public static void main(String[] args) {
        InitTicket director = new InitTicket();
        Ticket ticket = director.makeConcreteTicket();
        System.out.println(ticket);
    }
}
