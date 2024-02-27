package com.app.ratelimiter.fixedwindow;

public class Window {
    private long startTime;
    private int requestCount;

    public Window(long startTime, int requestCount) {
        this.startTime = startTime;
        this.requestCount = requestCount;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }
}
