<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그아웃</title>
</head>
<body>
	<% 
		//모든 사용자 세션 삭제
		session.invalidate();
		//로그아웃 후 상품 등록 페이지로 이동
		response.sendRedirect("addProduct.jsp");
	%>
</body>
</html>