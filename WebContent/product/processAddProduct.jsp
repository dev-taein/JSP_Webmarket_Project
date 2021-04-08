<%@page import="java.sql.*"%>
<%@ page import="com.oreilly.servlet.*"%>
<%@ page import="com.oreilly.servlet.multipart.*"%>
<%@ page import="java.util.*"%>
<%@ page import="dto.Product"%>
<%@ page import="dao.ProductRepository"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ include file="../dbconn.jsp" %>    
<%
	request.setCharacterEncoding("utf-8");
	String realFolder = "D:/JSP작업/WebMarket/WebContent/resources/images"; 
	int maxSize = 10 * 1024 * 1024; //최대 업로드 크기(10M)
	String encType = "utf-8";  //인코딩 유형
	
	MultipartRequest multi = new MultipartRequest(request, realFolder, maxSize,
			                 encType, new DefaultFileRenamePolicy());

	//addProduct.jsp에서 사용자가 입력한 부분을 받아서 저장을 하고 있다.
	String productId = multi.getParameter("productId");
	String pname = multi.getParameter("pname");
	String unitPrice = multi.getParameter("unitPrice");
	String description = multi.getParameter("description");
	String manufacturer = multi.getParameter("manufacturer");
	String category = multi.getParameter("category");
	String unitsInStock = multi.getParameter("unitsInStock");
	String condition = multi.getParameter("condition");
	
	Integer price;
	long stock;
	
	//단가(unitPrice)입력창에 미 입력시에
	if(unitPrice.isEmpty()){
		price = 0; 
	}
	else{
		//String을 숫자로 변환하고 있다.
		price = Integer.valueOf(unitPrice);
	}
	
	//재고수량(unitsInStock)입력창에 미 입력시에
	if(unitsInStock.isEmpty()){
		stock = 0; 
	}
	else{
		//String을 long타입으로 변환하고 있다.
		stock = Long.valueOf(unitsInStock);
	}
	
	Enumeration files = multi.getFileNames();
	String fname = (String) files.nextElement();
	String fileName = multi.getFilesystemName(fname);
	
	//db연동
	PreparedStatement pstmt = null;
	String sql = "insert into product value(?,?,?,?,?,?,?,?,?)";
	pstmt = conn.prepareStatement(sql);
	
	//아래 9개의 사용자로부터 입력받은 데이터를 DB에 저장을 하고 있다.
	pstmt.setString(1, productId);
	pstmt.setString(2, pname);
	pstmt.setInt(3, price);
	pstmt.setString(4, description);
	pstmt.setString(5, manufacturer);
	pstmt.setString(6, category);
	pstmt.setLong(7, stock);
	pstmt.setString(8, condition);
	pstmt.setString(9, fileName);
	
	pstmt.executeUpdate();
	System.out.println("상품등록완료");
	
	//자원해제
	if(pstmt != null) pstmt.close();
	if(conn != null) conn.close();
	
	//강제로 페이지 이동을 시키고 있다.
	response.sendRedirect("products.jsp");	
%>    
