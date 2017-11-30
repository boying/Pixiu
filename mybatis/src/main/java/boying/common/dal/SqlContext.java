package boying.common.dal;

/**
 * Created by boying on 17-11-30.
 */
public class SqlContext {
    private ThreadLocal<String> sqlId = new ThreadLocal<>();

    private static SqlContext instance = new SqlContext();

    private SqlContext(){}

    public static SqlContext getInstance(){
        return instance;
    }

    public void setSqlId(String sqlId){
        this.sqlId.set(sqlId);
    }

    public String getSqlId(){
        return this.sqlId.get();
    }

    public void remove(){
        sqlId.remove();
    }
}
