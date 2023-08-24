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
	<table class="tb-list text-center">
		<colgroup>
			<col width="150px">
			<col width="250px">
			<col width="250px">
			<col width="100px">
			<col width="200px">
		</colgroup>
		<tr>
			<th>아이디</th>
			<th>알람 시간</th>
			<th>이름</th>
			<th>보호자 이름</th>
			<th>보호자 번호</th>
		</tr>
		<tr>
			<td>${vo.member_id }</td>
			<td>${vo.alarm_time }</td>
			<td>${vo.member_name }</td>
			<td>${vo.ephone_name }</td>
			<td>${vo.ephone_phone }</td>
		</tr>
	</table>
</body>
</html>