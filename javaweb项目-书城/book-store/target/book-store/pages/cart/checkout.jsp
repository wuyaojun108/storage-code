<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="/book-store/"></base>
<title>结算页面</title>
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
			<span class="wel_word">结算</span>
			<div>
				<span>欢迎<span class="um_span">${sessionScope.user.username}</span>光临尚硅谷书城</span>
				<a href="pages/order/order.jsp">我的订单</a>
				<a href="index.jsp">返回</a>
			</div>
	</div>
	
	<div id="main">
		<h1>你的订单已结算，订单号为: ${orderId}</h1>
	</div>
	
    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>
</body>
</html>