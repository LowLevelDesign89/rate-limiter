package com.app.ratelimiter.fixedwindow;

import java.util.HashMap;
import java.util.Map;

public class FixedWindowRatelimiter {
    private Map<Integer, Window> store = new HashMap<>();
    private int maxRequestCount;
    private long windowSizeInMillis;

    public FixedWindowRatelimiter(int maxRequestCount, long windowSizeInMillis) {
        this.maxRequestCount = maxRequestCount;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    public boolean shouldAllowUser(int userId) {
        long currentTimeInMillis = System.currentTimeMillis();
        Window window = store.get(userId);

        if(window == null || window.getStartTime() < (currentTimeInMillis - windowSizeInMillis)) {
            window = new Window(currentTimeInMillis, 0);
        }
        if(window.getRequestCount() >= maxRequestCount) {
            return false;
        }
        window.setRequestCount(window.getRequestCount() + 1);

        store.put(userId, window);
        return true;
    }

}
