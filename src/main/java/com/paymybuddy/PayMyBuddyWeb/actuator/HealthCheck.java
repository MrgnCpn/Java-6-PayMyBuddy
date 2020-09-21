package com.paymybuddy.paymybuddyweb.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

/**
 * @author MorganCpn
 */
public class HealthCheck implements HealthIndicator {
    @Override
    public Health health() {
        return null;
    }
}
