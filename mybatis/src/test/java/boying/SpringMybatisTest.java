package boying;

import boying.dao.BookDao;
import boying.domain.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by boying on 2017/11/26.
 */
public class SpringMybatisTest {
    private BookDao bookDao;
    private ApplicationContext applicationContext;
    private PlatformTransactionManager transactionManager;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        bookDao = applicationContext.getBean(BookDao.class);
        transactionManager = applicationContext.getBean("txManager", PlatformTransactionManager.class);
    }

    @Test
    public void selectTest() {
        Book bookById = bookDao.getBookById(37);
        System.out.println(bookById);
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
        bookDao.addBook(book);

        transactionManager.commit(transaction);

        Book bookById = bookDao.getBookById(book.getId());
        Assert.assertNotNull(bookById);
    }
}
