<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <base href="/book-store/"></base>
    <meta charset="UTF-8">
    <!--设置html页面不被浏览器缓存-->
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache, must-revalidate">
    <meta http-equiv="expires" content="Wed, 26 Feb 1997 08:21:57 GMT">
    <title>书城首页</title>
    <link type="text/css" rel="stylesheet" href="static/css/style.css" >
    <script src="static/scripts/jquery-3.6.0.js"></script>

    <script>
        $(function(){
            // 为加入购物车按钮绑定单击事件
            $(".addBookToItem").click(function(){
                 // 判断当前用户是否登录
                 var username = $(this).attr("username")
                 if(username == ""){
                     $(".cartMsg").css("color", "red")
                     $(".cartMsg").css("visibility", "visible")
                     return false
                 } else {
                     $(".cartMsg").css("visibility", "hidden")
                     // 向后台页面发送请求，请求中附带点击事件对应的bookId
                     location.href = "/book-store/cart?action=addBookToItem&bookId="
                         + $(this).attr("bookId")
                         + "&username=" + $(this).attr("username")
                 }
            })

        })
    </script>
</head>
<body>

<%--动态的获取URL中直到项目名之前的部分，展示在页面上--%>
<%
    String basePath = request.getScheme()
        + "://"
        + request.getServerName()
        + ":"
        + request.getServerPort()
        + request.getContextPath()
        + "/";
%>
<%--<%=basePath%>--%>

	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">网上书城</span>
			<div>
			    <%--如果会话域中的用户对象为空，表明当前没有用户登录--%>
			    <c:if test="${sessionScope.user == null}">
			        <a href="pages/user/login.jsp">登录</a> |
                	<a href="pages/user/register.jsp">注册</a> &nbsp;&nbsp;
			    </c:if>

			    <%--如果会话域中的用户对象不为空，表明当前有用户登录--%>
			    <c:if test="${sessionScope.user != null}">
                    <span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临静默书咖</span>
                    <a href="pages/cart/cart.jsp">购物车</a>
                    <a href="/book-store/order?action=queryOrdersByUserId&userId=${sessionScope.user.id}">
                        我的订单
                    </a>
                    <a href="/book-store/user?action=logout">退出登录</a>&nbsp;&nbsp;
                </c:if>

				<c:if test="${sessionScope.user != null && sessionScope.user.username == 'admin'}">
				    <a href="pages/manager/manager.jsp">后台管理</a>
				</c:if>

			</div>
	</div>
	<div id="main">
		<div id="book">
			<div class="book_cond">
				<form action="/book-store/client/book" method="get">
				    <input type="hidden" name="action" value="pageByPrice">
					价格：<input id="min" type="text" name="min" value="${minPrice}"> 元 -
						<input id="max" type="text" name="max" value="${maxPrice}"> 元
						<input type="submit" value="查询" />
				</form>
			</div>

			<br/>
			<br/>

		<c:forEach items="${requestScope.page.items}" var="book">
			<div class="b_list">
				<div class="img_div">
					<img class="book_img" alt="" src="${book.imgPath}" />
				</div>
				<div class="book_info">
					<div class="book_name">
						<span class="sp1">书名:</span>
						<span class="sp2">${book.name}</span>
					</div>
					<div class="book_author">
						<span class="sp1">作者:</span>
						<span class="sp2">${book.author}</span>
					</div>
					<div class="book_price">
						<span class="sp1">价格:</span>
						<span class="sp2">￥${book.price}</span>
					</div>
					<div class="book_sales">
						<span class="sp1">销量:</span>
						<span class="sp2">${book.sales}</span>
					</div>
					<div class="book_amount">
						<span class="sp1">库存:</span>
						<span class="sp2">${book.stock}</span>
					</div>
					<div class="book_add">
						<button class="addBookToItem" bookId="${book.id}"
						    username="${sessionScope.user.username}">
						    加入购物车
						</button>
						<!--错误提示-->
						<span class="cartMsg" style="visibility: hidden">尚未登录</span>
					</div>
				</div>
			</div>
	    </c:forEach>
		</div>
		
    <%--静态分页条--%>
    <%@ include file="/pages/common/page_nav.jsp"%>
	
    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>
</body>
</html>