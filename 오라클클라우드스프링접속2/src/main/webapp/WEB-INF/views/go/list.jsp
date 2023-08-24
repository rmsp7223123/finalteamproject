<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="list" method="post">
	<input type="hidden" name="curPage" value="1">
	
	<div class="row mb-3 justify-content-between">
		<div class="col-auto">
		<div class="input-group">
			<select name="search" class="form-select">
				<option value="all" ${page.search eq 'all' ? 'selected' : '' }>전체</option>
				<option value="member_id" <c:if test="${page.search eq 'member_id'}">selected</c:if> >아이디</option>
				<option value="member_name" ${page.search eq 'member_name' ? 'selected' : '' }>이름</option>
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
	</colgroup>
		<tr>
		<th>알람번호</th>
			<th>이름</th>
			<th>알람 시간</th>
			<th>아이디</th>
			<th>보호자 이름</th>
			<th>보호자 번호</th>
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
			<td>${vo.member_name }</td>
			<td>${vo.alarm_time }</td>
			<td>${vo.ephone_name }</td>
			<td>${vo.ephone_phone }</td>
		</tr>
		</c:forEach>
	</table>
	
	</form>
	
		
	<jsp:include page="/WEB-INF/views/include/page.jsp"/>
</body>
</html>