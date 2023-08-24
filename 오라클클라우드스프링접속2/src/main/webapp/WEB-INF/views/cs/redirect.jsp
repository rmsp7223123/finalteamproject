<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<form action="<c:url value='/${url}'/>" method="post">
<input type="hidden" name="curPage" value="${page.curPage }">
<input type="hidden" name="search" value="${page.search }">
<input type="hidden" name="keyword" value="${page.keyword }">
<input type="hidden" name="viewType" value="${page.viewType }">
<input type="hidden" name="pageList" value="${page.pageList }">
<input type="hidden" name="board_id" value="${board_id}">
<input type="hidden" name="comment_exist" value="${page.comment_exist}">
</form>
<script>
$('form').submit()
</script>