package example_1;

import java.time.LocalDateTime;

public interface Agenda {
    public void addEvent(String event, LocalDateTime dataTime);
    public void printAgenda();
    public  Agenda cloneAgenda();
}
