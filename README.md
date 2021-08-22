# JSP_Webmarket_Project (개인 프로젝트)
## 목적 ##
+ JSP 개발 경험 만들기
+ 웹 사이트 개발 시 필수이자 기본
+ 실력 향상
------------
# 개발 환경 및 사용한 언어
> Front-End
+ HTML
+ CSS
+ JavaScript
+ Bootstrap 4
> Back-End
+ Java 1.8
+ JSP
+ MySQL 8.0
+ Tomcat 9.0
------------
# 개발 기간 - 2021/03/08 ~ 2021/03/23 총 15일 
> 1주차 (03/08 ~ 03/12) – 시작 페이지, 상품DB, 상품CRU
+ 03/08 개발환경 설정 및 시작페이지 구현
+ 03/09 상품DB 생성, 상품 목록, 상품 상세 정보 페이지 구현
+ 03/10 상품 등록 및 이미지 등록 유효성 검사 구현
+ 03/11 장바구니 페이지 구현
+ 03/12 주문처리 페이지 구현
> 2주차 (03/13 ~ 03/18) – 예외 페이지, 로그기록, 사용자 페이지
+ 03/13 예외처리 페이지 구현
+ 03/14 로그 기록하기 구현
+ 03/15 다국어 처리, 보안처리 구현
+ 03/16 사용자 DB 생성, 로그인 페이지 구현
+ 03/17 회원가입 및 회원 수정 페이지 구현
> 3주차 (03/19 ~ 03/24) – 게시판 MVC Model 1
+ 03/19 게시판 DB 생성, 게시판 목록 페이지 구현
+ 03/20 게시판 검색 기능 구현
+ 03/21 게시판 수정, 삭제 구현
+ 03/22 조회수 기능 구현
+ 03/23 코드 재정리 및 마무리
------------
# 설계구조 및 구축 흐름도
![github3](https://user-images.githubusercontent.com/77142806/114081913-1f4e6c80-98e8-11eb-8ba8-10227ecb7f05.PNG)
![github4](https://user-images.githubusercontent.com/77142806/114081914-1f4e6c80-98e8-11eb-9cab-6e90e503b431.PNG)
------------
# Front-End 작동 화면 미리 보기
#### 상단 헤더 메뉴바 기능
> 일반 사용자가 로그인 했을 경우와 어드민 관리자 계정으로 로그인 했을 경우 상단에 상품 등록, 수정, 삭제 메뉴를 추가함(JSTL태그)
+ 관리자 계정 
![menu1](https://user-images.githubusercontent.com/77142806/130346429-61d7eb0b-e512-41c8-8361-f0c811937a42.PNG)
+ 일반 사용자 계정
![menu2](https://user-images.githubusercontent.com/77142806/130346430-b8d036d8-6553-4eee-bac3-8a032b8b0078.PNG)
```
<c:choose>
	<c:when test="${empty sessionId}">
	<li class="nav-item"><a class="nav-link" href="<c:url value="/members/loginMember.jsp" />">로그인</a></li>
	<li class="nav-item"><a class="nav-link" href="<c:url value="/members/addMember.jsp" />">회원가입</a></li>
	</c:when>
	<c:otherwise>
		<li style="padding-top: 7px; color: white"><%= sessionId %>[님]</li>
		<li class="nav-item"><a class="nav-link" href="<c:url value="/members/logoutMember.jsp" />">로그아웃</a></li>
		<li class="nav-item"><a class="nav-link" href="<c:url value="/members/updateMember.jsp" />">회원 수정</a></li>
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
		li class="nav-item"><a href="${pageContext.request.contextPath}/product/editProduct.jsp?edit=delete" class="nav-link">상품 삭제</a></li>
	</c:otherwise>
</c:choose>
<li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/BoardListAction.do?pageNum=1">게시판</a></li>
```
#### index 페이지, Login 및 회원가입 페이지 
![front1](https://user-images.githubusercontent.com/77142806/130345353-dcf90fd6-6325-4c22-bfc9-24efd3b22eac.gif)
#### 상품 목록, 주문 페이지
![front2](https://user-images.githubusercontent.com/77142806/130345564-ff0965e9-7a26-4ca2-9643-373b247c33f5.gif)
#### 게시판 목록 페이지
> 로그인을 하지 않았다면 게시글을 읽지 못하도록 설정
```
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
```
![front3](https://user-images.githubusercontent.com/77142806/130345571-1c26de71-e8fa-422e-b3b2-f0942a91cd36.gif)
#### 로그인, 로그아웃 페이지
![front4](https://user-images.githubusercontent.com/77142806/130345885-9a623c97-2db0-4d48-bdc0-483b617b4ac8.gif)
#### 회원 정보, 상품 등록 페이지
> 상품을 등록 유효성 검사
```
/* 상품 등록 유효성 검사 */
function checkAddProduct(){
	var productId = document.getElementById("productId");
	var pname = document.getElementById("pname");
	var unitPrice = document.getElementById("unitPrice");
	var unitsInStock = document.getElementById("unitsInStock");
	
	//상품 ID check
	if(!check(/^P[0-9]{4,11}$/, productId, 
		"[상품 코드]\nP와 숫자를 조합하여 5~12자까지 입력하세요.\n" + "반드시 첫 글자는 P로 시작해주세요.")){
			return false;
		}
		
	//상품명 check
	if(pname.value.length < 4 || pname.value.length > 12){
		alert("[상품명]\n최소 4자에서 최대 11자까지 입력해주세요.");
		name.select();
		name.focus();
		return false;
	}
 ```
![front5](https://user-images.githubusercontent.com/77142806/130346109-dd85fc23-bcb9-42ca-84f9-e6123f281261.gif)
#### 상품 수정, 삭제 페이지
![front6](https://user-images.githubusercontent.com/77142806/130345881-938b247f-3ace-4df9-b317-6423097e2232.gif)
#### 로그인 성공 시 게시글  읽기 페이지
![front7](https://user-images.githubusercontent.com/77142806/130345882-bf8e19f7-b501-44ab-b98f-195602ee088b.gif)
------------
# Back-End 기능
> 상품 등록
> 상품 수정
> 상품 삭제
> 

------------
# 주요 이슈
------------
# 느낀점
------------

 
