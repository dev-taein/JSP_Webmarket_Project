<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
	Connection conn = null;

	try{
		String url = "jdbc:mysql://localhost:3306/webmarket?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String password = "a1234";
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url, user, password);
		System.out.println("데이터베이스 연결이 되었습니다.");
		
	} catch(SQLException e){
		System.out.println("데이터베이스 연결이 실패되었습니다.");
		System.out.println("예외 이유 : ");
		e.printStackTrace();
	}
%>