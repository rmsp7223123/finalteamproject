<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>우동탑</title>
<link rel="icon" href="<c:url value = '/theme/images/logo.png'/>"
	type="image/x-icon">
</head>
<body>
<div class="container" style = "width:100%;">
		<tiles:insertAttribute name ="container"/>
	</div>
</body>
</html>