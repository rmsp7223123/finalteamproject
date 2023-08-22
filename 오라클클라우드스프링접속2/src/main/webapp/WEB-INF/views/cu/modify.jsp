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
						<input type="text" class="form-control" name="member_phone" value="${vo.member_phone }">
					</div>
				</div>
			</td>
		</tr>
	</table>
	
	<div class="btn-toolbar my-3 gap-2 justify-content-center">
		<button class="btn btn-warning">저장</button>
		<button class="btn btn-online-primary" type="button" onclick="history.go(-1)">취소</button>
	</div>
	
	</form>
	
	
	
</body>
</html>