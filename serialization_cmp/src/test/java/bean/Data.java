package bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by boying on 2017/9/28.
 */
public class Data {
    private Long id;
    private LocalDateTime localDateTime;
    private Date date;
    private Timestamp timestamp;
    private String content;
    private List<Integer> integerList;
    private List<String> stringList;
    private Map<String, String> map;
    private List<V> vs;
    private Integer[] integers;
    private V[] arrays;
    private Object alwaysNull;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Integer> getIntegerList() {
        return integerList;
    }

    public void setIntegerList(List<Integer> integerList) {
        this.integerList = integerList;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<V> getVs() {
        return vs;
    }

    public void setVs(List<V> vs) {
        this.vs = vs;
    }

    public Integer[] getIntegers() {
        return integers;
    }

    public void setIntegers(Integer[] integers) {
        this.integers = integers;
    }

    public V[] getArrays() {
        return arrays;
    }

    public void setArrays(V[] arrays) {
        this.arrays = arrays;
    }

    public Object getAlwaysNull() {
        return alwaysNull;
    }

    public void setAlwaysNull(Object alwaysNull) {
        this.alwaysNull = alwaysNull;
    }
}
