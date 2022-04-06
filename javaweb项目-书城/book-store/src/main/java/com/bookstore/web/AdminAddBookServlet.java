package com.bookstore.web;

import com.bookstore.beans.Book;
import com.bookstore.service.BookService;
import com.bookstore.service.impl.BookServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AdminAddBookServlet extends HttpServlet {

    private BookService bookService = new BookServiceImpl();
    private Logger logger = Logger.getLogger(AdminAddBookServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        // 创建工厂类
        DiskFileItemFactory factory = new DiskFileItemFactory();
        int pageNo = 0;

        // 创建解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            // 解析请求，将请求体中的每个键值对都存储到list集合中
            List<FileItem> fileItems = upload.parseRequest(req);
            // 第1个位置是name
            FileItem nameItem = fileItems.get(0);
            String name = nameItem.getString(StandardCharsets.UTF_8.toString());
            // 第2个位置是price
            FileItem priceItem = fileItems.get(1);
            String price = priceItem.getString(StandardCharsets.UTF_8.toString());
            // 第3个位置是author
            FileItem authorItem = fileItems.get(2);
            String author = authorItem.getString(StandardCharsets.UTF_8.toString());
            // 第4个位置是sales
            FileItem salesItem = fileItems.get(3);
            String sales = new String(salesItem.get(), 0, salesItem.get().length);
            // 第5个位置是stock
            FileItem stockItem = fileItems.get(4);
            String stock = new String(stockItem.get(), 0, stockItem.get().length);
            // 第6个位置是pageNo
            FileItem pageNoItem = fileItems.get(5);
            pageNo = Integer.parseInt(new String(pageNoItem.get(), 0, pageNoItem.get().length));
            // 第7个位置是photo
            FileItem photoItem = fileItems.get(6);
            String fieldName = photoItem.getName();
            String realPath = this.getServletContext().getRealPath("static/img");

            String relativePath = "static/img" + "/" + System.currentTimeMillis() + ".jpg";
            String filePathAndName = realPath + "/" + System.currentTimeMillis() + ".jpg";
            File file = new File(filePathAndName);
            photoItem.write(file);

            Book book = new Book(null, name, author, Double.parseDouble(price)
                    , Integer.parseInt(sales), Integer.parseInt(stock), relativePath);
            bookService.addBook(book);
            logger.info("管理员新增图书 " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 请求重定向到 /book-store/manager?action=bookPage&pageNo=${pageTotal}，
        // 因为添加的图书总是会被展示在最后一页
        pageNo++;
        resp.sendRedirect("/book-store/manager?action=bookPage&pageNo=" + pageNo);
    }
}
