package example_1;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NoteBook implements Agenda {
    private List<String> notes = new ArrayList<>();
    

    @Override
    public void addEvent(String event, LocalDateTime dataTime) {
        notes.add(event + ","+ dataTime.getDayOfWeek()+" "+ dataTime.getHour() + ":" +dataTime.getMinute());
    }

    @Override
    public void printAgenda() {
        notes.forEach(System.out::println);
    }

    @Override
    public Agenda cloneAgenda() {
       // deep copy
        List<String> notesClone = new ArrayList<>();
        notesClone.addAll(notes);

        NoteBook notesNew = new NoteBook();
        notesNew.notes = notesClone;
        return notesNew;
    }
}
