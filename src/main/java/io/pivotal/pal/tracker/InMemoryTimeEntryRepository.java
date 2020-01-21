package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private final List<TimeEntry> timeEntries = new ArrayList<>();
    private long idSequence = 0;

    public TimeEntry create(TimeEntry timeEntry) {
        idSequence++;
        TimeEntry newTimeEntry = new TimeEntry(
                idSequence,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        timeEntries.add(newTimeEntry);
        return newTimeEntry;
    }

    public TimeEntry find(long id) {
        return timeEntries.stream().filter(te -> te.getId() == id).findFirst().orElse(null);
    }

    public List<TimeEntry> list() {
        return timeEntries;
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry existingTimeEntry = find(id);
        if (existingTimeEntry == null) {
            return null;
        }
        timeEntries.remove(existingTimeEntry);
        TimeEntry newTimeEntry = new TimeEntry(
                id,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        timeEntries.add(newTimeEntry);
        return newTimeEntry;
    }

    public void delete(long id) {
        timeEntries.clear();
    }
}
