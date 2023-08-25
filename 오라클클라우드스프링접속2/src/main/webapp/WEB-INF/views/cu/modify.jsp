<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">정보수정</h3>
	<form action="update" method="post">
	<input type="hidden" name="member_id" value="${vo.member_id }">
<!-- 	<input type="hidden" name="filename"> -->
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
						<input type='text' value="${vo.member_nickname}" name="member_nickname" class="check-item form-control">
					</div>
					<div class="col-auto">
						<a class="btn btn-secondary btn-sm" id="btn-member_nickname">
							<i class="fs-4 fa-regular fa-circle-check me-2"></i>중복확인
						</a>
					</div>
					<div class="col-auto">숫자, 영문 소문자 포함</div>
					<div class="desc"></div>
				</div>
			</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>
				<div class="row">
					<div class="col-auto">
						<input type="text" class="form-control date" name="member_birth" value="${vo.member_birth }">
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
							${vo.member_gender eq '남' ? 'checked' : ''} }>남	
						</label>
						</div>
						<div class="form-check form-check-inline">
						<label>
							<input class="form-check-input" type="radio" name="member_gender" value="여"
							${vo.member_gender eq '여' ? 'checked' : ''}>여	
						</label>
						</div>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td>
				<div class="row">
					<div class="col-auto">
						<input type="text" class="form-control" name="member_phone" 
						value="${vo.member_phone}">
<%-- 						value="${vo.member_phone.replace(vo.member_phone.substring(3), vo.member_phone.substring(3)+'-') }"> --%>
					</div>
				</div>
			</td>
		</tr>
	</table>
	
	</form>
	
	
	
	<div class="btn-toolbar my-3 gap-2 justify-content-center">
		<button class="btn btn-warning" id="btn-join">저장</button>
		<button class="btn btn-online-primary" type="button" onclick="history.go(-1)">취소</button>
	</div>
	
	
	<script src="<c:url value='/js/member.js?${now}'/>"></script>
	<script src="<c:url value='/js/common.js?${now}'/>"></script>
	
	<script>
	

	
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
			alert("회원가입 불가\n" + member.member_nickname.unUsable.desc)
			_id.focus()
			return;
		}
	}else{
	//중복확인 하지 않은 경우
		if(  invalidStatus( _id ) ) return;
		else{
			//입력은 유효하나 중복확인하지 않은 경우
			alert("회원가입 불가\n" + member.member_nickname.valid.desc)
			_id.focus()
			return;
		}
	}
	
	if(  invalidStatus( $("[name=member_name]") ) ) return;
	if(  invalidStatus( $("[name=member_nickname]") ) ) return;
	if(  invalidStatus( $("[name=member_phone]") ) ) return;
	if(  invalidStatus( $("[name=member_birth]") ) ) return;
	
	singleFileUpload();
	$('form').submit()
})



//체크항목에 입력을 유효하게 했는지 확인
function invalidStatus( tag ){
	var status = member.tagStatus( tag )
	if( status.is )
		return false;
	else{
		alert('회원가입 불가\n' + status.desc )
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
	var _id = $('[name=member_nickname]');
	var status = member.tagStatus( _id );
	if( status.is ){
		$.ajax({
			url: 'memberNicknameCheck',
			data: { userid: _id.val() }
		}).done(function( response ){
			console.log( response )
			status = response ? member.userid.usable : member.userid.unUsable;
			_id.closest('.input-check').find('.desc').text( status.desc )
				.removeClass('text-success text-danger')
				.addClass( status.is ? 'text-success' : 'text-danger')
			_id.addClass("checked-item");  //중복확인 했음을 클래스로 지정
		})
		
	}else{
		alert('아이디 중복확인 불필요\n' + status.desc );
		_id.focus();
	}
}

//체크대상 항목에 키보드입력시 처리
$('.check-item').on('keyup', function( e ){
	$(this).removeClass("checked-item") //중복확인했음 클래스 삭제
	// 아이디에서 엔터시 중복확인처리하기
	if( $(this).attr("name")=="userid" && e.keyCode==13 ){
		memberNicknameCheck();
	}else{
		member.showStatus( $(this) )
	}
})


$(function(){
	var today = new Date();
	//만 나이를 적용한다면:13년전 해의 오늘날짜 이전까지는 선택 가능 
	//var endDay = new Date(today.getFullYear()-13, today.getMonth(), today.getDate()-1);
	//년도: 13년전 해의 12월 31일까지는 선택 가능
	var endDay = new Date(today.getFullYear()-13, 11, 31);
	$('[name=birth]').datepicker('option', 'maxDate', endDay);


})




</script>
	
	
	
</body>
</html>