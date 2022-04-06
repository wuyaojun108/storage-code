package com.bookstore.dao;

import com.bookstore.beans.Book;

import java.util.List;

public interface BookDao {
    /**
     * 添加图书
     *
     * @param book 图书
     * @return 返回在sql语句在数据库中影响的行数
     */
    int addBook(Book book);

    /**
     * 根据ID查找图书
     *
     * @param id 图书ID
     * @return 返回Book对象，如果没有找到，返回null
     */
    Book queryBookById(int id);

    /**
     * 返回所有图书，并把Book实例封装到一个list集合中
     *
     * @return 存储Book对象的list集合
     */
    List<Book> queryBooks();

    /**
     * 修改图书的信息
     *
     * @param book 图书对象
     * @return 返回在sql语句在数据库中影响的行数
     */
    int updateBook(Book book);

    /**
     * 根据id删除图书
     *
     * @param id 图书的ID
     * @return 返回在sql语句在数据库中影响的行数
     */
    int deleteBook(int id);

    /**
     * 查询总记录数
     *
     * @return 返回总记录数
     */
    int queryForPageTotalCount();

    /**
     * 查询某一页的图书信息
     *
     * @param begin    limit子句中的偏移量
     * @param pageSize limit子句中的数据条数
     * @return 把某一页所展示的图书信息放到list集合中并返回
     */
    List<Book> queryForPageItems(int begin, int pageSize);

    /**
     * 查询价格区间内的总记录数
     *
     * @param minPrice 最低价
     * @param maxPrice 最高价
     * @return 价格区间内的总记录数
     */
    int queryForPageByPriceTotalCount(double minPrice, double maxPrice);

    /**
     * 返回某个价格区间内的某一页图书信息
     *
     * @param minPrice 最低价
     * @param maxPrice 最高价
     * @param begin    偏移量
     * @param pageSize 页面大小
     * @return 图书信息，被封装到一个list集合中
     */
    List<Book> queryForPageByPriceItems(double minPrice, double maxPrice, int begin, int pageSize);

}
