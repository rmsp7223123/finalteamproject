<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.input-group .form-select { flex:initial; width: 130px }
table { table-layout: fixed; }
</style>
</head>
<body>
<h3 class="my-4">고객센터</h3>

<form method="post" action="list">
<div class="row justify-content-between mb-3">
	<div class="col-auto">
		<div class="input-group">
			<select class="form-select" name="search">
				<option value="s1" ${page.search eq 's1' ? 'selected' : ''}>전체</option>
				<option value="s2" ${page.search eq 's2' ? 'selected' : ''}>제목</option>
				<option value="s3" ${page.search eq 's3' ? 'selected' : ''}>내용</option>
				<option value="s4" ${page.search eq 's4' ? 'selected' : ''}>작성자</option>
				<option value="s5" ${page.search eq 's5' ? 'selected' : ''}>제목+내용</option>
			</select>
			<input type="text" name="keyword" class="form-control" value="${page.keyword}">
			<button class="btn btn-warning px-3">
				<i class="fa-solid fa-magnifying-glass"></i>
			</button>
		</div>
	</div>

</div>
<input type="hidden" name="curPage" value="1">
<input type="hidden" name="csboard_id" value="10">
</form>

<table class="tb-list">
<colgroup><col width="100px"><col><col width="100px"><col width="150px"></colgroup>
<tr><th>번호</th>
	<th>제목</th>
	<th>작성자</th>
	<th>최종수정일</th>
</tr> 
<c:if test="${empty page.list}">
<tr><td colspan="5">건의사항이 없습니다</td></tr>
</c:if>
<c:forEach items="${page.list}" var="vo">
<tr><td>${vo.no }</td>
	<td class="text-start">
		${vo.comment_exist eq 'Y' ? '<i class="fa-brands fa-replyd"></i>' : ''}
	<a class="text-link" onclick="javascript:info(${vo.csboard_id})">${vo.csboard_title }</a></td>
	<td>${vo.nickname }</td>
	<td>${vo.csboard_writedate }</td>
</tr>
</c:forEach>
</table>

<jsp:include page="/WEB-INF/views/include/page.jsp"/>

<script>
//상세정보화면 요청
function info( csboard_id ){
	$('[name=csboard_id]').val(csboard_id )
	$('[name=curPage]').val( ${page.curPage} )
	$('form').attr('action', 'info').submit()
}

</script>

</body>
</html>