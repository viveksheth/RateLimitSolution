package com.ratelimiting.objects;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientId {

    private AtomicInteger reqCount;
    private Long lastCallTimeStamp;
    private int countLimit;

    public ClientId(AtomicInteger reqCount, Long lastCallTimeStamp, int countLimit) {
        this.reqCount = reqCount;
        this.lastCallTimeStamp = lastCallTimeStamp;
        this.countLimit = countLimit;
    }

    public int remainingCount()
    {
        return countLimit - getReqCount().intValue();
    }

    public AtomicInteger getReqCount() {
        return reqCount;
    }

    public void setReqCount(AtomicInteger reqCount) {
        this.reqCount = reqCount;
    }

    public Long getLastCallTimeStamp() {
        return lastCallTimeStamp;
    }

    public void setLastCallTimeStamp(Long lastCallTimeStamp) {
        this.lastCallTimeStamp = lastCallTimeStamp;
    }

}
