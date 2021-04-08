<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>주문 취소</title>
</head>
<body>
	<jsp:include page="../menu.jsp" />
		<div class="jumbotron">
			<div class="container">
				<h1 class="display-3">주문 완료</h1>
			</div>
		</div>
		<div class="container">
			<h2 class="alert alert-danger">주문이 취소되었습니다.</h2>
		</div>
		<div class="container">
			<p><a href="./products.jsp" class="btn btn-secondary" role="button">&raquo; 상품 목록</a>
		</div>
</body>
</html>