package executor;

/**
 * Created by jiangzhiwen on 17/4/7.
 */
public abstract class Executor<T> {
    protected Class<T> clazz;
    protected T obj;

    public Executor(Class<T> clazz, T obj) {
        this.clazz = clazz;
        this.obj = obj;
    }

    public SerializeStatistics serialize(int times) {
        int totalSize = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; ++i) {
            totalSize += serialize().length;
        }
        long cost = System.currentTimeMillis() - startTime;

        return new SerializeStatistics(
                times,
                cost,
                (double) cost / times,
                totalSize / times
        );

    }

    public DeserializeStatistics deserialize(int times) {
        byte[] bytes = serialize();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; ++i) {
            deserialize(bytes);
        }
        long cost = System.currentTimeMillis() - startTime;

        return new DeserializeStatistics(
                times,
                cost,
                (double) cost / times
        );
    }

    public SerializeStatistics serializeToStr(int times) {
        int totalSize = 0;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; ++i) {
            totalSize += serializeToStr().length();
        }
        long cost = System.currentTimeMillis() - startTime;

        return new SerializeStatistics(
                times,
                cost,
                (double) cost / times,
                totalSize / times
        );

    }

    public DeserializeStatistics deserializeFromStr(int times) {
        String json = serializeToStr();

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; ++i) {
            deserializeFromStr(json);
        }
        long cost = System.currentTimeMillis() - startTime;

        return new DeserializeStatistics(
                times,
                cost,
                (double) cost / times
        );
    }


    public abstract byte[] serialize();

    public abstract T deserialize(byte[] bytes);

    public abstract String serializeToStr();

    public abstract T deserializeFromStr(String json);
}
