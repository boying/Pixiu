package executor;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JavaExecutor<T> extends Executor<T> {
    private ObjectMapper mapper;

    public JavaExecutor(Class<T> clazz, T obj) {
        super(clazz, obj);
        mapper = new ObjectMapper();
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
