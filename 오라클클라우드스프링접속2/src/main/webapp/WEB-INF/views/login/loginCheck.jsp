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
	<input type="hidden" name="url" value="<c:url value ="/"/>" />
	<form method="post"></form>

	<script>
	
	if(${admin} != 1) {
		alert('없는 계정입니다.')
	} else {		
	$("[name=url]").val("home");
	}
	$("form").attr("action", $("[name=url]").val());
	$("form").submit();
</script>
</body>
</html>