package com.example.library.services;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    private final Counter totalRequestsCounter;
    private final Counter successRequestsCounter;
    private final Counter failureRequestsCounter;

    public MonitoringService(MeterRegistry registry) {
        this.totalRequestsCounter = Counter.builder("library_requests_total")
                .description("Total number of requests to library API")
                .register(registry);

        this.successRequestsCounter = Counter.builder("library_requests_success")
                .description("Total number of successful requests")
                .register(registry);

        this.failureRequestsCounter = Counter.builder("library_requests_failure")
                .description("Total number of failed requests")
                .register(registry);
    }

    public void incrementTotal() { totalRequestsCounter.increment(); }
    public void incrementSuccess() { successRequestsCounter.increment(); }
    public void incrementFailure() { failureRequestsCounter.increment(); }
}