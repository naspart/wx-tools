package com.rolbel.ma.lock;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RedisLock implements Lock, java.io.Serializable {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    private static final Long RELEASE_SUCCESS = 1L;

    private final Pool<Jedis> jedisPool;
    private final String key;

    public RedisLock(Pool<Jedis> jedisPool, String key) {
        this.jedisPool = jedisPool;
        this.key = key;
    }

    @Override
    public void lock() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            String result = jedis.set("wx_ma_distribute_lock:" + this.key, this.key, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 30);
            if (!LOCK_SUCCESS.equals(result)) {
                this.lock();
            }
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            Object result = jedis.eval(script, Collections.singletonList("wx_ma_distribute_lock:" + this.key), Collections.singletonList(this.key));

            if (RELEASE_SUCCESS.equals(result)) {
                return;
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
