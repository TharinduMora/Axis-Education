package com.teamtrace.axiseducation.health;

import com.codahale.metrics.health.HealthCheck;

public class SystemHealthCheck extends HealthCheck {

    @Override
    protected Result check() throws Exception {
        return Result.healthy();
    }
}
