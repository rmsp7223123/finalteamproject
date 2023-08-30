<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

  <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
  <style type="text/css">
  	.ui-datepicker table tbody tr, .ui-datepicker table thead tr {
  		height: initial;
  	}
  </style>
</head>
<body>
	<h3 class="my-4">정보수정</h3>
	<form action="update" method="post">
	<input type="hidden" name="member_id" value="${vo.member_id }">
	<input type="hidden" name="curPage" value="${page.curPage }">
	<input type="hidden" name="search" value="${page.search }">
	<input type="hidden" name="keyword" value="${page.keyword }">
	<table class="tb-row">
		<colgroup>
			<col width="180px">
		</colgroup>
		<tr>
			<th>이름</th>
			<td>
				<div class="row">
					<div class="col-auto">
						<input type="text" class="form-control" name="member_name" value="${vo.member_name }">
					</div>
				</div>
			</td>
		</tr>
		<tr><th>아이디</th>
			<td>${vo.member_id }</td>
		</tr>
		<tr><th>닉네임</th>
			<td><div class="row input-check align-items-center">
					<div class="col-auto">
						<input type='text' value="${vo.member_nickname}" name="member_nickname" class="check-item check-nickname form-control checked-item" maxlength="15">
					</div>
					<div class="col-auto nickname-check d-none">
						<a class="btn btn-secondary btn-sm" id="btn-member_nickname">
							<i class="fs-4 fa-regular fa-circle-check me-2"></i>중복확인
						</a>
					</div>
					<div class="desc nickname-check d-none"></div>
				</div>
			</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>
				<div class="row">
					<div class="col-auto">
						<input type="text" class="date form-control" id="datepicker" name="member_birth" value="${vo.member_birth}">
					</div>
				</div>
			</td>
		</tr>
		<tr><th>성별</th>
			<td><div class="row">
					<div class="col-auto">
						<div class="form-check form-check-inline">
						<label>
							<input class="form-check-input" type="radio" name="member_gender" value="남" 
							${vo.member_gender eq '남' ? 'checked' : ''} }>   남	
						</label>
						</div>
						<div class="form-check form-check-inline">
						<label>
							<input class="form-check-input" type="radio" name="member_gender" value="여"
							${vo.member_gender eq '여' ? 'checked' : ''}>   여	
						</label>
						</div>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td>
				<div class="row input-check">
					<div class="col-auto">
						<input type="text" class="form-control check-item" name="member_phone" 
						value="${vo.member_phone}">
					</div>
				<div class="col-auto ms-3 mt-2_5">- 제외</div>
				<div class="desc col-auto ms-3 mt-2_5"></div>
				</div>
			</td>
		</tr>
		
			<tr><th>주소</th>
		<td><div class="row align-items-center">
				<div class="col-auto pe-0">
					<a class="btn btn-secondary btn-sm" id="btn-post">
						<i class="fa-solid fa-magnifying-glass me-2"></i>주소찾기
					</a>
				</div>
			</div>
<!-- 			<div class="row mt-2"> -->
				<div class="col-xl-7">
					<input type="text" name="address" class="form-control"  readonly value="${vo.member_address}">			
				</div>
				<div class="col-xl">
					<input type="text" name="address" class="form-control">			
				</div>
<!-- 			</div> -->
		</td>
	</tr>
	</table>
	
	</form>
	
	
	
	<div class="btn-toolbar my-3 gap-2 justify-content-center">
		<button class="btn btn-warning" id="btn-join">저장</button>
		<button class="btn btn-online-primary" type="button" onclick="history.go(-1)">취소</button>
	</div>
	
	
<!--   <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script> -->
  <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
	<script src="<c:url value='/js/member.js?${now}'/>"></script>
  <script src="<c:url value='/js/scripts.js'/>"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
        
	<script>
	
$(function(){
	nickname();
})
function nickname(){
	if("${vo.member_nickname}"==$("[name=member_nickname]").val()){
		$(".check-nickname").addClass("checked-item") //중복확인했음 클래스 삭제
		$(".nickname-check").addClass('d-none');
	}else {
		$(".check-nickname").removeClass("checked-item") //중복확인했음 클래스 삭제
		$(".nickname-check").removeClass('d-none');
		
	}
}
	
