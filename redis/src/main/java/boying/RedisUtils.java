package boying;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.lang.reflect.Type;

/**
 * Created by boying on 2017/9/27.
 */
public class RedisUtils {
    private RedisTemplate redisTemplate;

    public RedisUtils() {
    }

    public RedisUtils(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void set(String key, Object value, Long milliseconds) {
        // TODO
        final String json;
        if(value == null){
            // TODO
            json = "";
        }else{
            if(value instanceof String){
                json = (String)value;
            }else {
                json = GsonUtils.getGson().toJson(value);
            }
        }
        set(key, value, milliseconds);
    }

    public void set(String key, byte[] bytes, Long milliseconds){
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                if(milliseconds == null){
                    connection.set(key.getBytes(), bytes);
                }else {
                    connection.set(key.getBytes(), bytes, Expiration.milliseconds(milliseconds), null);
                }
                return null;
            }
        });
    }

    public void set(String key, Object value) {
        set(key, value, null);
    }

    public <T> T get(String key, Class<T> clazz) {
        byte[] bytes = getBytes(key);
        if(bytes == null){
            return null;
        }

        if(bytes.length == 0){
            // TODO
            return null;
        }

        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        String json = stringSerializer.deserialize(bytes);

        if (json == null) {
            return null;
        }

        if(clazz == String.class){
            return (T)json;
        }
        return GsonUtils.getGson().fromJson(json, clazz);
    }

    /**
     * Gson gen type, get("key", new TypeToken<>(){}.getType())
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public <T> T get(String key, Type type) {
        byte[] bytes = getBytes(key);
        if(bytes == null){
            return null;
        }

        if(bytes.length == 0){
            // TODO
            return null;
        }

        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        String json = stringSerializer.deserialize(bytes);

        if (json == null) {
            return null;
        }

        return GsonUtils.getGson().fromJson(json, type);
    }

    public byte[] getBytes(String key){
        return (byte[])redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
    }

    public String getString(String key){
        return get(key, String.class);
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
