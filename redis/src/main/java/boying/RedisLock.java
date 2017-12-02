package boying;

/**
 * Created by boying on 2017/12/2.
 */

import com.google.common.base.Strings;
import redis.clients.jedis.Jedis;


/**
 * reffer:
 * https://github.com/wyzssw/DistributedLock/blob/master/src/main/java/com/wyzssw/distributedLock/DistributedLock.java
 * http://www.cnblogs.com/PurpleDream/p/5559352.html
 */
public class RedisLock {
    private Jedis jedis;

    public RedisLock(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 得不到锁立即返回，得到锁返回设置的超时时间
     */
    public long tryLock(String key, long lockTimeOut) {
        if (lockTimeOut <= 0) {
            throw new IllegalArgumentException();
        }
        //得到锁后设置的过期时间，未得到锁返回0
        long expireTime;
        expireTime = System.currentTimeMillis() + lockTimeOut + 1;
        if (jedis.setnx(key, String.valueOf(expireTime)) == 1) {
            //得到了锁返回
            return expireTime;
        } else {
            String curLockTimeStr = jedis.get(key);
            //判断是否过期
            if (Strings.isNullOrEmpty(curLockTimeStr)
                    || System.currentTimeMillis() > Long.valueOf(curLockTimeStr)) {
                expireTime = System.currentTimeMillis() + lockTimeOut + 1;

                curLockTimeStr = jedis.getSet(key, String.valueOf(expireTime));
                //仍然过期,则得到锁
                if (Strings.isNullOrEmpty(curLockTimeStr)
                        || System.currentTimeMillis() > Long.valueOf(curLockTimeStr)) {
                    return expireTime;
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        }
    }

    public void unlock(String key) {
        jedis.del(key);
    }

    public Jedis getJedis() {
        return jedis;
    }

    public void setJedis(Jedis jedis) {
        this.jedis = jedis;
    }

    /**
     * 得到锁返回设置的超时时间，得不到锁等待
     */
    /*
    public long lock(String key, long lockTimeOut) throws InterruptedException {
        if(lockTimeOut <= 0){
            throw new IllegalArgumentException();
        }

        long startTime = System.currentTimeMillis();
        long sleep =lockTimeOut / 10;
        if(sleep == 0){
            sleep = lockTimeOut;
        }
        //得到锁后设置的过期时间，未得到锁返回0
        long expireTime;
        for (; ; ) {
            expireTime = System.currentTimeMillis() + lockTimeOut + 1;
            if (jedis.setnx(key, String.valueOf(expireTime)) == 1) {
                //得到了锁返回
                return expireTime;
            } else {
                String curLockTimeStr = jedis.get(key);
                //判断是否过期
                if (Strings.isNullOrEmpty(curLockTimeStr)
                        || System.currentTimeMillis() > Long.valueOf(curLockTimeStr)) {
                    expireTime = System.currentTimeMillis() + lockTimeOut + 1;

                    curLockTimeStr = jedis.getSet(key, String.valueOf(expireTime));
                    //仍然过期,则得到锁
                    if (Strings.isNullOrEmpty(curLockTimeStr)
                            || System.currentTimeMillis() > Long.valueOf(curLockTimeStr)) {
                        return expireTime;
                    } else {
                        Thread.sleep(sleep);
                    }
                } else {
                    Thread.sleep(sleep);
                }
            }
            if (lockTimeOut > 0
                    && ((System.currentTimeMillis() - startTime) >= lockTimeOut)) {
                expireTime = 0;
                return expireTime;
            }
        }

    }
    */

}
