package de.tilokowalski.db.config;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "surreal")
public interface SurrealConfig {
    String host();
    Integer port();
    String username();
    String password();
    String database();
    String namespace();
}
