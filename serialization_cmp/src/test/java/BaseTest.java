import bean.Result;
import bean.StatusEnum;
import bean.V;
import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import bean.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public void init() {
        standardResult.setRetCode(code);
        standardResult.setMsg(msg);
        standardResult.setData(standardData);
        standardResult.setStatusEnum(StatusEnum.FAILED);

        standardData.setLongValue(888L);
        standardData.setInteger(2);
        standardData.setBigDecimal(BigDecimal.valueOf(3.1415926));
        standardData.setDoubleValue(3.1415926);
        standardData.setLocalDateTime(LocalDateTime.now());
        standardData.setDate(new Date());
        standardData.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
        standardData.setString("contest");
        standardData.setIntegerList(Arrays.asList(1, 2, null, 5));
        standardData.setStringList(Arrays.asList("abc", null, "def"));
        standardData.setMap(ImmutableMap.of("k1", "v1", "k2", "v2"));
        standardData.setVs(Arrays.asList(new V(1L), new V(), null, new V(2L)));
        standardData.setIntegers(new Integer[]{1, 2, 3, 4});
        standardData.setArrays(new V[]{new V(1L), new V(2L), null, new V(3L)});
    }


    protected void cmpResult(Result<Data> result) {
        assertEquals(standardResult.getRetCode(), result.getRetCode());
        assertEquals(standardResult.getMsg(), result.getMsg());
        assertEquals(standardResult.getStatusEnum(), result.getStatusEnum());

        Data data = result.getData();
        cmpData(data);
    }

    protected void cmpData(Data data) {
        assertEquals(standardData.getLongValue(), data.getLongValue());
        assertEquals(standardData.getInteger(), data.getInteger());
        assertEquals(standardData.getBigDecimal(), data.getBigDecimal());
        assertEquals(standardData.getDoubleValue(), data.getDoubleValue());
        assertEquals(standardData.getLocalDateTime(), data.getLocalDateTime());

        assertEquals(standardData.getDate(), data.getDate());
        assertEquals(standardData.getTimestamp(), data.getTimestamp());
        assertEquals(standardData.getString(), data.getString());
        assertEquals(standardData.getIntegerList(), data.getIntegerList());
        assertEquals(standardData.getStringList(), data.getStringList());
        assertEquals(standardData.getMap(), data.getMap());
        assertEquals(new ArrayList(standardData.getVs()), new ArrayList(data.getVs()));
        assertEquals(standardData.getIntegers(), data.getIntegers());
        assertEquals(standardData.getArrays(), data.getArrays());
    }

}
