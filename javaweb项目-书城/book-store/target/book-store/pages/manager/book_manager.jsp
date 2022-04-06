<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<base href="/book-store/"></base>
<meta charset="UTF-8">
<title>图书管理</title>
<link type="text/css" rel="stylesheet" href="static/css/style.css" >

<!--引入jquery库-->
<script src="static/scripts/jquery-3.6.0.js" ></script>
<script>
    $(function(){
        // 在用户点击删除按钮时，触发提醒，避免用户误删除
        $("a.book_delete").click(function(){
            // 获取当前a标签的父对象的父对象中的第一个元素的内容，也就是书名
            var msg = "是否删除：" + $(this).parent().parent().find("td:first").text()
            // confirm方法，会在页面上弹出提示，如果用户选择取消，会返回false
            return confirm(msg)
        })
        // 实现跳到指定页面的功能
        $("#jumpTo").click(function(){
            var pageNo = $("#pn_input").val()
            location.href = "/book-store/manager/book_manager?action=page&pageNo=" + pageNo
        })
    })
</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">图书管理系统</span>
			<div>
				<a href="/book-store/order?action=queryOrdersByUserId&userId=${sessionScope.user.id}">订单管理</a>
				<a href="index.jsp">返回商城</a>
			</div>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<th>名称</th>
				<th>价格</th>
				<th>作者</th>
				<th>销量</th>
				<th>库存</th>
				<th colspan="2">操作</th>
			</tr>

			<c:forEach items="${requestScope.page.items}" var="book">
                <tr>
                    <td>${book.name}</td>
                    <td>${book.price}</td>
                    <td>${book.author}</td>
                    <td>${book.sales}</td>
                    <td>${book.stock}</td>
                    <!--修改图书信息前需要在编辑页面中展示图书信息，所以需要经过一个servlet来获取数据-->
                    <td>
                        <a href="/book-store/manager?action=getBook&id=${book.id}&pageNo=${page.pageNo}">修改
                        </a>
                    </td>
                    <td><a class="book_delete"
                        href="/book-store/manager?action=delete&id=${book.id}&pageNo=${page.pageNo}">删除
                        </a>
                    </td>
                </tr>
            </c:forEach>

			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><a href="pages/manager/book_add.jsp?pageNo=${page.pageTotal}">添加图书</a></td>
			</tr>	
		</table>
		<br/>
		<br/>

    <%--静态分页条--%>
    <%@ include file="/pages/common/page_nav.jsp"%>
	
    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>
</body>
</html>