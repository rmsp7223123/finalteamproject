<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
	<!-- 댓글이 없는 경우 -->
	<c:if test="${empty list }">
	<div class="col-md-10 content text-center">
		<div class="fs-5">등록된 답변이 없습니다.</div>
	</div>
	</c:if>
	
	<c:forEach items="${list }" var="vo">
	
	<!-- 댓글이 있는 경우 -->
	<div class="col-md-10 content border-bottom py-3" data-id="${vo.csboard_comment_id }">
		<div class="d-flex align-items-center mb-2 justify-content-between">
		
			<div class="d-flex align-items-center mb-2">
				<span>${vo.nickname } [ ${vo.csboard_comment_writedate } ]</span>
			</div>
		<div>
			<span class="title me-4 d-none">답변수정 [<span class="writing">0</span>/1000]</span>
			<a class="btn btn-outline-info btn-sm btn-modify-save">수정</a>
			<a class="btn btn-outline-danger btn-sm btn-delete-cancel">삭제</a>
		</div>
		</div>
		<div class="comment">${fn:replace(fn:replace(vo.csboard_comment_content, lf, '<br>'), crlf, '<br>') }</div>
		<div class="d-none hidden"></div>
	</div>
	
	</c:forEach>
	
<script>
/* 수정/저장버튼 클릭시 */
$('.btn-modify-save').click(function(){
	var _content = $(this).closest('.content');
	if($(this).text()=='수정'){
		modifyStatus(_content);
	}else{
		//변경입력한 댓글을 DB에 저장하도록 처리
		//json 데이터를 서버로 보낼 때 : post, 보내는 내용에 대해 json으로 지정
		$.ajax({
			type: 'post',
			contentType: 'application/json',
			url:'<c:url value="/cs/comment/update"/>',
			data: JSON.stringify( {csboard_comment_id:_content.data('id'), 
				csboard_comment_content:_content.find('textarea').val()})
		}).done(function(response){
			console.log(response)
			alert(response.message)
			_content.find('.hidden').text(response.content);
			stayStatus(_content);
		});
	}
})

// 변경모드상태
function modifyStatus(_content){
	//버튼은 저장/취소
	_content.find('.btn-modify-save').text('저장').removeClass('btn-outline-info').addClass('btn-primary');
	_content.find('.btn-delete-cancel').text('취소').removeClass('btn-outline-danger').addClass('btn-secondary')
	
	// .comment 안에 있던 댓글내용은 textarea에 보여지게
	var _comment = _content.find('.comment'); //댓글 내용이 있는 태그
	var comment = _comment.html().replace(/<br>/g, '\n'); //댓글 내용
	_comment.html(`<textarea class="form-control w-100">\${comment}</textarea>`);
	_content.find('.title').removeClass('d-none').find('.writing').text(comment.length); //입력글자수 보이게
// 	_content.find('.writing').text(comment.length); //실제입력글자수 보이게
// 	_content.find('.title').removeClass('d-none'); //입력글자수 부분 보이게
	
	_content.find('.hidden').html(`\${comment}`);	//원래 댓글내용 그대로 담기
}

//삭제/취소버튼 클릭시
$('.btn-delete-cancel').click(function(){
	var _content = $(this).closest('.content');
	if($(this).text()=='취소'){
	stayStatus(_content);
	}else{
		//삭제클릭시
		if(confirm('댓글을 삭제하시겠습니까?')){
			//댓글 삭제 후 댓글목록을 다시 조회해오는 경우
// 			$.ajax({
// 				url: '<c:url value="/board/comment/delete"/>',
// 				data: {id: _content.data('id')}
// 			}).done(function(){
// 				commentList();
// 			})
//			댓글 삭제 후 해당 댓글태그만 삭제하는 경우
			$.ajax({
				url: '<c:url value="/cs/comment/delete"/>',
				data: {csboard_comment_id: _content.data('id')}
			}).done(function(response){
				if(response){
					_content.remove();
					history.go(0);
				}
			})
		}
	}
})

//가만있는 모드 상태
function stayStatus(_content){
	_content.find('.btn-modify-save').text('수정').addClass('btn-outline-info').removeClass('btn-primary');
	_content.find('.btn-delete-cancel').text('삭제').addClass('btn-outline-danger').removeClass('btn-secondary');
	
	// .comment 안에 있던 textarea의 내용은 원래대로 보여지게
	var _comment = _content.find('.comment'); //댓글 내용이 있는 태그
	var textarea = _content.find('.hidden').html().replace(/\n/g, '<br>');
	_comment.html(textarea);
	_content.find('.title').addClass('d-none'); //글자수부분 안보이게
	_content.find('.hidden').empty();
}
</script>
