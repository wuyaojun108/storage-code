package com.bookstore.service.impl;

import com.bookstore.beans.Book;
import com.bookstore.beans.Page;
import com.bookstore.dao.BookDao;
import com.bookstore.dao.impl.BookDaoImpl;
import com.bookstore.service.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    BookDao bookDao = new BookDaoImpl();

    @Override
    public int addBook(Book book) {
        return bookDao.addBook(book);
    }

    @Override
    public Book queryBookById(int id) {
        return bookDao.queryBookById(id);
    }

    @Override
    public List<Book> queryBooks() {
        return bookDao.queryBooks();
    }

    @Override
    public int updateBook(Book book) {
        return bookDao.updateBook(book);
    }

    @Override
    public int deleteBook(int id) {
        return bookDao.deleteBook(id);
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) {

        // 获取总记录数
        int pageTotalCount = bookDao.queryForPageTotalCount();

        // 获取总页码
        int pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal += 1;
        }

        // 对页码的边界值进行处理，如果页码小于1，显示第一页，如果页码大于最大页码，显示最大页码的内容
        if (pageNo < 1) {
            pageNo = 1;
        } else if (pageNo > pageTotal) {
            pageNo = pageTotal;
        }

        // 获取当前页面起始位置在数据库中的偏移量
        int begin = (pageNo - 1) * pageSize;

        // 获取页面中的数据内容
        List<Book> items = bookDao.queryForPageItems(begin, pageSize);

        return new Page<>(pageNo, pageTotal, pageSize, pageTotalCount, items);

    }

    @Override
    public Page<Book> pageByPrice(double minPrice, double maxPrice, int pageNo, int pageSize) {
        // 获取总记录数
        int pageTotalCount = bookDao.queryForPageByPriceTotalCount(minPrice, maxPrice);
        // 获取总页码
        int pageTotal = pageTotalCount / pageSize;
        if (pageTotalCount % pageSize > 0) {
            pageTotal += 1;
        }

        // 对页码的边界值进行处理，如果页码小于1，显示第一页，如果页码大于最大页码，显示最大页码的内容
        if (pageNo < 1) {
            pageNo = 1;
        } else if (pageNo > pageTotal) {
            pageNo = pageTotal;
        }
        // 获取当前页面起始位置在数据库中的偏移量
        int begin = (pageNo - 1) * pageSize;

        // 获取页面中的数据内容
        List<Book> items = bookDao.queryForPageByPriceItems(minPrice, maxPrice, begin, pageSize);

        return new Page<>(pageNo, pageTotal, pageSize, pageTotalCount, items);
    }


}
