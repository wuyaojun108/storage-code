<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>订单详情</title>
<base href="/book-store/"></base>
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}
</style>
</head>
<body>

	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">订单详情</span>
			<div>
				<span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
				&nbsp;&nbsp;
				<a href="${header.referer}">返回</a>
			</div>
	</div>

	<div id="main">

		<table>
			<tr>
				<td>书名</td>
				<td>数量</td>
				<td>单价</td>
				<td>总价</td>
			</tr>
			<c:forEach items="${sessionScope.orderItems}" var="orderItem">
			    <tr>
			    	<td>${orderItem.name}</td>
			    	<td>${orderItem.count}</td>
			    	<td>${orderItem.price}</td>
			    	<td>${orderItem.totalPrice}</td>
			    </tr>
			</c:forEach>
		</table>
	</div>

    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>

</body>
</html>