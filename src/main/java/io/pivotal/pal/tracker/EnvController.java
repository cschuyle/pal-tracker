package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

public class EnvController {
    private final String port;
    private final String memoryLimit;
    private final String instanceIndex;
    private final String instanceAddr;

    public EnvController(
            @Value("${PORT: NOT SET}") String port,
            @Value("${MEMORY_LIMIT:NOT SET}") String memoryLimit,
            @Value("${CF_INSTANCE_INDEX:NOT SET}") String instanceIndex,
            @Value("${CF_INSTANCE_ADDR:NOT SET}") String instanceAddr
    ) {
        this.port = port;
        this.memoryLimit = memoryLimit;
        this.instanceIndex = instanceIndex;
        this.instanceAddr = instanceAddr;
    }

    public Map<String, String> getEnv() {
        return Map.of(
                "PORT", port,
                "MEMORY_LIMIT", memoryLimit,
                "CF_INSTANCE_INDEX", instanceIndex,
                "CF_INSTANCE_ADDR", instanceAddr
        );
    }
}
