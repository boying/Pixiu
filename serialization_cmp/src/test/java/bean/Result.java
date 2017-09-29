package bean;

/**
 * Created by boying on 2017/9/28.
 */
public class Result<T> {
    private String retCode;
    private String msg;
    private StatusEnum statusEnum;
    private T data;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Result<?> result = (Result<?>) o;

        if (retCode != null ? !retCode.equals(result.retCode) : result.retCode != null) return false;
        if (msg != null ? !msg.equals(result.msg) : result.msg != null) return false;
        if (statusEnum != result.statusEnum) return false;
        return data != null ? data.equals(result.data) : result.data == null;
    }

    @Override
    public int hashCode() {
        int result = retCode != null ? retCode.hashCode() : 0;
        result = 31 * result + (msg != null ? msg.hashCode() : 0);
        result = 31 * result + (statusEnum != null ? statusEnum.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    public StatusEnum getStatusEnum() {
        return statusEnum;
    }

    public void setStatusEnum(StatusEnum statusEnum) {
        this.statusEnum = statusEnum;
    }

}
