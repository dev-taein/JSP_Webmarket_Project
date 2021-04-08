<%@page import="java.util.ArrayList"%>
<%@page import="dto.Product"%>
<%@page import="dao.ProductRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 주문</title>
</head>
<body>
	<%
		String id = request.getParameter("id");
	
		//전송된 상품 ID가 잘못되었을 경우
		if(id == null || id.trim().equals("")){
			response.sendRedirect("products.jsp");
			return;
		}
		
		//가져온 상품 ID의 클래스 인스턴스를 받아온다.
		ProductRepository dao = ProductRepository.getInstance();
		//해당 id값을 이용해서 상품 상세정보를 얻어온다.
		Product product = dao.getProductById(id);
		
		//해당 상품이 없을 시
		if(product == null){
			response.sendRedirect("exceptionNoProductId.jsp");	
		}
		
		//DB에서 저장된 모든 상품을 가져와서 대입하고 있다.
		ArrayList<Product> goodList = dao.getAllProducts();
		
		//상품전체목록 중 사용자가 주문 한 상품의 id와 일치하는지 비교해서 Product에 입력한다.
		Product goods = new Product();
		for(int i=0; i<goodList.size(); i++){
			goods = goodList.get(i);
			if(goods.getProductId().equals(id)){
				break;
			}
		}
		
		
		//세션 속성의 이름이 cartlist(장바구니에 담긴 물품 목록)의 세션정보를 가져온다.
		ArrayList<Product> list = (ArrayList<Product>)session.getAttribute("cartlist");
		//cartlist이 null이라면 새로운 리스트를 만들고 cartlist에 리스트 값을 대입한다.
		if(list == null){
			list = new ArrayList<Product>();
			session.setAttribute("cartlist", list);
		}
		
		int cnt = 0;
		Product goodsQnt = new Product();
		
		//상품이 장바구니에 중복이 될 수 있을 시
		for(int i=0; i<list.size(); i++){
			goodsQnt = list.get(i);
			if(goodsQnt.getProductId().equals(id)){
				cnt++;
				int orderQuandity = goodsQnt.getQuantity() + 1;
				goodsQnt.setQuantity(orderQuandity);
			}
		}
		
		//상품이 장바구니에 목록에 없을 시 
		if(cnt == 0) {
			goods.setQuantity(1);
			list.add(goods);
		}
		
		response.sendRedirect("product.jsp?id=" + id);
	%>

</body>
</html>