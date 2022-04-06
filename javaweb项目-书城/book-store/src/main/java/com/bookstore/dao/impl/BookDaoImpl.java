package com.bookstore.dao.impl;

import com.bookstore.beans.Book;
import com.bookstore.dao.BookDao;

import java.util.List;

public class BookDaoImpl extends BaseDao implements BookDao {
    @Override
    public int addBook(Book book) {
        String sql = "insert into t_book values(null, ?, ?, ?, ?, ?, ?)";
        return update(sql, book.getName(), book.getAuthor(), book.getPrice(), book.getSales()
                , book.getStock(), book.getImgPath());
    }

    @Override
    public Book queryBookById(int id) {
        String sql = "SELECT id, name, author, price, sales, stock, img_path as imgPath " +
                "FROM t_book where id = ?";
        return queryForOne(Book.class, sql, id);
    }

    @Override
    public List<Book> queryBooks() {
        String sql = "SELECT id, name, author, price, sales, stock, img_path as imgPath " +
                " FROM t_book";
        return queryForList(Book.class, sql);
    }

    @Override
    public int updateBook(Book book) {
        String sql = "UPDATE t_book "
                + " SET name = ?, author = ?, price = ?, sales = ?, stock = ?, img_path = ? "
                + " WHERE id = ?";
        return update(sql, book.getName(), book.getAuthor(), book.getPrice(), book.getSales()
                , book.getStock(), book.getImgPath(), book.getId());
    }

    @Override
    public int deleteBook(int id) {
        String sql = "DELETE from t_book WHERE id = ?";
        return update(sql, id);
    }

    @Override
    public int queryForPageTotalCount() {
        String sql = "SELECT count(1) FROM t_book";
        Number number = (Number) queryForSingleValue(sql);
        return number.intValue();
    }

    @Override
    public List<Book> queryForPageItems(int begin, int pageSize) {
        String sql = "SELECT id, name, author, price, sales, stock, img_path as imgPath" +
                "  FROM t_book limit ?, ?";
        return queryForList(Book.class, sql, begin, pageSize);
    }

    @Override
    public int queryForPageByPriceTotalCount(double minPrice, double maxPrice) {
        String sql = "select count(1) from t_book where price between ? and ?";
        Number number = (Number) queryForSingleValue(sql, minPrice, maxPrice);
        return number.intValue();
    }

    @Override
    public List<Book> queryForPageByPriceItems(double minPrice, double maxPrice, int begin, int pageSize) {
        String sql = "SELECT id, name, author, price, sales, stock, img_path as imgPath" +
                " FROM t_book where price between ? and ? order by price limit ?, ?";
        return queryForList(Book.class, sql, minPrice, maxPrice, begin, pageSize);
    }


}
