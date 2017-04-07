package executor;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FastjsonExecutor<T> extends Executor<T> {
    private ObjectMapper mapper;

    public FastjsonExecutor(Class<T> clazz, T obj) {
        super(clazz, obj);
        mapper = new ObjectMapper();
    }

    @Override
    public byte[] serialize() {
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            return (T) mapper.readValue(bytes, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
