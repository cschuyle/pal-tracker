package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private ArrayList<TimeEntry> timeEntries = new ArrayList<>();
    private long seq = 0;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        ++seq;
        timeEntries.add(newTimeEntry(timeEntry, seq));
        return find(seq);
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        if(timeEntries.size() == 0) {
            return null;
        }
        return timeEntries.get(0);
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntries;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        if (find(id) == null) {
            return null;
        }
        delete(id);
        timeEntries.add(newTimeEntry(timeEntry, id));
        return find(id);
    }

    @Override
    public void delete(long timeEntryId) {
        timeEntries.clear();
    }

    private TimeEntry newTimeEntry(TimeEntry timeEntry, long seq) {
        return new TimeEntry(
                seq,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
    }
}
