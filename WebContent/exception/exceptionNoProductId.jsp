<!-- 상품ID 예외 처리 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>상품 ID 오류</title>
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h2 class="alert alert-danger">해당 상품이 존재하지 않습니다.</h2>
		</div>
	</div>
	
	<div class="container">
		<!-- 요청 URL을 표시하고 요청 파라메터 값도 표시한다. -->
		<p><%= request.getRequestURL() %>?<%= request.getQueryString() %></p>
		<p><a href="products.jsp" class="btn btn-secondary">상품 목록&raquo;</a></p>
	</div>

</body>
</html>