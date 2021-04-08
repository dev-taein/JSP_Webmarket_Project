<%@page import="dto.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

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
			if(str.equals("shipping_Date")){
				shipping_Date = URLDecoder.decode(thisCookie.getValue(), "UTF-8");
			}
			
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>주문 완료</title>
</head>
<body>
	<jsp:include page="../menu.jsp" />
		<div class="jumbotron">
			<div class="container">
				<h1 class="display-3">주문 완료</h1>
			</div>
		</div>
		
		<div class="container">
			<h2 class="alert alert-danger">주문해주셔서 감사합니다.</h2>
		</div>
		<div class="container">
			<p>주문은 <% out.println(shipping_Date); %>에 배송될 예정입니다.!</p>
			<p>주문 번호 : <% out.println(Shipping_cartId); %></p>
		</div>
		
		<div>
			<p><a href="./products.jsp" class="btn btn-secondary">&laquo; 상품 목록</a>
		</div>
</body>
</html>
<% 
	//주문이 완료되면 세션을 삭제한다.
	session.invalidate();

	//쿠키삭제
	for(int i=0; i<cookies.length; i++){
		Cookie thisCookie = cookies[i];
		String str = thisCookie.getName();
	
		if(str.equals("Shipping_cartId")){
			thisCookie.setMaxAge(0);
		}
		if(str.equals("Shipping_name")){
			thisCookie.setMaxAge(0);
		}
		if(str.equals("shipping_Date")){
			thisCookie.setMaxAge(0);
		}
		if(str.equals("shipping_Country")){
			thisCookie.setMaxAge(0);
		}
		if(str.equals("shipping_zipCode")){
			thisCookie.setMaxAge(0);
		}
		if(str.equals("shipping_addressName")){
			thisCookie.setMaxAge(0);
		}
		//변경된 쿠키를 다시 응답처리 객체에 재탑재를 한다.
		response.addCookie(thisCookie);
	}
%>