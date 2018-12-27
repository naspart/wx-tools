package com.rolbel.mp.lock;

import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class RedisLock implements Lock, java.io.Serializable {
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private final Pool<Jedis> jedisPool;
    private final String key;

    public RedisLock(Pool<Jedis> jedisPool, String key) {
        this.jedisPool = jedisPool;
        this.key = key;
    }

    @Override
    public void lock() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            String result = jedis.set("wx_mp_distribute_lock:" + this.key, UUID.randomUUID().toString(), SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, 30);
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
            jedis.del("wx_mp_distribute_lock:" + this.key);
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
