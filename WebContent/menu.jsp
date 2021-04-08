<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	request.setCharacterEncoding("UTF-8"); 

	//로그인 여부를 판단(세션이용)
	String sessionId = (String)session.getAttribute("sessionId");
%>
<!-- 상단 네비게이션 바 생성 -->
<nav class="navbar navbar-expand navbar-dark bg-dark">
<!-- container 부터 내용을 표시 -->
	<div class="container">
	<!-- 네비게이션 헤더(머리) 부분 -->
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/welcome.jsp">HOME</a>
		</div>
		<div>
			<ul class="navbar-nav mr-auto">
			<!-- sessionId가 null일 시 로그인 -->
			<!-- 로그인이  되어 있다면 -->
			<!-- c:choose = core 태그 안에는 주석을 만들 수 없다 -->
				<c:choose>
					<c:when test="${empty sessionId}">
						<li class="nav-item"><a class="nav-link" 
						href="<c:url value="/members/loginMember.jsp" />">로그인</a></li>
						<li class="nav-item"><a class="nav-link" 
						href="<c:url value="/members/addMember.jsp" />">회원가입</a></li>
					</c:when>
					<c:otherwise>
						<li style="padding-top: 7px; color: white"><%= sessionId %>[님]</li>
						<li class="nav-item"><a class="nav-link" 
						href="<c:url value="/members/logoutMember.jsp" />">로그아웃</a></li>
						<li class="nav-item"><a class="nav-link" 
						href="<c:url value="/members/updateMember.jsp" />">회원 수정</a></li>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${sessionId ne 'admin'}">
						<li class="nav-item"><a href="${pageContext.request.contextPath}/product/products.jsp" class="nav-link">상품 목록</a></li>
					</c:when>
					<c:otherwise>
						<li class="nav-item"><a href="${pageContext.request.contextPath}/product/products.jsp" class="nav-link">상품 목록</a></li>
						<li class="nav-item"><a href="${pageContext.request.contextPath}/product/addProduct.jsp" class="nav-link">상품 등록</a></li>
						<li class="nav-item"><a href="${pageContext.request.contextPath}/product/editProduct.jsp?edit=update" class="nav-link">상품 수정</a></li>
						<li class="nav-item"><a href="${pageContext.request.contextPath}/product/editProduct.jsp?edit=delete" class="nav-link">상품 삭제</a></li>
					</c:otherwise>
				</c:choose>
				<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/BoardListAction.do?pageNum=1">게시판</a></li>
			</ul>
		</div>
	</div>
</nav>