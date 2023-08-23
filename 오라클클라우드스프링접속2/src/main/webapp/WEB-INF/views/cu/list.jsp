<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3 class="my-4">회원목록</h3>
	
	<form action="list" method="post">
	<input type="hidden" name="curPage" value="1">
	
	<div class="row mb-3 justify-content-between">
		<div class="col-auto">
		<div class="input-group">
			<select name="search" class="form-select">
				<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
				<option value="member_id" <c:if test="${page.search eq 'member_id'}">selected</c:if> >아이디</option>
				<option value="member_name" ${page.search eq 'member_name' ? 'selected' : '' }>이름</option>
				<option value="member_nickname" ${page.search eq 'member_nickname' ? 'selected' : '' }>닉네임</option>
			</select>
			<input type="text" name="keyword" value="${page.keyword }" class="form-control">
			<button class="btn btn-warning px-3"><i class="fa-solid fa-magnifying-glass" style="color: #ffffff;"></i></button>
		</div>
		</div>
	</div>
	
	<table class="tb-list text-center">
	<colgroup>
		<col width="100px">
		<col width="150px">
		<col width="250px">
		<col width="250px">
		<col width="100px">
		<col width="200px">
		<col width="200px">
	</colgroup>
		<tr>
		<th>번호</th>
			<th>이름</th>
			<th>아이디</th>
			<th>닉네임</th>
			<th>성별</th>
			<th>생일</th>
			<th>전화번호</th>
		</tr>
		<!-- 회원정보가 없는 경우 -->
		<c:if test="${empty page.list }">
			<tr>
				<td colspan='7'>회원정보가 없습니다.</td>
			</tr>
		</c:if>
		<!-- 사원정보가 있는 경우 -->
		<c:forEach items="${page.list }" var="vo">
		<tr><td>${vo.no }</td>
			<td class="text-start">
				<a class="text-link" 
					href="info?id=${vo.member_id}&curPage=${page.curPage}&search=${page.search}&keyword=${page.keyword}">
					${vo.member_name }
				</a>
			</td>
			<td>${vo.member_id }</td>
			<td>${vo.member_nickname }</td>
			<td>${vo.member_gender }</td>
			<td>${vo.member_birth }</td>
			<td>${vo.member_phone }</td>
		</tr>
		</c:forEach>
	</table>
	
	</form>
	
		
	<jsp:include page="/WEB-INF/views/include/page.jsp"/>
	
</body>
</html>