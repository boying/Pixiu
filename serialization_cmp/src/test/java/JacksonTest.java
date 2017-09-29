import bean.Data;
import bean.Result;
import bean.V;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import common.JacksonUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by boying on 2017/9/28.
 */
public class JacksonTest extends BaseTest {
    private ObjectMapper mapper = JacksonUtils.getMapper();

    @Test
    public void test() throws IOException {
        String json = mapper.writeValueAsString(standardResult);

        System.out.println(json);

        Result<Data> result = mapper.readValue(json, new TypeReference<Result<Data>>() {
        });
        cmpResult(result);
    }

    @Test
    public void test2() throws IOException {
        String json = mapper.writeValueAsString(standardData);

        System.out.println(json);

        Data data = mapper.readValue(json, Data.class);
        cmpData(data);
    }

    /**
     * List test
     */
    @Test
    public void test3() throws IOException {
        Result<List<V>> result = new Result<>();
        List<V> list = new ArrayList<>();
        result.setData(list);
        list.add(new V(1L));
        list.add(new V(2L));
        list.add(new V());

        String json = mapper.writeValueAsString(result);
        System.out.println(json);

        Result<List<V>> r = mapper.readValue(json, new TypeReference<Result<List<V>>>() {
        });

        assertEquals(result.getData(), r.getData());
    }

    /**
     * Map test
     * 泛型 test
     */
    @Test
    public void test4() throws IOException {
        Result<List<V>> result = new Result<>();
        List<V> list = new ArrayList<>();
        result.setData(list);
        list.add(new V(1L));
        list.add(new V(2L));
        list.add(new V());

        Map<String, Result<List<V>>> map = ImmutableMap.of("key", result);
        String json = mapper.writeValueAsString(map);
        System.out.println(json);

        Map<String, Result<List<V>>> r = mapper.readValue(json, new TypeReference<Map<String, Result<List<V>>>>() {
        });

        assertEquals(map, r);
    }


}

