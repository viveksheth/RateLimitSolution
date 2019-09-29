package com.ratelimiting.service;

public interface RateLimitingService {

    boolean isRequestAllowed(String clientId);
    long nextAvailableTime();
}
