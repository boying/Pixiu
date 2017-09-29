import bean.Data;
import bean.Result;
import bean.V;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import common.GsonUtils;
import org.junit.Test;

import static org.junit.Assert.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by boying on 2017/9/28.
 */
public class GsonTest extends BaseTest {
    Gson gson = GsonUtils.getGson();

    @Test
    public void test() {
        String json = gson.toJson(standardResult);
        System.out.println(json);

        Type type = new TypeToken<Result<Data>>() {
        }.getType();
        Result result = gson.fromJson(json, type);
        cmpResult(result);
    }

    @Test
    public void test2() {
        String json = gson.toJson(standardData);
        System.out.println(json);

        Data result = gson.fromJson(json, Data.class);
        cmpData(result);
    }

    /**
     * List test
     */
    @Test
    public void test3() {
        Result<List<V>> result = new Result<>();
        List<V> list = new ArrayList<>();
        result.setData(list);
        list.add(new V(1L));
        list.add(new V(2L));
        list.add(new V());

        String json = gson.toJson(result);

        Result<List<V>> r = gson.fromJson(json, new TypeToken<Result<List<V>>>() {
        }.getType());

        assertEquals(result.getData(), r.getData());
    }

    /**
     * Map test
     * 泛型 test
     */
    @Test
    public void test4() {
        Result<List<V>> result = new Result<>();
        List<V> list = new ArrayList<>();
        result.setData(list);
        list.add(new V(1L));
        list.add(new V(2L));
        list.add(new V());

        Map<String, Result<List<V>>> map = ImmutableMap.of("key", result);
        String json = gson.toJson(map);
        System.out.println(json);

        Map<String, Result<List<V>>> r = gson.fromJson(json, new TypeToken<Map<String, Result<List<V>>>>() {
        }.getType());

        assertEquals(map, r);
    }

}
