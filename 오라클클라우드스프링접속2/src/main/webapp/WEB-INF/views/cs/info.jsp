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
<h3 class="my-4">건의사항</h3>
<table class="tb-row">
<colgroup><col width="180px"><col>
		<col width="160px"><col width="160px">
		<col width="100px"><col width="100px">
</colgroup>
<tr><th>제목</th>
	<td colspan="5">${vo.csboard_title }</td>
</tr>
<tr><th>작성자</th>
	<td>${vo.nickname }</td>
	<th>작성일자</th>
	<td>${vo.csboard_writedate }</td>
</tr>
<tr><th>내용</th>
	<td colspan="5">${fn: replace(vo.csboard_content, crlf, "<br>") }</td>
</tr>
</table>

	<div class="btn-toolbar my-3 justify-content-center">
	<c:set var="params" value="curPage=${page.curPage}&search=${page.search}&keyword=${page.keyword }&pagelist=${page.pageList }"/>
		<button class="btn btn-warning" onclick="location='list?${params}'">목록</button>
		<button class="btn btn-warning ml-3" onclick="location='modify?csboard_id=${vo.csboard_id}&${params}'">수정</button>
		<button class="btn btn-warning ml-3"
			onclick="if(confirm('건의사항을 삭제하시겠습니까?')){location='delete?csboard_id=${vo.csboard_id}&${params}'}">삭제
		</button>
	</div>

<!-- 댓글입력부분 -->
<div class="row justify-content-center" id="comment-register">
	<div class="col-md-10 content">
		<div class="title d-flex align-items-center justify-content-between mb-2 invisible">
			<span>답변작성 [ <span class="writing">0</span> / 1000 ]</span>
			<a class="btn btn-outline-primary btn-sm btn-register invisible">답변등록</a>
		</div>
		<div class="comment">
			<c:if test="${empty list }">
			<div class="form-control  justify-content-center align-items-center  py-3 d-flex ">
				<div>답변을 입력하세요</div>
			</div>
			</c:if>
		</div>	
	</div>
</div>
<!-- 댓글목록출력부분 -->
<div class="row justify-content-center mt-4" id="comment-list">

</div>

<script>
commentList();

//댓글목록조회해와 출력
function commentList(){
	$.ajax({
		url: '<c:url value="/cs/comment/list/${vo.csboard_id}"/>'
	}).done(function( response ){
		console.log( response )
		$('#comment-list').html( response )
	})
	
}

//댓글등록처리
$('.btn-register').click(function(){
	//입력한 글이 있을때만 처리
	var _textarea = $('#comment-register textarea');
	if( _textarea.val().length == 0 ) return;
	
	$.ajax({
		url: '<c:url value="/cs/comment/register"/>',
		data: { csboard_id: ${vo.csboard_id}, csboard_comment_content: _textarea.val()},
	}).done(function( response ){
		console.log( response )
		if( response ){
			alert("댓글 등록 성공");
			initRegisterContent();
			commentList();
			history.go(0);
		}else{
			alert("댓글 등록 실패");
		}
	});
	
})


$('#comment-register .comment').click(function(){
	//form-control이 지정된 태그가 div 이면 입력 안되니 입력태그를 만들어서 교체한다
	if( $(this).children(".form-control").is("div") ){
		$(this).children('div.form-control').remove(); //div 는 없애기
		$(this).append(`<textarea class="form-control w-100"></textarea>`);
		$(this).children("textarea").focus();
		$('.content .title').removeClass('invisible');
	}
})

//댓글등록부분 초기화
function initRegisterContent(){
	$('#comment-register .writing').text(0);
	//등록제목부분:등록버튼이 포함된 부분 안보이게
	$('#comment-register .title').addClass('invisible');
	
	//textarea 태그가 아니라 화면 초기화상태로
	$('#comment-register .comment textarea').remove();
	$('#comment-register .comment').html(
		`
	 	<div class="form-control  justify-content-center align-items-center  py-3 d-flex ">
			<div>댓글을 입력하세요</div>
		</div>
		` 
	)
	
}

//댓글입력 textarea에서 커서를 다른곳으로 이동하면
$(document).on('focusout', '#comment-register textarea', function(){
	//입력이 되어 있지 않은 경우 초기화하기
	$(this).val( $(this).val().trim() );
	
	if( $(this).val()== ""){
		initRegisterContent();
	}
	
}).on('keyup', '.comment textarea',  function(){
	var comment = $(this).val();
	if( comment.length > 1000 ){
		alert("최대 1000자까지 입력할 수 있습니다");
		comment = comment.substr(0,1000);
	}
	$(this).val( comment );	
	$(this).closest('.content').find('.writing').text( comment.length ); //입력글자수 표현
	
	//입력글자수가 1이상 이면 등록버튼 보이게
	if( comment.length > 0 ) $('.btn-register').removeClass('invisible');
	else                     $('.btn-register').addClass('invisible'); 
})



</script>

</body>
</html>