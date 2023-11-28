package de.tilokowalski.db;

import io.quarkus.test.common.DevServicesContext;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.quarkus.test.junit.QuarkusIntegrationTest;

import java.util.Map;

public class SurrealContainer implements QuarkusTestResourceLifecycleManager {
    @Override
    public Map<String, String> start() {
        return null;
    }

    @Override
    public void stop() {

    }
}
