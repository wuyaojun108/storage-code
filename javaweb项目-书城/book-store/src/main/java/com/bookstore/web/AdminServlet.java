package com.bookstore.web;

import com.bookstore.beans.Book;
import com.bookstore.beans.Page;
import com.bookstore.service.BookService;
import com.bookstore.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 实现管理员模块的servlet
 */
public class AdminServlet extends BaseServlet {

    private BookService bookService = new BookServiceImpl();

    // 分页展示图书信息
    public void bookPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解析请求
        int pageNo = req.getParameter("pageNo") == null ? 1 :
                Integer.parseInt(req.getParameter("pageNo"));
        int pageSize = req.getParameter("pageSize") == null ? Page.PAGE_SIZE :
                Integer.parseInt(req.getParameter("pageSize"));

        // 调用service层的page方法，获取Page对象
        Page<Book> page = bookService.page(pageNo, pageSize);
        page.setUriPart("/book-store/manager?action=bookPage");
        // 保存page对象到request域中
        req.setAttribute("page", page);
        // 请求转发到pages/manager/book_manager.jsp
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }

    // 修改图书信息
    public void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 解析请求
        int id = Integer.parseInt(req.getParameter("id"));
        String name = new String(req.getParameter("name").getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
        String author = new String(req.getParameter("author").getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
        double price = Double.parseDouble(req.getParameter("price"));
        int sales = Integer.parseInt(req.getParameter("sales"));
        int stock = Integer.parseInt(req.getParameter("stock"));
        String defaultPath = "static/img/default.jpg";
        Book book = new Book(id, name, author, price, sales, stock, defaultPath);

        // 调用service层的方法，修改图书信息
        bookService.updateBook(book);

        // 请求重定向到 /javaweb/manager/book_manager?action=page，在前端页面展示图书列表
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        resp.sendRedirect("/book-store/manager?action=bookPage&pageNo=" + pageNo);

    }

    // 获取指定ID的图书信息，把它展示在图书编辑页面中
    public void getBook(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Book book = bookService.queryBookById(id);
        req.setAttribute("book", book);
        req.getRequestDispatcher("/pages/manager/book_edit.jsp?action=update").forward(req, resp);
    }

    // 实现删除图书的功能
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 解析请求并调用service层的方法删除图书
        int id = Integer.parseInt(req.getParameter("id"));
        // 删除服务器上的图片
        Book book = bookService.queryBookById(id);
        System.out.println("book = " + book);
        String imgPath = this.getServletContext().getRealPath(book.getImgPath());
        System.out.println(imgPath);
        File file = new File(imgPath);
        if (file.exists()) {
            file.delete();
        }

        bookService.deleteBook(id);
        // 请求重定向到 /javaweb/manager/book_manager?action=page，在前端页面展示图书列表
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        resp.sendRedirect("/book-store/manager?action=bookPage&pageNo=" + pageNo);
    }


    // 实现添加图书功能
    public void addBook(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {


        // 解析请求
        String name = new String(req.getParameter("name").getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
        String author = new String(req.getParameter("author").getBytes(StandardCharsets.ISO_8859_1),
                StandardCharsets.UTF_8);
        double price = Double.parseDouble(req.getParameter("price"));
        int sales = Integer.parseInt(req.getParameter("sales"));
        int stock = Integer.parseInt(req.getParameter("stock"));
        String defaultPath = "static/img/default.jpg";

        // 调用service方法，在数据库中添加图书
        Book book = new Book(null, name, author, price, sales, stock, defaultPath);
        bookService.addBook(book);

        // 请求重定向到 /book-store/manager?action=bookPage&pageNo=${pageTotal}，
        // 因为添加的图书总是会被展示在最后一页
        int pageTotal = Integer.parseInt(req.getParameter("pageNo"));
        pageTotal++;
        resp.sendRedirect("/book-store/manager?action=bookPage&pageNo=" + pageTotal);

    }

    // 获取图书列表
    public void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = bookService.queryBooks();
        req.setAttribute("books", books);
        req.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(req, resp);
    }


}
