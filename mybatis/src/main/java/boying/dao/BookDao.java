package boying.dao;

import boying.domain.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by boying on 2017/11/26.
 */
public interface BookDao {
    Book getBookById(@Param("id") long id);

    void addBook(Book book);

    void deleteBook(@Param("id") long id);

    void updateBook(Book book);

    List<Book> fuzzyQueryBooksByName(@Param("name") String name);
}
