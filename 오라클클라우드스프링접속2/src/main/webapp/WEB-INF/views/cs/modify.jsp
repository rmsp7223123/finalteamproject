<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3 class="my-4">건의사항 수정</h3>
<form method="post" action="update">
<table class="tb-row">
<colgroup><col width="180px"><col></colgroup>
<tr><th>제목</th>
	<td><input type="text" value="${fn: escapeXml(vo.csboard_title) }" 
	name="csboard_title" class="check-empty form-control" title="제목"></td>
</tr>
<tr><th>내용</th>
	<td><textarea name="csboard_content" 
		class="check-empty form-control" title="내용">${vo.csboard_content }</textarea></td>
</tr>
</table>

	<input type="hidden" name="csboard_id" value="${vo.csboard_id }"/>

</form>
	<div class="btn-toolbar my-3 gap-2 justify-content-center">
		<button class="btn btn-warning" id="btn-join">저장</button>
		<button class="btn btn-online-warning" type="button" onclick="history.go(-1)">취소</button>
	</div>



<script>
$(function(){
	

$('#btn-join').on('click', function(){
	
	if( $("[name=csboard_title]").val().trim() =="" ){
		alert("제목을 입력하세요");
		$("[name=csboard_title]").focus();
		$("[name=csboard_title]").val("")
		return;
	}else if($("[name=csboard_content]").val().trim() =="" ){
		alert("제목을 입력하세요");
		$("[name=csboard_content]").focus();
		$("[name=csboard_content]").val("")
		return;
	}

	$('form').submit()
})

$('#btn-cancel').click(function(){
	$('form').attr('action', 'info').submit()
})

})
</script>
</body>
</html>