//회원가입 버튼 클릭시
$('#btn-join').on('click', function(){
	
	if( $("[name=member_name]").val().trim() =="" ){
		alert("이름을 입력하세요");
		$("[name=member_name]").focus();
		$("[name=member_name]").val("")
		return;
	}
	
	var _id = $("[name=member_nickname]");
	//중복확인 한 경우
	if( _id.hasClass("checked-item") ){
		//사용중인 아이디인 경우 회원가입 불가
		if( _id.closest(".input-check").find(".desc").hasClass("text-danger") ){
			alert("정보 수정 실패\n" + member.nickname.unUsable.desc)
			_id.focus()
			return;
		}
	}else{
	//중복확인 하지 않은 경우
		if(  invalidStatus( _id ) ) return;
		else{
			//입력은 유효하나 중복확인하지 않은 경우
			alert("정보 수정 실패\n" + member.nickname.valid.desc)
			_id.focus()
			return;
		}
	}

	if(  invalidStatus( $("[name=member_nickname]") ) ) return;
	if(  invalidStatus( $("[name=member_phone]") ) ) return;
	
	$('form').submit()
})



//체크항목에 입력을 유효하게 했는지 확인
function invalidStatus( tag ){
	var status = member.tagStatus( tag )
	if( status.is )
		return false;
	else{
		alert('정보 수정 실패\n' + status.desc )
		tag.focus()
		return true;
	}	
}

//아이디중복확인 버튼 클릭시
$('#btn-member_nickname').on('click', function(){
	memberNicknameCheck();
})

// 아이디 중복확인 함수
function memberNicknameCheck(){
	var nickname = $('[name=member_nickname]');
	var status = member.tagStatus( nickname );
	if( status.is||("${vo.member_nickname}"==$("[name=member_nickname]").val())){
		$.ajax({
			url: 'nicknameCheck',
			data: { member_nickname: nickname.val() }
		}).done(function( response ){
			console.log( response )
			status = response ? member.nickname.usable : member.nickname.unUsable;
			nickname.closest('.input-check').find('.desc').text( status.desc )
				.removeClass('text-success text-danger')
				.addClass( status.is ? 'text-success' : 'text-danger')
			nickname.addClass("checked-item");  //중복확인 했음을 클래스로 지정
		})
		
	}else{
		alert( status.desc );
		nickname.focus();
	}
}

//체크대상 항목에 키보드입력시 처리
$('.check-item').on('keyup', function( e ){
	$(this).removeClass("checked-item") //중복확인했음 클래스 삭제
	if($(this).attr("name")=="member_nickname"){
		nickname();
	}
	// 아이디에서 엔터시 중복확인처리하기
	if( $(this).attr("name")=="member_nickname" && e.keyCode==13 ){
		memberNicknameCheck();
	}else{
		member.showStatus( $(this) )
	}
})

// $('#btn-post').click(function(){
$('#btn-post').on('click', function(){
	new daum.Postcode({
        oncomplete: function(data) {
        	console.log(data)
//         	if( data.userSelectedType == "R" ){
//         		data.roadAddress
//         	}else{
//         		data.jibunAddress
//         	}
        	var address = data.userSelectedType == "R" ? data.roadAddress : data.jibunAddress;
        	if( data.buildingName !="" ) address += " ("+data.buildingName+")";
        	
        	$('[name=address]:eq(0)').val( address );
        	
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.
            // 예제를 참고하여 다양한 활용법을 확인해 보세요.
        }
    }).open();
})


$(function(){
	var today = new Date();
	//만 나이를 적용한다면:13년전 해의 오늘날짜 이전까지는 선택 가능 
	//var endDay = new Date(today.getFullYear()-13, today.getMonth(), today.getDate()-1);
	//년도: 13년전 해의 12월 31일까지는 선택 가능
	var endDay = new Date(today.getFullYear()-44, 11, 31);
	$('[name=member_birth]').datepicker('option', 'maxDate', endDay);
	
})

</script>
	
	
	
</body>
</html>