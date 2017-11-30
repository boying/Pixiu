package boying;

import boying.dao.BookDao;
import boying.domain.Book;
import boying.service.BookService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by boying on 2017/11/26.
 */

/**
 * TODO
 * 暂时结论：
 * 在使用spring-mybatis时，在同一个事务中，mybtis缓存才生效，不在一个事务中，mybatis缓存不生效
 * 跟踪代码上看，不在事务时，dao的每次方法调用，都会调用org.mybatis.spring.SqlSessionUtils#getSqlSession(org.apache.ibatis.session.SqlSessionFactory, org.apache.ibatis.session.ExecutorType, org.springframework.dao.support.PersistenceExceptionTranslator)
 * 创建新的sqlSession，创建新的SimpleExecutor，因而缓存不生效
 */
public class MonitorPluginInSpringTest {
    private BookDao bookDao;
    private ApplicationContext applicationContext;
    private PlatformTransactionManager transactionManager;
    private BookService bookService;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        bookDao = applicationContext.getBean(BookDao.class);
        transactionManager = applicationContext.getBean("txManager", PlatformTransactionManager.class);
        bookService = applicationContext.getBean(BookService.class);

        initData();
    }

    private void initData(){
        // 数据库插入一条id为1大的数据
    }

    @Test
    public void insertTest(){
        Book book = new Book();
        book.setName("a real book");
        book.setIsbn("a isbn");
        book.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        book.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

        bookDao.addBook(book);
    }

    @Test
    public void selectTest() {
        Book bookById = bookDao.getBookById(1);
        System.out.println(bookById);
    }

    /**
     * cache并不生效，因为不在一个事务里
     */
    @Test
    public void selectWithCacheTest() {
        System.out.println("#####################");
        Book bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
    }

    @Test
    public void deleteTest(){
        Book book = new Book();
        book.setName("a real book");
        book.setIsbn("a isbn");
        book.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        book.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

        bookDao.addBook(book);

        bookDao.realDeleteBook(book.getId());
    }

    @Test
    public void updateTest(){
        Book book = new Book();
        book.setName("a real book");
        book.setIsbn("a isbn");
        book.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        book.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));

        bookDao.addBook(book);

        bookDao.updateNameIsbn(book.getId(), "newName", "newISBN");
    }

    /**
     * 事务中多个相同的查询，cache生效
     */
    @Test
    public void serviceMethodInTransactional(){
        bookService.inTransactional();
    }

    /**
     *
     */
    @Test
    public void serviceMethodNotInTransactional(){
        bookService.notInTransaction();
    }

    /**
     * 缓存生效
     */
    @Test
    public void selectInTransactionWithCacheTest() {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);


        System.out.println("#####################");
        Book bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);

        transactionManager.commit(transaction);
    }



    @Test
    public void transactionTest() {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);

        Book book = new Book();
        book.setName("AName");
        book.setIsbn("Aisbn");
        bookDao.addBook(book);

        transactionManager.rollback(transaction);

        Book bookById = bookDao.getBookById(book.getId());
        Assert.assertNull(bookById);
    }

    @Test
    public void transaction1Test() {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        TransactionStatus transaction = transactionManager.getTransaction(transactionDefinition);

        Book book = new Book();
        book.setName("AName");
        book.setIsbn("Aisbn");
        book.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        book.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        bookDao.addBook(book);

        transactionManager.commit(transaction);

        Book bookById = bookDao.getBookById(book.getId());
        Assert.assertNotNull(bookById);
    }

    @Test
    public void batchTest(){
        List<Book> books = new ArrayList<>();
        for(int i = 0; i < 10; ++i) {
            Book book = new Book();
            book.setName("AName"+i);
            book.setIsbn("Aisbn"+i);
            book.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            book.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
            books.add(book);
        }
        bookDao.addBooks(books);
    }
}
