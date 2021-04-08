<%@page import="mvc.model.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% 
	BoardDTO notice = (BoardDTO)request.getAttribute("board");
	int num = (Integer)request.getAttribute("num");
	int nowpage = (Integer)request.getAttribute("pageNum");
	
	String sessionId = (String)session.getAttribute("sessionId");
	System.out.println(sessionId); 
	String userId = notice.getId();
	System.out.println(userId);
	
//	System.out.println(notice.getName());
//	System.out.println(num);
//	System.out.println(nowpage);
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/CSS/bootstrap.min.css" />
<meta charset="UTF-8">
<title>글 상세 보기</title>
<script type="text/javascript">
	function checkdelete() {
		if(confirm("정말로 삭제하시겠습니까?") == true){
			location.href="./BoardDeleteAction.do?num=<%= notice.getNum() %>&pageNum=<%=nowpage%>";
		} 
		else 
			return;
		
	}
</script>
</head>
<body>
	<jsp:include page="../menu.jsp" />
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">게시글</h1>
		</div>
	</div>
	
	<div class="container">
		<form name="newUpdate" action="BoardUpdateAction.do?num=<%= notice.getNum() %>&pageNum=<%=nowpage%>"
				class="form-horizontal" method="post">
			<!-- DB에 저장되어 있는 이름 값을 출력 -->
			<div class="form-group row">
				<label class="col-sm-2 control-label">이름</label>
				<div class="col-sm-3">
					<input name="name" class="form-control" value="<%= notice.getName()%> " readonly>
				</div>
			</div>
			
			
			<!-- DB에 저장되어져 있는 제목을 출력하는 부분 -->
			  <div class="form-group row">
			  	    <label class="col-sm-2 control-label">제목</label>
			  		<div class="col-sm-5">
			  		<% 
			  			//게시글 작성자 이거나 관리자(admin)일 경우 수정 가능
			  			if(sessionId.equals(userId) || sessionId.equals("admin")) {			  			
			  		%>	
			  			<input name="subject" class="form-control" value="<%=notice.getSubject() %>">
			  		<%
			  			}
			  			//게시글 작성자가 아니라면..읽기 전용으로 하는 코드
			  			else {
			  		%>		  			
	 			  		<input name="subject" class="form-control" value="<%=notice.getSubject() %>" readonly="readonly"> 
	 			  	<% 
			  			}
	 			  	%>				  			
			  		</div>			  
			  </div>
			 
			  <div class="form-group row">
			  	    <label class="col-sm-2 control-label">내용</label>
			  		<!-- word-break속성은 단어의 분리를 결정하여 줄 바꿈에 대한 속성을 지정할 때 사용한다. -->
			  		<!-- break-all값은 영어 단어나 공백이나 하이픈이 아닌 두 문자사이에서 분리를 하고자 할때 사용 -->
			  		<div class="col-sm-8" style="word-break: break-all">
			  		
			  		<% 
			  			//게시글 작성자 이거나 관리자(admin)일 경우 수정 가능
			  			if(sessionId.equals(userId) || sessionId.equals("admin")) {			  			
			  		%>
			  			<textarea name="content" class="form-control" rows="5" cols="50"><%= notice.getContent() %></textarea>
			  		<% 
			  			}
			  			//게시글 작성자가 아니라면..읽기 전용으로 하는 코드
			  			else {
			  		%>		
			  			<textarea name="content" class="form-control" rows="5" cols="50" readonly="readonly"><%= notice.getContent() %></textarea>
			  		<% 
			  			}
			  		%>	
			  		</div>			  
			  </div>
	
			
			<!-- 게시글 작성자가 맞다면 수정, 삭제 가능 -->
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10">
				<% 
			  			//게시글 작성자 이거나 관리자(admin)일 경우 수정 가능
			  			if(sessionId.equals(userId) || sessionId.equals("admin")) {			  			
			  		%>
						<p><a href="./BoardDeleteAction.do?num=<%= notice.getNum() %>&pageNum=<%=nowpage%>"
						 		class="btn btn-danger" onclick="checkdelete()">삭제</a>
						 <input type="submit" class="btn btn-success" value="수정"> 
					<%
			  			}
			  			
			  		%>		 
					<a href="./BoardListAction.do?pageNum=<%=nowpage%>"
						 		class="btn btn-primary">목록</a>
				
				</div>
			</div>
		</form>
		<hr>
	</div>
<jsp:include page="../footer.jsp" />
</body>
</html>