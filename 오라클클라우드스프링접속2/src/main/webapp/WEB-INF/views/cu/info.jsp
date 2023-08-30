<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">회원정보</h3>
	<table class="tb-row my-3 mt-3" style="margin-left:auto;margin-right:auto; width: 800px;">
		<colgroup>
			<col width="180px">
			<col width="250px">
			<col width="200px">
		</colgroup>
		<tr>
			<th>이름</th>
			<td>${vo.member_name }</td>
			<c:if test="${! empty vo.member_profileimg }">
			<td rowspan="6">
				<div class="row">
					<div class="col-auto d-flex gap-3 align-items-center file-preview">
						<img style="max-height: 300px" src="${vo.member_profileimg }" />
					</div>
				</div>
			</td>
			</c:if>
		</tr>
		<tr>
			<th>아이디</th>
			<td>${vo.member_id }</td>
		</tr>
		<tr>
			<th>닉네임</th>
			<td>${vo.member_nickname }</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>${vo.member_birth }</td>
		</tr>
		<tr>
			<th>성별</th>
			<td>${vo.member_gender }</td>
		</tr>
		<tr>
			<th>전화번호</th>
			<td>${vo.member_phone }</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${vo.member_address }</td>
		</tr>
	</table>
	
	<c:set var="params" value="curPage=${page.curPage}&search=${page.search}&keyword=${page.keyword }"/>
	<div class="d-flex btn-toolbar my-3 justify-content-center mt-4 gap-0">
		<button class="btn btn-warning" onclick="location='list?${params}'">회원목록</button>
		<button class="btn btn-warning" onclick="location='modify?id=${vo.member_id}&${params}'">정보수정</button>
		<button class="btn btn-warning"
			onclick="if(confirm('회원 [${vo.member_id }] 회원정보를 삭제하시겠습니까?')){location='delete?id=${vo.member_id}&${params}'}">회원삭제
		</button>
	</div>
	<jsp:include page="/WEB-INF/views/include/modal_image.jsp"/>
	
</body>
</html>