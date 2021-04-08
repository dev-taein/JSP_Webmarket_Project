<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>배송 정보</title>
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">배송 정보</h1>
		</div>
	</div>
	
	<div class="container">
		<form action="./processShippingInfo.jsp" class="form-horizontal" method="POST">
			<input type="hidden" name="cartId" value="<%= request.getParameter("cartId") %>" />
			<!-- 성명을 입력 받는 부분 -->
			<div class="form-group row">
				<label class="col-sm-2">성명</label>
				<div class="col-sm-3">
					<input type="text" name="name" class="form-control" placeholder="성명"/>
				</div>
			</div>
			<!-- 배송일을 입력 받는 부분 -->
			<div class="form-group row">
				<label class="col-sm-2">배송일</label>
				<div class="col-sm-3">
					<input type="text" name="shippingDate" class="form-control" placeholder="yyyy/mm/dd"/> 
				</div>
			</div>
			<!-- 국가명을 입력 받는 부분 -->
			<div class="form-group row">
				<label class="col-sm-2">국가명</label>
				<div class="col-sm-3">
					<input type="text" name="country" class="form-control" placeholder="국가명"/> 
				</div>
			</div>
			<!-- 우편번호를 입력 받는 부분 -->
			<div class="form-group row">
				<label class="col-sm-2">우편번호</label>
				<div class="col-sm-3">
					<input type="text" name="zipCode" class="form-control" placeholder="우편번호"/> 
				</div>
			</div>
			<!-- 주소를 입력 받는 부분 -->
			<div class="form-group row">
				<label class="col-sm-2">주소</label>
				<div class="col-sm-3">
					<input type="text" name="addressName" class="form-control" placeholder="시/도/구"/> 
				</div>
			</div>
			<!-- 전 페이지 이동, 상품 등록, 취소 버튼 -->
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
					<a href="./cart.jsp?=<%= request.getParameter("cartId") %>" class="btn btn-secondary" role="button">이전</a>
					<input type="submit" class="btn btn-primary" value="등록" />
					<a href="./checkOutCancelled.jsp" class="btn btn-secondary" role="button">취소</a>
				</div>
			</div>
		</form>
	</div>
	<hr>
	<jsp:include page="../footer.jsp" />
</body>
</html>