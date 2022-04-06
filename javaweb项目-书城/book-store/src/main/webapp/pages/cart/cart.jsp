<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <base href="/book-store/"></base>
    <meta charset="UTF-8">
    <title>购物车</title>
    <link type="text/css" rel="stylesheet" href="static/css/style.css" >
    <script src="static/scripts/jquery-3.6.0.js"></script>
    <script>
        $(function(){
            // 如果数量输入框中的内容有改变，则触发这个事件
            $(".updateCount").change(function(){
                // 获取商品名称
                var name = $(this).parent().parent().find("td:first").text()
                // 获取商品数量
                var count = this.value
                if(confirm("你确定要将【"+ name +"】商品修改数量为：" + count  + "吗？")){
                    location.href = "/book-store/cart?action=updateCount&count="
                        + count
                        + "&bookId=" + $(this).attr("bookId")
                } else {
                    this.value = this.defaultValue;
                }
            })

            // 使用ajax实现清空购物车功能
            $("#clearCart").click(function(){
                $.get({
                    url: "/book-store/cart?action=clearCart",
                    data: "",
                    success: function(msg){
                        // 重新加载当前页面
                        // location.href = "${header.Referer}"
                        location.href = "/book-store/pages/cart/cart.jsp"
                    },
                    dataType: "text"
                })
            })
        })
    </script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">购物车</span>
			<div>
				<span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
				<a href="/book-store/order?action=queryOrdersByUserId&userId=${sessionScope.user.id}">我的订单</a>
				<a href="index.jsp">注销</a>&nbsp;&nbsp;
				<a href="index.jsp">返回</a>
			</div>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>
			<!--购物车为空-->
            <c:if test="${empty sessionScope.cart.items}">
                <tr><td colspan="5">您的购物车是空的</td></tr>
            </c:if>
            <!--购物车不为空-->
            <c:if test="${not empty sessionScope.cart.items}">
			    <c:forEach items="${sessionScope.cart.items}" var="entry">
			         <tr>
                	    <td>${entry.value.name}</td>
                	    <td>
                	        <input type="text"
                	            value="${entry.value.count}"
                	            bookId="${entry.value.id}"
                	            class="updateCount"
                	            style="width: 70px"/>
                	    </td>
                	    <td>${entry.value.price}</td>
                	    <td>${entry.value.totalPrice}</td>
                	    <td><a href="/book-store/cart?action=deleteItem&id=${entry.value.id}">删除</a></td>
                	</tr>
			    </c:forEach>
			</c:if>
		</table>
		
		<div class="cart_info">

		<!--购物车不为空-->
		<c:if test="${not empty sessionScope.cart.items}">
		    <span class="cart_span">
                购物车中共有
                <span class="b_count">${sessionScope.cart.totalCount}</span>
                件商品
            </span>
            <span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
            <span class="cart_span"><button id="clearCart">清空购物车</button></span>
            <span class="cart_span"><a href="/book-store/order?action=createOrder">去结账</a></span>
		</c:if>
		</div>
	
	</div>
	
    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>
</body>
</html>