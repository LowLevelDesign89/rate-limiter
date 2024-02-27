package com.app.ratelimiter.tokenbucket;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

public class TokenBucketRateLimiter {
    private long capacity;
    private AtomicLong tokens;
    private Duration refillRate;
    private long lastRefillTime;

    public TokenBucketRateLimiter(long capacity, Duration refillRate) {
        this.capacity = capacity;
        this.tokens = new AtomicLong(capacity);
        this.refillRate = refillRate;
        lastRefillTime = System.currentTimeMillis();
    }

    public boolean shouldAllowRequest() {
        refillToken();

        if(tokens.get() > 0) {
            tokens.decrementAndGet();
            return true;
        }
        return false;
    }

    private void refillToken() {
        long currentTimeInMillis = System.currentTimeMillis();

        long timeElapsed = (currentTimeInMillis - lastRefillTime);

        long tokensToBeAdded = timeElapsed / refillRate.toMillis();

        if(tokensToBeAdded > 0) {
            lastRefillTime = currentTimeInMillis;
            tokens.getAndUpdate(currentToken -> Math.min(capacity, currentToken + tokensToBeAdded));
        }
    }
}
