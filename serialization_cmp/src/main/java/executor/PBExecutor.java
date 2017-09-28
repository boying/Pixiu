package executor;

import bean.PbBean;
import com.google.protobuf.AbstractMessageLite;

public class PBExecutor<T extends AbstractMessageLite> extends Executor<T> {

    public PBExecutor(Class<T> clazz, T obj) {
        super(clazz, obj);
    }

    @Override
    public byte[] serialize() {
        try {
            return obj.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            return (T) PbBean.Person.parseFrom(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String serializeToStr() {
       throw new RuntimeException("not supported");
    }

    @Override
    public T deserializeFromStr(String json) {
        throw new RuntimeException("not supported");
    }
}
