package io.pivotal.pal.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PalTrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PalTrackerApplication.class, args);
    }

    @Bean
    public TimeEntryRepository theRepo() {
        return new InMemoryTimeEntryRepository();
    }

    @Bean
    public TimeEntryController theController(TimeEntryRepository repo) {
        return new TimeEntryController(repo);
    }
}
