package de.tilokowalski.db;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

public class SurrealContainer implements QuarkusTestResourceLifecycleManager {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String NAMESPACE = "test";
    private static final String DATABASE = "test";
    GenericContainer<?> container;

    @Override
    public Map<String, String> start() {
        container = new GenericContainer<>(DockerImageName.parse("surrealdb/surrealdb:latest"))
                .withExposedPorts(8000)
                .withCommand("start")
                .withEnv("SURREAL_USER", USERNAME)
                .withEnv("SURREAL_PASS", PASSWORD)
                .waitingFor(new LogMessageWaitStrategy().withRegEx(".*Started web server.*"));

        container.start();

        return Map.of(
                "surreal.host", container.getHost(),
                "surreal.port", String.valueOf(container.getFirstMappedPort()),
                "surreal.username", USERNAME,
                "surreal.password", PASSWORD,
                "surreal.namespace", NAMESPACE,
                "surreal.database", DATABASE
        );
    }

    @Override
    public void stop() {
        container.stop();
    }
}
