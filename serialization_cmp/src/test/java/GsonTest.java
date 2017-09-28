import bean.Result;
import com.google.gson.Gson;
import org.junit.Test;

/**
 * Created by boying on 2017/9/28.
 */
public class GsonTest extends BaseTest{
    Gson gson = new Gson();
    @Test
    public void test(){
        String json = gson.toJson(standardResult);
        System.out.println(json);

        Result result = gson.fromJson(json, Result.class);
        cmp(result);
    }

}
