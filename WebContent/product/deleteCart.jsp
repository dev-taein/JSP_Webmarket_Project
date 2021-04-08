<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>장바구니 비우기</title>
</head>
<body>
	<%
		//cartId는 세션id이다. 즉, 세션의 모든 상품을 삭제한다.
		String id = request.getParameter("cartId");
		if(id == null || id.trim().equals("")){
			response.sendRedirect("cart.jsp");
			return;
		}
		//세션을 삭제한다.
		session.invalidate();
		response.sendRedirect("cart.jsp");
		
		
	%>
</body>
</html>