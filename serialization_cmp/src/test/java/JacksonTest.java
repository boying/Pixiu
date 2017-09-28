import bean.Data;
import bean.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by boying on 2017/9/28.
 */
public class JacksonTest extends BaseTest {
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws IOException {
        String json = mapper.writeValueAsString(standardResult);

        System.out.println(json);

        Result result = mapper.readValue(json, Result.class);
        cmpResult(result);
    }

    @Test
    public void test2() throws IOException {
        String json = mapper.writeValueAsString(standardData);

        System.out.println(json);

        Data data = mapper.readValue(json, Data.class);
        cmpData(data);
    }


}

