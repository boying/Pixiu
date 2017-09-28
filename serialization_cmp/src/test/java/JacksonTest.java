import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import bean.Result;

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
        cmp(result);
    }

}

