import bean.Result;
import bean.V;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import bean.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by boying on 2017/9/28.
 */
public class BaseTest {
    protected Data standardData = new Data();
    protected Result<Data> standardResult = new Result<>();
    protected final String code = "0";
    protected final String msg = "msg";

    @Before
    public void init(){
        standardResult.setRetCode(code);
        standardResult.setMsg(msg);
        standardResult.setData(standardData);

        standardData.setId(888L);
        standardData.setTime(LocalDateTime.now());
        standardData.setDate(new Date());
        standardData.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        standardData.setContent("contest");
        standardData.setIntegerList(Arrays.asList(1, 2, null, 5));
        standardData.setStringList(Arrays.asList("abc", null, "def"));
        standardData.setMap(ImmutableMap.of("k1", "v1", "k2", "v2"));
        standardData.setVs(Arrays.asList(new V(), new V(), null, new V()));
    }

    protected void cmp(Result<Data> result){
        assertEquals(standardResult.getRetCode(), result.getRetCode());
        assertEquals(standardResult.getMsg(), result.getMsg());

        Data data = result.getData();
        assertEquals(standardData.getId(), data.getId());
        assertEquals(standardData.getTime(), data.getTime());
        assertEquals(standardData.getDate(), data.getDate());
        assertEquals(standardData.getTimestamp(), data.getTimestamp());
        assertEquals(standardData.getContent(), data.getContent());
        assertEquals(standardData.getIntegerList(), data.getIntegerList());
        assertEquals(standardData.getStringList(), data.getStringList());
        assertEquals(standardData.getMap(), data.getMap());
    }
}
