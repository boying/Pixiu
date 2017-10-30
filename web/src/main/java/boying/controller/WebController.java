package boying.controller;

import boying.domain.Book;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by boying on 2017/10/30.
 */
@Controller
@RequestMapping("/web")
public class WebController {
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(ModelMap model) {
        List<Book> books = new ArrayList<>();
        Book book1 = new Book();
        book1.setIsbn("xxx");
        book1.setName("name1");
        books.add(book1);

        Book book2 = new Book();
        book2.setIsbn("xxx");
        book2.setName("name2");
        books.add(book2);

        model.addAttribute("books", books);
        return "list";
    }
}
