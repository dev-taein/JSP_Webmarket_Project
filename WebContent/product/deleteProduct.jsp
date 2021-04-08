<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../dbconn.jsp" %> 
	<%
		//어떤 제품을 편집할지의 상품id값을 받아온다.
		String productId = request.getParameter("id");
	
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from product";
		//Connection객체로 부터 쿼리문을 주고 PreparedStatement를 얻고 있다.
		pstmt = conn.prepareStatement(sql);
		//쿼리문 결과 받아온다.
		rs = pstmt.executeQuery();

		//가져온 결과가 있다면..
		if(rs.next()){
			sql = "delete from product where p_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			pstmt.executeUpdate();
		}
		//없을 시
		else {
			out.println("일치하는 상품이 없습니다.");
		}
		if(rs != null) rs.close();
		if(pstmt != null) pstmt.close();
		if(conn != null) conn.close();
		
		response.sendRedirect("editProduct.jsp?edit=delete");
	%>