package com.fm.contactus.messages.application.service;

import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fm.contactus.messages.application.exception.RateLimitExceededException;

@Service
public class MessageRateLimitService {

    private final int maxRequests;
    private final long windowMillis;
    private final ConcurrentHashMap<String, Deque<Long>> requestTimestampsByKey = new ConcurrentHashMap<>();

    public MessageRateLimitService(
        @Value("${contactus.abuse.max-requests:10}") int maxRequests,
        @Value("${contactus.abuse.window-seconds:60}") int windowSeconds
    ) {
        if (maxRequests <= 0) {
            throw new IllegalArgumentException("contactus.abuse.max-requests must be greater than zero");
        }
        if (windowSeconds <= 0) {
            throw new IllegalArgumentException("contactus.abuse.window-seconds must be greater than zero");
        }
        this.maxRequests = maxRequests;
        this.windowMillis = windowSeconds * 1000L;
    }

    public void validateRequest(Long projectId, String ipAddress) {
        String key = buildKey(projectId, ipAddress);
        long now = System.currentTimeMillis();
        Deque<Long> timestamps = requestTimestampsByKey.computeIfAbsent(key, unused -> new ConcurrentLinkedDeque<>());

        synchronized (timestamps) {
            purgeExpiredTimestamps(timestamps, now);

            if (timestamps.size() >= maxRequests) {
                throw new RateLimitExceededException("Rate limit exceeded for this endpoint");
            }

            timestamps.addLast(now);
        }
    }

    private String buildKey(Long projectId, String ipAddress) {
        String resolvedProjectId = projectId == null ? "unknown-project" : projectId.toString();
        String resolvedIp = (ipAddress == null || ipAddress.isBlank()) ? "unknown-ip" : ipAddress;
        return resolvedProjectId + ":" + resolvedIp;
    }

    private void purgeExpiredTimestamps(Deque<Long> timestamps, long now) {
        while (!timestamps.isEmpty()) {
            Long oldest = timestamps.peekFirst();
            if (oldest == null || now - oldest > windowMillis) {
                timestamps.pollFirst();
                continue;
            }
            break;
        }
    }
}
