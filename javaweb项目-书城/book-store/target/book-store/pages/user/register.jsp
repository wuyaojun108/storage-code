<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="/book-store/"></base>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面</title>
<link type="text/css" rel="stylesheet" href="static/css/style.css" >
    <style type="text/css">
    	.login_form{
    		height: 420px;
    		margin-top: 25px;
    	}
    </style>
	<script src="static/scripts/jquery-3.6.0.js"></script>
	<script>
		$(function(){
		    // 判断用户名是否存在于数据库中
			$("#username").blur(function(){
			    var username = this.value;
			    $.get({
			        url: "/book-store/user?action=isUsernameExists",
			        data: "action=isUsernameExists&username=" + username,
			        success: function(msg){
			            if(msg == "用户名已存在"){
			                $("#input_prompt").css("visibility", "visible")
			                $("#input_prompt").text("用户名已存在，请重新输入")
			            } else {
			                $("#input_prompt").css("visibility", "hidden")
			            }
			        },
			        dataType: "text"
			    })
			})


			// 点击提交时校验：
			// 1，用户名和密码是否符合规范；
			// 2：两次输入的密码是否一致；
			// 3，校验邮箱名称是否符合规范
			// 4，校验用户输入的验证码是否为空
		    $("#sub_btn").click(function(){
		        // 获取用户的输入
		        var usernameText = $("#username").val()
			    var passwordText = $("#password").val()
			    var repwdText = $("#repwd").val()
			    var emailText = $("#email").val()
			    var codeText = $("#code").val()
		        // 校验用户名的正则表达式，可以包含中文和英文字母，长度在2到12位之间
			    var usernamePat = /[\u4e00-\u9fa5\w]{2,12}/
			    // 校验密码的正则表达式：强密码(必须包含大小写字母和数字的组合，不能使用特殊字符，长度在8-10之间)
			    // (?=字符)：位置匹配，正向肯定预查，匹配pattern之前的位置，在最终结果中不包含pattern
			    var passwordPat = /^(?=.*[\d])(?=.*[a-z])(?=.*[A-Z])(?=.*[^\w]).{8,16}$/
			    // 校验邮箱的正则表达式，有些有些@符号之前可能有“.”
			    var emailPat = /[\w]+(\.[\w]+)*@[\w]+(\.[\w])+/

                $("#input_prompt").css("visibility", "visible")
                if(usernameText == "" || passwordText == "" || repwdText == "" || emailText == ""
                    || codeText == ""){
                    $("#input_prompt").html("有必填项没有填<br/>")
                } else if (!usernamePat.test(usernameText)){
                    $("#input_prompt").html("用户名可以使用中文和英文字母，必须2到12位<br/>")
			    } else if(!passwordPat.test(passwordText)){
                    $("#input_prompt").html("密码必须包含英文大小写和特殊字符<br/>")
			    } else if(repwdText != passwordText){
                    $("#input_prompt").html("两次输入的密码必须一致<br/>")
			    } else if(!emailPat.test(emailText)){
                    $("#input_prompt").html("邮箱名不符合规范<br/>")
			    } else if(codeText == ""){
                    $("#input_prompt").html("请输入验证码<br/>")
			    } else{
			        $("#input_prompt").css("visibility", "hidden")
                    // 使用ajax向后台发送请求
                    var form_data = $("#register_form").serialize()
                    $.get({
                        url: "/book-store/user?action=register",
                        data: form_data,
                        success: function(msg){
                            $("#input_prompt").css("visibility", "visible")
                            $("#input_prompt").text(msg)
                        },
                        dataType: "text"
                    })
			    }
		    })


		    // 为验证码图片绑定单击事件，点击切换验证码
		    $("#img_code").click(function(){
		        // 为了防止浏览器因为缓存而不去访问服务器，在每个请求之后都拼接一个日期字符串
		        this.src = "/book-store/verification_code.jpg?date=" + new Date()
		    })
		})
	</script>



</head>
<body>
	<div id="login_header">
		<img class="logo_img" alt="" src="static/img/logo.gif" >
	</div>

	<div class="login_banner">
		<div id="l_content">
			<span class="login_word">欢迎注册</span>
		</div>

		<div id="content">
			<div class="login_form">
				<div class="login_box">
					<div class="tit">
						<h1>注册尚硅谷会员</h1>
						<a href="pages/user/login.jsp">立即登录</a>
					</div>
					<div class="form">
						<form action="/book-store/register" method="post" id="register_form">
							<label>用户名称：</label>
							<input class="itxt" type="text" placeholder="请输入用户名"
								   autocomplete="off"  name="username" id="username"/>
							<br />
							<br />
							<label>用户密码：</label>
							<input class="itxt" type="password" placeholder="请输入密码"
								   autocomplete="off"  name="password" id="password" />
							<br />
							<br />
							<label>确认密码：</label>
							<input class="itxt" type="password" placeholder="确认密码"
								   autocomplete="off"  id="repwd" />
							<br />
							<br />
							<label>电子邮件：</label>
							<input class="itxt" type="text" placeholder="请输入邮箱地址"
								   autocomplete="off"  name="email" id="email"/>
							<br />
							<br />
							<label>验证码：</label>
							<input class="itxt" type="text" style="width: 100px;"
								   id="code" name="code"/>
							<img id="img_code" alt="" src="/book-store/verification_code.jpg"
								 style="float: right; margin-right: 40px; width: 130px">
							<br />
							<span style="color:red" id="input_prompt"><br/></span>
						</form>
						<button id="sub_btn">注册</button>
					</div>
				</div>
			</div>
		</div>
	</div>
    <%--静态引入页脚--%>
    <%@ include file="/pages/common/footer.jsp"%>
</body>
</html>