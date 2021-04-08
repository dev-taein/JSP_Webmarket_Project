<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String name = (String) request.getAttribute("name");
	System.out.println("writeForm.jsp진입");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>게시글 등록</title>
</head>
<script type="text/javascript">
	function checkForm() {
		if(!document.newWrite.subject.value){
			alert("제목을 입력하세요.");
			return false;
		}
		if(!document.newWrite.content.value){
			alert("내용을 입력하세요.");
			return false;
		}
	}
</script>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">게시글 작성</h1>
		</div>
	</div>
	
	<div class="container">
		<form name="newWrite" action="./BoardWriteAction.do" class="form-horizontal" method="post" 
				onsubmit="return checkForm()">
			<input name="id" type="hidden" class="form-control" value="${sessionId}" />
			
			<!-- 게시글의 작성자 이름을 표시 -->
			<div class="form-group row">
				<label class="col-sm-2 control-label">이름</label>
				<div class="col-sm-3">
					<input name="name" type="text" class="form-control" value="${name}" readonly/>
				</div>
			</div>
			
			<!-- 게시글의 제목을 표시 -->
			<div class="form-group row">
				<label class="col-sm-2 control-label">제목</label>
				<div class="col-sm-5">
					<input name="subject" type="text" class="form-control" placeholder="제목을 입력하세요." />
				</div>
			</div>
			
			<!-- 게시글의 내용을 표시 -->
			<div class="form-group row">
			<label class="col-sm-2 control-label">내용</label>
				<div class="col-sm-8">
					<textarea name="content" cols="50" rows="5" class="form-control" 
								placeholder="내용을 입력하세요."></textarea>
				</div>
			</div>
			
			<div class="form-group-row">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="submit" class="btn btn-primary" value="등록">
					<input type="reset" class="btn btn-danger" value="취소">
				</div>
			</div>
		</form>
		<hr>
	</div>
<jsp:include page="../footer.jsp" />
</body>
</html>