package executor;

import com.google.gson.Gson;

public class GsonExecutor<T> extends Executor<T> {
    private Gson gson = new Gson();

    public GsonExecutor(Class<T> clazz, T obj) {
        super(clazz, obj);
    }

    @Override
    public byte[] serialize() {
        try {
            return gson.toJson(obj).getBytes();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
             return gson.fromJson(new String(bytes), clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serializeToStr() {
        try {
            return gson.toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserializeFromStr(String json) {
        try {
            return gson.fromJson(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
