package common;

import bean.V;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by boying on 2017/9/29.
 */
public class ErrorGsonUtilsTest {
    private Gson gson = ErrorGsonUtils.getGson();

    List<V> vs = Arrays.asList(new V(1L), new V(2L), null, new V());
    String vsJson = gson.toJson(vs);

    @Test
    public void parseList() throws Exception {
        List<String> strList = Arrays.asList("a", "b", null, "c");
        String strListJson = gson.toJson(strList);
        List<Integer> integers = Arrays.asList(1, 2, null, 3);
        String integersJson = gson.toJson(integers);
        List<V> vs = Arrays.asList(new V(1L), new V(2L), null, new V());
        String vsJson = gson.toJson(vs);

        System.out.println(strListJson);
        List<String> objects = ErrorGsonUtils.parseList(strListJson);
        assertEquals(strList, objects);

        List<String> strings = ErrorGsonUtils.parseList(strListJson, String.class);
        assertEquals(strList, strings);

        System.out.println(vsJson);
        List<Object> objects1 = ErrorGsonUtils.parseList(vsJson);
        assertEquals(vs, objects);
        List<V> list = ErrorGsonUtils.parseList(vsJson);
        assertEquals(vs, list);

        List<V> listx = gson.fromJson(vsJson, new TypeToken<List<V>>() {
        }.getType());
        assertEquals(vs, listx);

        List<V> vs1 = ErrorGsonUtils.parseList(vsJson, V.class);
        assertEquals(vs, vs1);

        System.out.println("");
    }

    @Test
    public void test(){
        List<V> vs = ErrorGsonUtils.parseList(vsJson, new TypeToken<List<V>>() {
        });
        assertEquals(vs, this.vs);
    }

    @Test
    public void parseMap() throws Exception {
    }

}