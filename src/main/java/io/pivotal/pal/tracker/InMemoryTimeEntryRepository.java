package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private final Collection<TimeEntry> timeEntries = new ArrayList<>();
    private long seq = 1;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry createdTimeEntry = newTimeEntry(seq++, timeEntry);
        timeEntries.add(createdTimeEntry);
        return createdTimeEntry;
    }

    private TimeEntry newTimeEntry(long id, TimeEntry timeEntry) {
        return new TimeEntry(id, timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours());
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        if(timeEntries.size() == 0) {
            return null;
        }
        return timeEntries
                .stream()
                .filter( timeEntry -> timeEntry.getId() == timeEntryId)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(timeEntries);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (find(id) == null) {
            return null;
        }
        delete(id);
        TimeEntry updated = newTimeEntry(id, timeEntry);
        timeEntries.add(updated);
        return updated;
    }

    @Override
    public void delete(long id) {
        timeEntries.clear();
    }
}
