package com.caretrack.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "caretrack.security.jwt")
public class JwtProperties {
    /**
     * ConfigurationProperties used to read a group of related values from configuration files (application.properties or application.yml) and store them in a Java object (bean).
     * Secret used to sign JWTs. In production, inject via environment/secret manager.
     * For HS256, a 32+ byte secret is recommended.
     */
    private String secret;

    /**
     * Token expiration in seconds.
     */
    private long expirationSeconds = 36000;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    public void setExpirationSeconds(long expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }
}
