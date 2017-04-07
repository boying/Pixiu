package executor;

import com.alibaba.fastjson.JSON;

public class FastjsonExecutor<T> extends Executor<T> {

    public FastjsonExecutor(Class<T> clazz, T obj) {
        super(clazz, obj);
    }

    @Override
    public byte[] serialize() {
        try {
            return JSON.toJSONBytes(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            return JSON.parseObject(bytes, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
