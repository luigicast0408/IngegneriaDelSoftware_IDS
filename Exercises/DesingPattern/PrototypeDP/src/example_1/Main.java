package example_1;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Create a new NoteBook instance
        NoteBook myAgenda = new NoteBook();

        // Add events
        myAgenda.addEvent("Doctor appointment", LocalDateTime.of(2025, 4, 10, 15, 30));
        myAgenda.addEvent("Team meeting", LocalDateTime.of(2025, 4, 11, 9, 0));

        // Print the agenda
        System.out.println("Original Agenda:");
        myAgenda.printAgenda();

        // Clone the agenda
        Agenda clonedAgenda = myAgenda.cloneAgenda();

        // Add another event to the original agenda
        myAgenda.addEvent("Dinner with friends", LocalDateTime.of(2025, 4, 12, 20, 0));

        // Print both agendas to show that the clone is unaffected
        System.out.println("\nOriginal Agenda After Adding New Event:");
        myAgenda.printAgenda();

        System.out.println("\nCloned Agenda:");
        clonedAgenda.printAgenda();
    }
}
