package com.pedrohk.stress;

public record StressResult(long totalRequests, long successfulRequests, long failedRequests, long durationMs) {
    public double requestsPerSecond() {
        return (totalRequests / (durationMs / 1000.0));
    }
}
