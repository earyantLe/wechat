package com.earyant.common.redis.exception;

public class RedisCacheException extends RuntimeException {

    private static final long serialVersionUID = -1209625392805014995L;

    public RedisCacheException(String msg) {
        super(msg);
    }

    public RedisCacheException(String msg, Throwable t) {
        super(msg, t);
    }

    public RedisCacheException(Throwable cause) {
        super(cause);
    }

}
