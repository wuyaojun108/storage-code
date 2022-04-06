<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单管理</title>
<base href="/book-store/"></base>
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">订单管理系统</span>
			<div>
				<a href="index.jsp">返回商城</a>
			</div>
	</div>
	
	<div id="main">
		<table>
			<tr>
				<td>日期</td>
				<td>金额</td>
				<td>详情</td>
			</tr>
			<c:forEach items="${sessionScope.allOrders}" var="order">
			    <tr>
			    	<td>${order.createTime}</td>
			    	<td>${order.price}</td>
			    	<td><a href="/book-store/order?action=queryOrderItemsByOrderId&orderId=${order.orderId}">
			    	    查看详情
			    	</a></td>
			    </tr>
			</c:forEach>

		</table>
	</div>
	
    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>
</body>
</html>