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

	<form method="post" action="list" class="mt-4">

<div class="d-flex mt-3 mb-3 justify-content-between">

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
				<i class="fa-solid fa-magnifying-glass" style="color: #ffffff;"></i>
			</button>
		</div>
	</div>



		<input type="hidden" name="curPage" value="1">
		<div class="mt-3">
		<input type="checkbox" name="comment_exist" id="checkbox_comment" 
		${page.comment_exist eq 'Y' ? 'checked' : ''}
		onclick='checkbox()'>   답변필요
		</div>

</div>
<input type="hidden" name="curPage" value="1">
<input type="hidden" name="csboard_id">
<%-- <input type="hidden" name="comment_exist" value="${page.comment_exist }"> --%>
		</form>

<table class="tb-list">
<colgroup><col width="100px"><col><col width="100px"><col width="150px"></colgroup>
<tr><th>번호</th>
	<th>제목</th>
	<th>작성자</th>
	<th>최종수정일</th>
</tr> 
<c:if test="${empty page.list}">
<tr><td colspan="4">건의사항이 없습니다</td></tr>
</c:if>
<c:forEach items="${page.list}" var="vo">
<tr><td>${vo.no }</td>
	<td class="text-start">
		${vo.comment_exist eq 'Y' ? ''
		: '<i class="fa-solid fa-q"></i>'}
	<a class="text-link" href="javascript:info(${vo.csboard_id})">${vo.csboard_title }</a></td>
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
	$('[name=comment_exist]').val(($("#checkbox_comment").prop("checked")) ? 'Y' : 'N') 
	$('form').attr('action', 'info').submit()
}

function checkbox(){

//     var newCommentExist = ($("#checkbox_comment").prop("checked")) ? 'Y' : 'N';
//     var newUrl = "list?comment_exist=" + newCommentExist + "&pagelist="+"${page.pageList }"+
//     		"&curPage="+"${page.curPage}"+"&search=" + "${page.search}" + "&keyword=" + "${page.keyword}";
//     window.location.href = newUrl;
	
 	if ($("#checkbox_comment").prop("checked")) {
		$('[name=comment_exist]').val('Y'); // 'Y'로 변경
	} else {
		$('[name=comment_exist]').val('N'); // 'N'로 변경
	} 
	
/* 	$('[name=search]').val("${page.search}");
	$('[name=keyword]').val("${page.keyword}");
	$('[name=curPage]').val("${page.curPage}");  */
	$('form').attr('action', 'list').submit();

}


</script>

</body>
</html>