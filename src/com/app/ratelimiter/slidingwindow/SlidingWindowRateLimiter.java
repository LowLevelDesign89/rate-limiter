package com.app.ratelimiter.slidingwindow;

import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SlidingWindowRateLimiter {
    private long windowSizeInMillis;
    private int maxRequest;

    private ConcurrentHashMap<String, Deque<Long>> requestMap = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(long windowSizeInMillis, int maxRequest) {
        this.windowSizeInMillis = windowSizeInMillis;
        this.maxRequest = maxRequest;
    }

    public boolean shouldAllowUser(String userId) {
        long currentTimeInMillis = System.currentTimeMillis();
        Deque<Long> reqQueue = requestMap.computeIfAbsent(userId, k -> new ConcurrentLinkedDeque<>());

        while(!reqQueue.isEmpty() && reqQueue.peekFirst() < (currentTimeInMillis - windowSizeInMillis)) {
            reqQueue.pollFirst();
        }

        if(reqQueue.size() >= maxRequest)
            return false;
        reqQueue.addLast(currentTimeInMillis);
        requestMap.put(userId, reqQueue);
        return true;
    }
}
