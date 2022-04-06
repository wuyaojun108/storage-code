package com.bookstore.service;

import com.bookstore.beans.Book;
import com.bookstore.beans.Page;

import java.util.List;

/**
 * 在service层，用于处理关于图书的请求
 */
public interface BookService {

    int addBook(Book book);

    Book queryBookById(int id);

    List<Book> queryBooks();

    int updateBook(Book book);

    int deleteBook(int id);

    // 分页功能
    Page<Book> page(int pageNo, int pageSize);

    // 根据价格来进行分页
    Page<Book> pageByPrice(double minPrice, double maxPrice, int pageNo, int pageSize);

}
