<%@page import="mvc.model.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	String sessionId = (String) session.getAttribute("sessionId");
	ArrayList<BoardDTO> boardList = (ArrayList<BoardDTO>)request.getAttribute("boardlist");
	int total_record = ((Integer)request.getAttribute("total_record")).intValue();
	int pageNum = ((Integer)request.getAttribute("pageNum")).intValue();
	int total_page = ((Integer)request.getAttribute("total_page")).intValue();
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>게시판</title>

<!-- 로그인 여부를 판단하는 자바스크립트 함수 -->
<script type="text/javascript">
	function checkForm() {
		if(${sessionId == null}){
			alert("로그인을 하셔야 작성 할 수 있습니다.");
			return false;
		}
		//로그인이 되었다면
		location.href="./BoardWriteForm.do?id=<%= sessionId %>";
	}
	
	function loginForm() {
		if(${sessionId == null}){
			alert("로그인을 해야 게시글을 볼 수 있습니다.");
			return false;
		}
	}
</script>

</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">게시판</h1>
		</div>
	</div>
	
	<div class="container">
		<form action='<c:url value="./BoardListAction.do" />' method="post">
			<div>
				<div class="text-right">
					<span class="badge badge-danger">전체 게시글 수 : <%= total_record %></span>
				</div>
			</div>
			<!-- 게시글을 표시하는 부분 -->
			<div style="padding-top: 50px">
				<table class="table table-hover">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>작성일</th>
						<th>조회 수</th>
						<th>글쓴이</th>
					</tr>
					<%
						for(int j=0; j<boardList.size(); j++){
							BoardDTO notice = boardList.get(j);	
					%>
					<tr>
						<td><%= notice.getNum() %></td>
						<!-- 해당 게시글을 클릭하면 해당 게시글로 이동 -->
						
						<td><% if(sessionId == null) { %>
							<a href="#" onclick="loginForm()"><%= notice.getSubject() %></a></td>
						<% 
							} 
						else {
						%>
						<a href="./BoardViewAction.do?num=<%= notice.getNum() %>&pageNum=<%= pageNum %>">
																		<%= notice.getSubject() %></a>
						<% 
							}
						%>
						<td><%= notice.getRegist_day() %>)</td>
						<td><%= notice.getHit() %></td>
						<td><%= notice.getName() %></td>
					</tr>
					<% 
						}
					%>
				</table>
			</div>
			
			<!-- 페이지 수를 표시하는 부분 -->
			<div align="center">
				<c:set var="pageNum" value="<%= pageNum %>" />
				<c:forEach var="i" begin="1" end="<%= total_page %>">
					<a href='<c:url value="./BoardListAction.do?pageNum=${i}"/>'>
						<c:choose>
							<c:when test="${pageNum==i}">
								<font color='4C5317'><b>[${i}]</b></font>
							</c:when>
							<c:otherwise>
								<font color='4C5317'>[${i}]</font>
							</c:otherwise>
						</c:choose>
					</a>
				</c:forEach>
			</div>
			<!-- 검색 페이지 코드 -->
			<div align="left">
				<table>
					<tr>
						<td width="100%" align="left">&nbsp;&nbsp;
							<select name="items" class="txt">
								<option value="subject">제목</option>
								<option value="content">본문</option>
								<option value="name">글쓴이</option>
							</select>
							<input name="text" type="text" />
							<input type="submit" id="btnAdd" class="btn btn-primary btn-sm" value="검색"/>
						</td>
						<td width="100%" align="left">
							<!-- 로그인이 된 회원들만 글쓰기가 되도록 하기 위해서 checkForm()함수 사용-->
							<h2><a href="#" onclick="checkForm()" class="badge badge-primary">글쓰기</a></h2>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<hr>
	</div>
	<jsp:include page="../footer.jsp" />
</body>
</html>