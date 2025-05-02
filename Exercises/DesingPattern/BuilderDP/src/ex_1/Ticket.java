package ex_1;

public class Ticket {
    private final String eventName;
    private final String date;
    private final double price;

    public Ticket(TicketBuilder builder){
        this.eventName = builder.eventName;
        this.date = builder.date;
        this.price = builder.price;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "eventName='" + eventName + '\'' +
                ", date='" + date + '\'' +
                ", price=" + price +
                '}';
    }

    // Builder inner class
    public static class TicketBuilder {
        private String eventName;
        private String date;
        private double price;

        public TicketBuilder setEventName(String eventName){
            this.eventName = eventName;
            return this;
        }

        public TicketBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public TicketBuilder setPrice(double price) {
            this.price = price;
            return this;
        }

        public Ticket build(){
            return new Ticket(this);
        }
    }
}
