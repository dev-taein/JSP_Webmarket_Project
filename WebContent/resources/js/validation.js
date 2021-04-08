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
	
	//상품 가격 check
	if(unitPrice.value.length == 0 || isNaN(unitPrice.value)){
		alert("[가격]\n숫자만 입력해주세요.")
		unitPrice.select();
		unitPrice.focus();
		return false;
	}
	
	if(unitPrice.value < 0 ){
		alert("[가격]\n음수를 입력할 수 없습니다.")
		unitPrice.select();
		unitPrice.focus();
		return false;
	}
	
	//수량 check
	if(isNaN(unitsInStock.value)){
		alert("[수량]\n숫자만 입력해주세요.")
		unitsInStock.select();
		unitsInStock.focus();
		return false;
	}
	if(unitsInStock.value < 0 ){
		alert("[수량]\n음수를 입력할 수 없습니다.")
		unitsInStock.select();
		unitsInStock.focus();
		return false;
	}
	
	document.newProduct.submit();
	
	
	//regExp=정규 표현식 e=유효성 검사, msg=오류발생시 메시지 
	function check(regExp, e, msg){
		//정규표현식에 적합하다면 true 반환
		if(regExp.test(e.value)){
			return true;
		}
		alert(msg);
		e.select();
		e.focus();
		return false;
	}
	
}

