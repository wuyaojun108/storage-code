<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="/book-store/"></base>
<meta charset="UTF-8">
<title>编辑图书</title>
<link type="text/css" rel="stylesheet" href="static/css/style.css">
<style type="text/css">
	h1 {
		text-align: center;
		margin-top: 200px;
	}

	h1 a {
		color:red;
	}

	input {
		text-align: center;
	}
</style>
</head>
<body>
	<div id="header">
		<img class="logo_img" alt="" src="static/img/logo.gif" >
		<span class="wel_word">编辑图书</span>
		<div>
			<a href="index.jsp">返回商城</a>
		</div>
	</div>

	<div id="main">
		<form action="/book-store/manager/addBook" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td>名称</td>
					<td>价格</td>
					<td>作者</td>
					<td>销量</td>
					<td>库存</td>
					<td colspan="2">上传封面</td>
				</tr>
				<tr>
					<td><input name="name" type="text" value="${book.name}"/></td>
					<td><input name="price" type="text" value="${book.price}"/></td>
					<td><input name="author" type="text" value="${book.author}"/></td>
					<td><input name="sales" type="text" value="${book.sales}"/></td>
					<td><input name="stock" type="text" value="${book.stock}"/></td>
					<input name="pageNo" type="hidden" value="${param.pageNo}"/>
					<td colspan="2"><input name="photo" type="file" /></td>
					<!--新增图书和修改图书都是使用的这个页面，所以action的属性值需要动态获取，以判断表单请求具体
					是什么操作-->

					<td><input type="submit" value="提交"/></td>
				</tr>
			</table>
		</form>
	</div>
	${param.action}

    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>

</body>
</html>