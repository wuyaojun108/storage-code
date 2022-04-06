package org.wyj;

import com.bookstore.dao.BookDao;
import com.bookstore.dao.impl.BookDaoImpl;
import com.bookstore.beans.Book;
import com.bookstore.utils.JDBCUtils;
import org.junit.Test;

import java.util.List;

public class BookDaoTest {
    private BookDao bookDao = new BookDaoImpl();
    /**
     * 测试添加图书的方法
     */
    @Test
    public void addBookTest(){
        int effectedRow = bookDao.addBook(new Book(null, "aaa", "加西亚·马尔克斯"
                , 100.23, 985, 1098, ""));
        System.out.println("effectedRow = " + effectedRow);
        JDBCUtils.commitAndClose();
    }

    @Test
    public void queryBookByIdTest(){
        Book book = bookDao.queryBookById(100);
        System.out.println(book);
        JDBCUtils.commitAndClose();
    }

    @Test
    public void queryBooksTest(){
        BookDao bookDao = new BookDaoImpl();
        List<Book> books = bookDao.queryBooks();
        for (Book book : books) {
            System.out.println(book);
        }
        JDBCUtils.commitAndClose();
    }

    @Test
    public void updateBookTest(){
        BookDao bookDao = new BookDaoImpl();
        int effectedRow = bookDao.updateBook(new Book(100, "百年孤独", "加西亚·马尔克斯"
                , 100.56, 1098, 999, ""));

        if(effectedRow == 1){
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }
        JDBCUtils.commitAndClose();
    }

    @Test
    public void deleteBookTest(){
        BookDao bookDao = new BookDaoImpl();
        int effectedRow = bookDao.deleteBook(100);
        if(effectedRow == 1){
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
        JDBCUtils.commitAndClose();
    }

    @Test
    public void queryForPageTotalCountTest(){
        BookDao bookDao = new BookDaoImpl();
        System.out.println("bookDao.queryForPageTotalCount() = " + bookDao.queryForPageTotalCount());
        JDBCUtils.commitAndClose();
    }

    @Test
    public void queryForPageItemsTest(){
        BookDao bookDao = new BookDaoImpl();
        List<Book> items = bookDao.queryForPageItems(0, 1000);
        for (Book item : items) {
            System.out.println(item);
        }
        JDBCUtils.commitAndClose();

    }

    @Test
    public void queryForPageByPriceTotalCountTest(){
        BookDao bookDao = new BookDaoImpl();
        int i = bookDao.queryForPageByPriceTotalCount(100, 300);
        System.out.println(i);
        JDBCUtils.commitAndClose();
    }

    @Test
    public void queryForPageByPriceTest(){
        BookDao bookDao = new BookDaoImpl();
        List<Book> items = bookDao.queryForPageByPriceItems(100, 300, 0, 4);
        System.out.println(items);
        JDBCUtils.commitAndClose();
    }
}
