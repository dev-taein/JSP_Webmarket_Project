<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="dao.ProductRepository"%>
<%@page import="dto.Product"%>
<%@page import="java.util.ArrayList"%>
<!--  ProductId값이 유효하지 않으면 에러 페이지로 이동 -->
<%@page errorPage="exceptionNoProductId.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <jsp:useBean id="productDAO" class="dao.ProductRepository" scope="session"/> --%>
<!DOCTYPE html>
<% 
	request.setCharacterEncoding("UTF-8");
	DecimalFormat dfFormat = new DecimalFormat("###,###");
%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/CSS/bootstrap.min.css" />
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"> -->
<meta charset="UTF-8">
<title>상품 상세 정보</title>

<!-- 장바구니 추가를 위한 핸들러 함수 -->
<script type="text/javascript">
	function addToCart() {
		if(confirm("해당 상품을 장바구니에 추가 하시겠습니까?")){
			document.addForm.submit();
		} else {
			document.addForm.reset();
		}
	}
</script>

</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">상품 정보</h1>
		</div>
	</div>
	<%-- <%
		/* //얻어온 상품 ID값을 얻어낸다.
		String id = request.getParameter("id");
		ProductRepository dao = ProductRepository.getInstance();
		//얻어온 상품 ID값을 이용해서 해당하는 Product객체를 얻는다.
		Product product = dao.getProductById(id); */
	%> --%>
	<%@include file="../dbconn.jsp" %> 
	<%
		//상품id값을 받아온다.
		String productId = request.getParameter("id");
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from product where p_id = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, productId);
		rs = pstmt.executeQuery();
		if(rs.next()){
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-5">
				<img src="${pageContext.request.contextPath}/resources/images/<%= rs.getString("p_fileName") %>" 
						style="width: 100%">
			</div>
			
			<div class="col-md-6">
				<h3><%= rs.getString("p_name") %></h3>
				<p><%= rs.getString("p_description") %></p>
				<p><b>상품 코드 : </b><span class="badge badge-danger"><%= rs.getString("p_id") %></span></p>
				<p><b>제조사 : </b><%= rs.getString("p_manufacturer") %></p>
				<p><b>분류 : </b><%= rs.getString("p_category") %></p>
				<p><b>재고 수량 : </b><%= dfFormat.format(Long.parseLong(rs.getString("p_unitsInStock"))) %></p>
				<h4><%= dfFormat.format(Integer.parseInt(rs.getString("p_unitPrice"))) %>원</h4>
				
				<p><form name="addForm" action="./addCart.jsp?id=<%= rs.getString("p_id") %>" 
							method="POST">
					<a href="#" class="btn btn-info" onclick="addToCart()">상품 주문&raquo;</a>
					<a href="./cart.jsp" class="btn btn-warning">장바구니&raquo;</a>
					<a href="./products.jsp" class="btn btn-secondary">상품 목록&raquo;</a>
				</form>
			</div>
			<% 
				}
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			%>
		</div>
		<hr>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
</html>