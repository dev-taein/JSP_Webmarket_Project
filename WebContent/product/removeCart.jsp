<%@page import="java.util.ArrayList"%>
<%@page import="dao.ProductRepository"%>
<%@page import="dto.Product"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>해당 상품 삭제</title>
</head>
<body>
	<%
		//삭제될 상품 id 가져온다.
		String id = request.getParameter("id");
		//전송된 상품 ID가 잘못되었을 경우
		if(id == null || id.trim().equals("")){
			response.sendRedirect("products.jsp");
			return;
		}
		
		//상품 데이터 접근 클래스의 인스턴스를 받아오는 코드
		ProductRepository dao = ProductRepository.getInstance();
		//해당 id값을 이용해서 상품상세정보를 얻어오는 코드
		Product product = dao.getProductById(id);
		//해당 상품이 없을 시
		if(product == null){
			response.sendRedirect("exceptionNoProductId.jsp");	
		}
		
		ArrayList<Product> cartList = (ArrayList<Product>)session.getAttribute("cartlist");
		//전체 상품 목록 중 삭제할 물품 id값과 동일하면 cartList에서 삭제한다.
		Product goodQnt = new Product();
		for(int i=0; i<cartList.size(); i++){
			goodQnt = cartList.get(i);
			if(goodQnt.getProductId().equals(id)){
				cartList.remove(goodQnt);
			}
		}
		response.sendRedirect("cart.jsp");
	%>
</body>
</html>