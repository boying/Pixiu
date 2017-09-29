package bean;

/**
 * Created by boying on 2017/9/29.
 */
public enum StatusEnum {
    SUCCESS(0, "成功"),
    FAILED(1, "失败");
    private int value;
    private String desc;

    StatusEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "StatusEnum{" +
                "value=" + value +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }
}
