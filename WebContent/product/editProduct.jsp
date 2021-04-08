<%@page import="java.text.DecimalFormat"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
	request.setCharacterEncoding("UTF-8");
	String edit = request.getParameter("edit"); //상품 수정,삭제할때 넘어오는 edit값을 받고 있다.
	DecimalFormat dfFormat = new DecimalFormat("###,###");
%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>상품 편집</title>

<!-- 삭제 버튼 클릭 시 호출 메시지 -->
<script type="text/javascript">
	function deleteConfirm(id) {
		if(confirm("해당 상품을 삭제합니다!!") == true)
			location.href="./deleteProduct.jsp?id="+id;
		else
			return;
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
	
	<div class="container">
		<div class="row" align="center">
			<%@ include file="../dbconn.jsp" %>
			<%
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				String sql = "select * from product";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()){
			%>
			<div class="col-md-4">
				<img src="${pageContext.request.contextPath}/resources/images/<%= rs.getString("p_fileName") %>" style="width: 100%">
				<h3><%= rs.getString("p_name") %></h3>
				<p><%= rs.getString("p_description") %>
				<p><%= dfFormat.format(Integer.parseInt(rs.getString("p_unitPrice"))) %>원
				<p><%
						if(edit.equals("update")){
					%>
					<a href="./updateProduct.jsp?id=<%= rs.getString("p_id") %>" 
						class="btn btn-success" role="button">수정&raquo;</a>
					<% } else if(edit.equals("delete")){ 
					%>
					<a href="#" onclick="deleteConfirm('<%= rs.getString("p_id")  %>')" 
						class="btn btn-danger" role="button">삭제&raquo;</a>
					<%
						} 
					%>
			</div>
			<%
				}
				if(rs != null) rs.close();
				if(pstmt != null) rs.close();
				if(conn != null) rs.close();
			%>
		</div>
		<hr>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
</html>