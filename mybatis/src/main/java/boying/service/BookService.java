package boying.service;

import boying.dao.BookDao;
import boying.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by boying on 17-11-30.
 */
@Service
public class BookService {
    @Autowired
    BookDao bookDao;

    @Transactional("txManager")
    public void inTransactional(){
        System.out.println("#####################");
        Book bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
    }

    public void notInTransaction(){
        System.out.println("#####################");
        Book bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
        System.out.println("#####################");
        bookById = bookDao.getBookById(1);
    }
}
