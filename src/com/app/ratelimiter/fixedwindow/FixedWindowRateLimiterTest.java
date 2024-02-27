package com.app.ratelimiter.fixedwindow;

public class FixedWindowRateLimiterTest {
    public static void main(String[] args) throws InterruptedException {
        long windowSizeInMillis = 60 * 1000;
        FixedWindowRatelimiter fixedWindowRatelimiter = new FixedWindowRatelimiter(10,
                windowSizeInMillis);

        for(int i = 0; i < 12; i++) {
            System.out.println(i + " :  userId : 1 :" + fixedWindowRatelimiter.shouldAllowUser(1));
        }

        for(int i = 0; i < 6; i++) {
            System.out.println(i + " :  userId : 2 :" + fixedWindowRatelimiter.shouldAllowUser(2));
        }

        Thread.sleep(60 * 1000);

        for(int i = 0; i < 12; i++) {
            System.out.println(i + " :  userId : 1 :" + fixedWindowRatelimiter.shouldAllowUser(1));
        }

    }
}
