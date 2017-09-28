import bean.Data;
import bean.Result;
import com.google.gson.Gson;
import common.GsonUtils;
import org.junit.Test;

/**
 * Created by boying on 2017/9/28.
 */
public class GsonTest extends BaseTest{
    Gson gson = GsonUtils.getGson();
    @Test
    public void test(){
        String json = gson.toJson(standardResult);
        System.out.println(json);

        Result result = gson.fromJson(json, Result.class);
        cmpResult(result);
    }

    @Test
    public void test2(){
        String json = gson.toJson(standardData);
        System.out.println(json);

        Data result = gson.fromJson(json, Data.class);
        cmpData(result);
    }

}
