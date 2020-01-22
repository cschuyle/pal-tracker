package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );
    private ResultSetExtractor<TimeEntry> timeEntryResultSetExtractor = rse -> rse.next() ?
            new TimeEntry(rse.getLong("id"),
                    rse.getLong("project_id"),
                    rse.getLong("user_id"),
                    rse.getDate("date").toLocalDate(),
                    rse.getInt("hours")
            )
            : null;


    public JdbcTimeEntryRepository(DataSource dataSource) {
        jdbc = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbc.update(
                "insert into time_entries (" +
                        "project_id," +
                        "user_id," +
                        "date," +
                        "hours" +
                        ") values (" +
                        ":project_id," +
                        ":user_id," +
                        ":date," +
                        ":hours" +
                        ")",
                new MapSqlParameterSource(Map.of(
                        "project_id", timeEntry.getProjectId(),
                        "user_id", timeEntry.getUserId(),
                        "date", timeEntry.getDate(),
                        "hours", timeEntry.getHours()
                )
                ),
                generatedKeyHolder
        );
        return new TimeEntry(generatedKeyHolder.getKey().longValue(),
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return jdbc.query("select " +
                        "id, " +
                        "project_id, " +
                        "user_id, " +
                        "date, " +
                        "hours " +
                        "from time_entries " +
                        "where id = :id",
                Map.of("id", timeEntryId),
                timeEntryResultSetExtractor
        );
    }

    @Override
    public List<TimeEntry> list() {
        return jdbc.query("select " +
                        "id, " +
                        "project_id, " +
                        "user_id, " +
                        "date, " +
                        "hours " +
                        "from time_entries",
                mapper
        );
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        jdbc.update("update time_entries " +
                "set " +
                "project_id = :project_id, " +
                "user_id = :user_id, " +
                "date = :date, " +
                "hours = :hours " +
                "where id = :id",
                Map.of(
                        "id", id,
                        "project_id", timeEntry.getProjectId(),
                        "user_id", timeEntry.getUserId(),
                        "date", timeEntry.getDate(),
                        "hours", timeEntry.getHours()
                )
        );
        return find(id);
    }

    @Override
    public void delete(long timeEntryId) {
        jdbc.update("delete from time_entries where id = :id", Map.of("id", timeEntryId));
    }
}
