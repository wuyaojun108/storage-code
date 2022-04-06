package org.wyj;

import com.bookstore.beans.Book;
import com.bookstore.beans.Page;
import com.bookstore.service.BookService;
import com.bookstore.service.impl.BookServiceImpl;
import org.junit.Test;


public class BookServiceTest {

    @Test
    public void pageTest(){
        BookService bookService = new BookServiceImpl();
        Page<Book> page = bookService.page(1, 10);
        System.out.println(page);
    }

    @Test
    public void pageByPriceTest(){
        BookService bookService = new BookServiceImpl();
        Page<Book> page = bookService.pageByPrice(10, 300, 1, 4);
        System.out.println(page);
    }
}
