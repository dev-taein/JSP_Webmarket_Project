<%@page import="java.net.URLEncoder"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%
	request.setCharacterEncoding("utf-8");
	
	//이 페이지에서 로직을 다 수행하고 난 뒤 다른 페이지로 이동을 하기에 한번 더 문자셋을 맞추어 주기 위해서 URLEncoder를 사용한다.
	Cookie cartId = new Cookie("Shipping_cartId", URLEncoder.encode(request.getParameter("cartId"), "utf-8"));
	
	Cookie name = new Cookie("Shipping_name", URLEncoder.encode(request.getParameter("name"), "utf-8"));
	
	Cookie shippingDate = new Cookie("shipping_Date", URLEncoder.encode(request.getParameter("shippingDate"), "utf-8"));
	
	Cookie country = new Cookie("shipping_Country", URLEncoder.encode(request.getParameter("country"), "utf-8"));
	
	Cookie zipCode = new Cookie("shipping_zipCode", URLEncoder.encode(request.getParameter("zipCode"), "utf-8"));
	
	Cookie addressName = new Cookie("shipping_addressName", URLEncoder.encode(request.getParameter("addressName"), "utf-8"));
	
	
	//각 쿠키의 유지 시간 설정
	cartId.setMaxAge(24*60*60); //24시간
	name.setMaxAge(24*60*60); 
	shippingDate.setMaxAge(24*60*60); 
	country.setMaxAge(24*60*60); 
	zipCode.setMaxAge(24*60*60); 
	addressName.setMaxAge(24*60*60); 
	
	//응답 객체에 탑재
	response.addCookie(cartId);
	response.addCookie(name);
	response.addCookie(shippingDate);
	response.addCookie(country);
	response.addCookie(zipCode);
	response.addCookie(addressName);
	
	//주문 정보 확인 페이지로 이동
	response.sendRedirect("orderConfirmation.jsp");
%>