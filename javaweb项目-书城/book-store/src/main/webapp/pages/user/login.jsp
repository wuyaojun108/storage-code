<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <base href="/book-store/"></base>
    <meta charset="UTF-8">
    <title>尚硅谷会员登录页面</title>
	<link type="text/css" rel="stylesheet" href="static/css/style.css" >
	<script src="static/scripts/jquery-3.6.0.js"></script>
	<script>
		$(function(){
		    // 当输入框获取焦点之后，取消“请用户输入用户名”的提示
			$(".itxt").focus(function(){
			    //$(".msg_cont").css("display", "none")
			    $(".msg_cont").css("visibility", "hidden")
			})

			// 当用户点击提交时触发的事件
			$("#sub_btn").click(function(){
			    var usernameText = $("#username").val()
			    var passwordText = $("#password").val()
			    // 判断用户名或密码是否为空，如果是，弹出提示
			    if(usernameText == ""){
			        $("#input_prompt").text("请输入用户名")
			    } else if (passwordText == ""){
			        $("#input_prompt").text("请输入密码")
			    } else {
			        $("#input_prompt").css("visibility", "hidden")
                    // 使用ajax向后台发送请求
                    var form_data = $("#login_form").serialize()
			        $.get({
			            url: "/book-store/user?action=login",
			            data: form_data,
			            success: function(msg){
			                if(msg == "登录成功"){
			                    location.href = "/book-store/index.jsp"
			                } else {
			                    $("#input_prompt").css("visibility", "visible")
			                    $("#input_prompt").text(msg)
			                }
			            },
			            dataType: "text"
			        })
			    }
			})

/* 取消这个功能，因为如果用户名和密码是自动填充的，会影响使用
			// 用户输入完用户名之后，使用ajax向后台发送请求，判断用户名是否可用，同时把结果返回给客户
			$("#username").blur(function(){
			    var username = this.value;
			    $.get({
			        url: "/book-store/user?action=isUsernameExists",
			        data: "action=isUsernameExists&username=" + username,
			        success: function(msg){
			            $("#input_prompt").text(msg)
			        },
			        dataType: "text"
			    })
			})
*/
		})
	</script>
</head>
<body>
	<div id="login_header">
		<img class="logo_img" alt="" src="static/img/logo.gif">
	</div>

	<div class="login_banner">

		<div id="l_content">
			<span class="login_word">欢迎登录</span>
		</div>

		<div id="content">
			<div class="login_form">
				<div class="login_box">
					<div class="tit">
						<h1>尚硅谷会员</h1>
						<a href="pages/user/register.jsp">立即注册</a>
					</div>
					<div class="msg_cont">
						<b></b>
						<span class="errorMsg">
                             ${msg==null?"请输入用户名和密码":msg}
                         </span>
					</div>
					<div class="form">
						<form action="/book-store/user?action=login" method="post" id="login_form">
							<label>用户名称：</label>
							<input id="username" class="itxt" type="text" placeholder="请输入用户名"
								   autocomplete="off" name="username"/>
							<br />
							<br />
							<label>用户密码：</label>
							<input id="password" class="itxt" type="password" placeholder="请输入密码"
								   autocomplete="off" name="password" />
							<br />
							<br />
							<span style="color:red" id="input_prompt"></span>
						</form>
                        <button id="sub_btn">登录</button>
					</div>
				</div>
			</div>
		</div>
	</div>
    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>
</body>
</html>