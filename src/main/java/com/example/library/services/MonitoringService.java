package com.example.library.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    private final MeterRegistry meterRegistry;

    public MonitoringService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void incrementTotal() {
        Counter.builder("library_requests_total")
                .description("Total requests")
                .register(meterRegistry)
                .increment();
    }

    public void incrementSuccess(String username) {
        Counter.builder("library_requests_user_success")
                .tag("username", username)
                .description("Successful requests per user")
                .register(meterRegistry)
                .increment();
    }

    public void incrementFailure(String username) {
        Counter.builder("library_requests_user_failure")
                .tag("username", username)
                .description("Failed requests per user")
                .register(meterRegistry)
                .increment();
    }
}