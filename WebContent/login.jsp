<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
<link rel="stylesheet" href="./resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<jsp:include page="menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">로그인</h1>
		</div>
	</div>
	
	<div class="container" align="center">
		<div class="col-md-4 col-md-offset-4">
			<h3 class="form-signin-headding">Please sign in</h3>
			
			<%
				//로그인 인증에서 실패를 했을 때 작성하는 코드
				String error = request.getParameter("error");
				if(error != null){
					out.println("<div class='alert alert-danger'>");
					out.println("아이디와 비밀번호를 확인해주세요.");
					out.println("</div>");
				}
			%>
			<form class="form-signin" action="j_security_check" method="POST">
				<!-- ID 입력 -->
				<div class="form_group">
				<!-- 웹 접근성을 위해 sr-only을 사용하여 label내용을 숨김 -->
					<label for="inputUserName" class="sr-only">UserName</label>
					<input type="text" class="form-control" placeholder="ID" 
								name='j_username' required autofocus>
				</div>
				<!-- 비밀번호 입력 -->
				<div class="form_group">
					<label for="inputPassword" class="sr-only">Password</label>
					<input type="password" class="form-control" placeholder="Password" 
								name='j_password' required>
				</div>
				<button class="btn btn-lg btn-success btn-block" type="submit">로그인</button>	
			</form>
		</div>
	</div>

</body>
</html>