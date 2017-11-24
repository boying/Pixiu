import org.junit.Test;

import java.sql.*;

/**
 * Created by boying on 17-11-24.
 */
public class JdbcTest {
    @Test
    public void createTableTest() throws ClassNotFoundException, SQLException {
        String sql;
        String url = "jdbc:mysql://localhost:3306/jdbc_test?"
                + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";

        Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
        // 一个Connection代表一个数据库连接
        Connection conn = DriverManager.getConnection(url);
        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        sql = "create table student(NO char(20),name varchar(20),primary key(NO)) ENGINE=InnoDB";
        stmt.execute(sql);// executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
        conn.rollback();
        conn.close();
    }

    /**
     * 有时候事务不生效，关注下表的engine是否支持事务！！！
     */
    @Test
    public void transactionTest() throws ClassNotFoundException, SQLException {
        String sql;
        String url = "jdbc:mysql://localhost:3306/jdbc_test?"
                + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";

        Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
        // 一个Connection代表一个数据库连接
        Connection conn = DriverManager.getConnection(url);
        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
        conn.setAutoCommit(false);
        Statement stmt = conn.createStatement();
        sql = "insert into student(NO,name) values('2012001','name1')";
        //int result = stmt.executeUpdate(sql);
        stmt.execute(sql);
        sql = "insert into student(NO,name) values('2012002','name2')";
        //result = stmt.executeUpdate(sql);
        stmt.execute(sql);
        sql = "select * from student";
        ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
        System.out.println("学号\t姓名");
        while (rs.next()) {
            System.out
                    .println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
        }
        //conn.rollback();
        conn.commit();
        stmt.close();
        conn.close();
    }

    @Test
    public void noTransactionTest() throws ClassNotFoundException, SQLException {
        String sql;
        String url = "jdbc:mysql://localhost:3306/jdbc_test?"
                + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";

        Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
        // 一个Connection代表一个数据库连接
        Connection conn = DriverManager.getConnection(url);
        // Statement里面带有很多方法，比如executeUpdate可以实现插入，更新和删除等
        conn.setAutoCommit(true);
        Statement stmt = conn.createStatement();
        sql = "insert into student(NO,name) values('2012001','name1')";
        //int result = stmt.executeUpdate(sql);
        stmt.execute(sql);
        sql = "insert into student(NO,name) values('2012002','name2')";
        //result = stmt.executeUpdate(sql);
        stmt.execute(sql);
        sql = "select * from student";
        ResultSet rs = stmt.executeQuery(sql);// executeQuery会返回结果的集合，否则返回空值
        System.out.println("学号\t姓名");
        while (rs.next()) {
            System.out
                    .println(rs.getString(1) + "\t" + rs.getString(2));// 入如果返回的是int类型可以用getInt()
        }
        //conn.rollback();
        conn.commit();
        stmt.close();
        conn.close();
    }

    @Test
    public void prepareStatementTest() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbc_test?"
                + "user=root&password=123456&useUnicode=true&characterEncoding=UTF8";

        Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
        // 一个Connection代表一个数据库连接
        Connection conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);

        String insertTableSQL = "INSERT INTO student"
                + "(no, name) VALUES"
                + "(?,?)";

        PreparedStatement preparedStatementInsert = conn.prepareStatement(insertTableSQL);
        preparedStatementInsert.setInt(1, 999);
        preparedStatementInsert.setString(2, "mkyong101");
        preparedStatementInsert.executeUpdate(); //data IS NOT commit yet

        preparedStatementInsert = conn.prepareStatement(insertTableSQL);
        preparedStatementInsert.setInt(1, 2);
        preparedStatementInsert.setString(2, "haha");
        preparedStatementInsert.executeUpdate(); //data IS NOT commit yet

        conn.commit();
        preparedStatementInsert.close();
        conn.close();
    }

    /**
     * 用于存储过程
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void callableStatementTest() throws ClassNotFoundException, SQLException {
    }
}
