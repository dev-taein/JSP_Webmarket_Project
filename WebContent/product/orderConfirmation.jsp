<%@page import="dto.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	request.setCharacterEncoding("UTF-8");

	String cartId = session.getId();
	
	String Shipping_cartId = "";
	String Shipping_name = "";
	String shipping_Date = "";
	String shipping_Country = "";
	String shipping_zipCode = "";
	String shipping_addressName = "";

	//모든 쿠키를 설정된 내용을 배열로 받기
	Cookie[] cookies = request.getCookies();
	
	if(cookies != null){
		for(int i=0; i<cookies.length; i++){
			Cookie thisCookie = cookies[i];
			//쿠키의 이름 가져오기
			String str = thisCookie.getName(); 
			//문자셋을 다시 맞추기
			if(str.equals("Shipping_cartId")){
				Shipping_cartId = URLDecoder.decode(thisCookie.getValue(), "UTF-8");
			}
			if(str.equals("Shipping_name")){
				Shipping_name = URLDecoder.decode(thisCookie.getValue(), "UTF-8");
			}
			if(str.equals("shipping_Date")){
				shipping_Date = URLDecoder.decode(thisCookie.getValue(), "UTF-8");
			}
			if(str.equals("shipping_Country")){
				shipping_Country = URLDecoder.decode(thisCookie.getValue(), "UTF-8");
			}
			if(str.equals("shipping_zipCode")){
				shipping_zipCode = URLDecoder.decode(thisCookie.getValue(), "UTF-8");
			}
			if(str.equals("shipping_addressName")){
				shipping_addressName = URLDecoder.decode(thisCookie.getValue(), "UTF-8");
			}
		}
	}
	
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>주문 정보</title>
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">주문 정보</h1>
		</div>
	</div>
	
	<div class="container col-8 alert alert-info">
		<div class="text-center">
			<h1>영수증</h1>
		</div>
		<div class="row justify-content-between">
			<div class="col-4" align="left">
				<strong>배송 주소</strong><br/>
				성명 : <% out.println(Shipping_name); %>  <br/>
				우편번호 : <% out.println(shipping_zipCode); %>  <br/>
				주소: <% out.println(shipping_addressName); %>  <br/>
			</div>
			<div class="col-4" align="right">
				<p><em>배송일 : <% out.println(shipping_Date); %> </em>
			</div>
		</div>
		<div>
			<table class="table table-hover">
				<tr>
					<th class="text-center">물품</th>
					<th class="text-center">수량</th>
					<th class="text-center">가격</th>
					<th class="text-center">소계</th>
				</tr>
				<%
					int sum = 0;
					ArrayList<Product> cartList = (ArrayList<Product>)session.getAttribute("cartlist");
					if(cartList == null)
						cartList = new ArrayList<Product>();
					for(int i=0; i<cartList.size(); i++){
						Product product = cartList.get(i);
						//물품의 가격
						int total = product.getUnitPrice() * product.getQuantity(); 
						sum += total; //소계
					
					
				%>
				<tr>
					<td class="text-center"><em><%= product.getPname() %></em></td>
					<td class="text-center"><em><%= product.getQuantity() %></em></td>
					<td class="text-center"><em><%= product.getUnitPrice() %>원</em></td>
					<td class="text-center"><em><%= total %>원</em></td>
				</tr>
				<% } %>
				
				<tr>
					<td></td>
					<td></td>
					<td class="text-right"><strong>총액</strong></td>
					<td class="text-center text-danger"><strong><%= sum %>원</strong></td>
				</tr>
			</table>
			<a href="./shippingInfo.jsp?cartId=<%= Shipping_cartId %>" class="btn btn-secondary" role="button">이전</a>
			<a href="./thankCustomer.jsp" class="btn btn-success" role="button">주문완료</a>
			<a href="./checkOutCancelled.jsp" class="btn btn-secondary" role="button">취소</a>
		</div>
	</div>
</body>
</html>