package boying;

import boying.dao.BookDao;
import boying.domain.Book;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by boying on 2017/11/26.
 */
public class RawMybatisTest {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init() throws IOException {
        InputStream resourceAsStream = Resources.getResourceAsStream("mybatis.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
    }

    @Test
    public void transactionTest(){
        SqlSession sqlSession = sqlSessionFactory.openSession(false);
        BookDao bookDao = sqlSession.getMapper(BookDao.class);
        Book book = new Book();
        book.setIsbn("hahah");
        book.setName("xxxName");
        bookDao.addBook(book);
        sqlSession.rollback();
        Book queriedBook = bookDao.getBookById(book.getId());
        Assert.assertNull(queriedBook);
    }

    @Test
    public void transactionTest1(){
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        BookDao bookDao = sqlSession.getMapper(BookDao.class);
        Book book = new Book();
        book.setIsbn("hahah");
        book.setName("xxxName");
        bookDao.addBook(book);
        Book queriedBook = bookDao.getBookById(book.getId());
        Assert.assertNotNull(queriedBook);
    }
}
