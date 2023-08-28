/**
 * 공통 함수 선언
 */

//10Mb 를 넘는 파일을 걸러내는 처리
function filteredFile( files ){
	var exist = false;
	//files는 객체, 값만 따로 뽑아본다
	//객체: Object : var obj1 = new Object();  === 	var obj2 = {};
	//배열 Array;  :	var arr1 = new Array();   === 	var arr2 = [];

	console.log( '1> ', files )
	files = Object.values(files).filter( function(file){
		//10M 초과 파일이 여러개라도 alert 은 한번만 띄우기 위한 처리
		if( file.size > 1024*1024*10 ) exist = true;	
		//10M 이하 파일들만 걸러내기	
		return file.size <= 1024*1024*10; 
	})
	console.log( '2> ', files )
	
	if( exist ){
		alert('10Mb 이상 첨부 불가!')
	}
	
	return files; //걸러낸 10Mb 이하 파일들
}

//파일관리 객체 생성자함수
function FileList(){
	this.files = [];
	this.info = { upload:[], fileId:[], removeId:[] };  //업로드여부, 업로드된 파일id, 삭제할 파일id
	//이미 업로드된 파일: 처리할 게 없음
	//새로 추가첨부한 파일: upload 해야 함
	//이미 업로드되어 있던 파일을 삭제: DB+ 물리적 파일 삭제
	// new Object  ==  {}, new Array() == []
	
	this.setFile = function( file, id ){
		//선택한 파일마다 alert이 뜬다 
//		if( file.size > 1024*1024*10 ){
//			alert("10Mb 이상의 파일은 첨부할 수 없습니다")
//			return;
//		}
		// 두번째 파라미터 id가 O 업로드 안함(false)
		// 드래그드랍 또는 파일태그로 선택 하는 경우  id가 X 업로드 해야함(true)
		this.info.upload.push( typeof id == "undefined" ? true  : false ); //true: 업로드해야 함, false: 업로드 안함
		// 두번쨰 파라미터 id가 O : fildId 에 담기
		if( typeof id != "undefined" ) this.info.fileId.push( id ); 
		
		this.files.push( file );
	}
	
	this.getFile = function(){
		return this.files;
	}
	
	this.removeFile = function( i ){
		this.files.splice( i, 1 );
		//업로드여부 삭제
		this.info.upload.splice( i, 1 );
		
		//이미 업로드 되어 있는 파일삭제한 경우 삭제할 파일id에 담기
		if( typeof this.info.fileId[i] != "undefined" ){
			this.info.removeId.push( this.info.fileId[i] ); //삭제대상 파일id 추가
			this.info.fileId.splice(i, 1); 	// 파일id 목록에서도 삭제			                                               
		}
		 
	}
	
	this.showFile = function(){
		
		var tag = '';
		if( this.files.length > 0 ){
			for( var i=0; i<this.files.length; i++ ){
				tag +=
				`<div class="file-item d-flex gap-2 align-items-center my-1">
					<button type="button" class="btn-close small" data-seq="${i}"></button>
					<span class="file-name">${this.files[i].name}</span>
				</div>`;				
			}
			
		}else{
			tag = '<div class="text-center py-3">첨부할 파일을 마우스로 끌어 오세요</div>';
		}
		$('.file-drag').html( tag );
		console.log( '최종fileList', fileList )
	}
}


//파일첨부 정보 file태그에 담기
var singleFile = ''; //파일선택시 선택한 첨부파일정보를 담아둘 변수
function singleFileUpload(){
	if( singleFile != "" ){
		var transfer = new DataTransfer();
		transfer.items.add( singleFile );
		//화면 태그 속성: attr : 기본에 해당, 나중에 속성추가지정: prop 
		$('input[type=file]').prop('files', transfer.files);
		//console.log( $('input[type=file]').val() )
	}
}

//전화번호형태 만들기
function toPhone( tag ){
	// 02-1234-5678(10자리)  010-1234-5678(11자리)
	//숫자만 입력되게 처리 01012345678 - 숫자만 뽑아내기
	var phone = tag.val().replace(/[^0-9]/g, '').replace(/[-]/g,'') ; 
	if( phone.length > 1 ){  //2자리 이상 입력하면: 02, 062, 010
		// - 가 들어가야 할 위치 계산하기		
		var start = phone.substr(0, 2)=="02" ? 2 : 3,    // 첫번째 항목: 02, 062 
			middle = start + 4	;  // 두번째 항목: 1234 - 무조건 4자리 
		
		// - 만들어서 넣기
		if( phone.length > middle ){
			phone = phone.substr(0, start) + "-" + phone.substr(start,4) + "-" + phone.substr(middle,4); 
		}else if( phone.length > start ) {
			phone = phone.substr(0, start) + "-" + phone.substr(start,4) + "-";
		}
	}
	// substr(시작, 길이), substring(시작, 끝위치)
	
	tag.val( phone );
}


function modalAlert( type, title, message ){
	$('#modal-alert .modal-title').html( title ); 	//모달창 제목
	$('#modal-alert .modal-body').html( message ); 	//모달창 내용

	//모달창 type에 따라 아이콘모양,색 지정을 위해 모든 클래스 삭제
  	$('.modal-icon').removeClass(
	'text-info text-warning text-danger text-primary text-success fa-circle-question fa-circle-exclamation')
	// 아니오/확인 으로 사용되는 버튼의 색상 초기화
	$('.modal-footer .btn-ok').removeClass(
		'btn-info btn-warning btn-danger btn-primary btn-success');
	  	
  	if( type=='danger' ){//confirm에 해당
  		//아니오, 예 버튼 
  		$('.modal-footer .btn-ok').addClass('btn-secondary').text('아니오')
		$('.modal-footer .btn-danger').removeClass('d-none')
		
  		$('.modal-icon').addClass('fa-circle-question') //아이콘 물음표
		
	}else{
		//확인 버튼, confirm일때만 사용할 btn-danger 는 필요없음
		$('.modal-footer .btn-ok').addClass('btn-' + type).text('확인')
		$('.modal-footer .btn-danger').addClass('d-none')
		
		$('.modal-icon').addClass('fa-circle-exclamation')  //아이콘 느낌표
	}
	$('.modal-icon').addClass('text-'+ type);
}


//동적으로 만들어진 요소에 대해서는 document에 이벤트를 등록해야 한다
$(document)
.on('click', '.date + .date-delete', function(){
	$(this).css('display', 'none'); //삭제버튼 안보이게
	$(this).prev('.date').val(''); //날짜태그의 값을 초기화
})
.on('click', '#file-attach .file-delete', function(){
	$(this).addClass('d-none');  //삭제버튼 안 보이게
	
	$('input[type=file]').val('');  //첨부되어 있던 이미지파일정보 없애기
	var _preview = $('#file-attach .file-preview');
	if( _preview.length > 0 ) _preview.empty(); //미리보기한 이미지 태그 없애기
			
	var _name = $('#file-attach .file-name'); //파일명 태그 
	if( _name.length > 0 ) _name.empty(); 	//파일명없애기
	
	console.log( '파일정보> ',$('[type=file]').val() )
})
.on('click', '.file-preview img', function(){
	// 미리보기 이미지 클릭시 크게 보이게
	if( $('#modal-image').length == 1 ){
		$('.modal').removeClass('fade');
		$('.modal-body').html( $(this).clone() );
		$('#modal-image .modal-body img').removeAttr('style');
		new bootstrap.Modal( $('#modal-image') ).show()
						$("#modal-image").on('show.bs.modal', function() {
					$(this).data('bs.modal')._config.backdrop = false;
				});
	}
	
})
.on('click', '.file-item .btn-close', function(){
	// 첨부된 파일 삭제 클릭시
	fileList.removeFile( $(this).data('seq') );
	fileList.showFile();
})


//파일이 이미지파일인지 확인
function isImage( filename ){
	// abc.png, abc.jpg, abc.txt, abc.hwp, abc.BMP, abc.JPG
	var ext = filename.substr(  filename.lastIndexOf('.')+1 ).toLowerCase();
	var imgs = [ "png", "jpg", "bmp", "gif", "jpeg", "webp" ];
	return imgs.indexOf( ext )== -1 ? false : true;
}


//첨부파일 크기 제한 함수
function rejectedFile( fileInfo, tag ){
	// 1024 byte = 1Kb, 1Mb = 1024*1024 byte, ...
	if( fileInfo.size > 1024*1024*10 ){
		alert("10Mb 를 넘는 파일은 첨부할 수 없습니다!");
		tag.val('');
		return true;
	}else
		return false;
}

$(function(){
	//다중파일선택 처리
	$('input#file-multiple').on('change', function(){
//		var files = this.files;
//		files = filteredFile( files )
		var files = filteredFile( this.files ) //10M 이하 파일만

		for( var i=0; i<files.length; i++ ){
			fileList.setFile( files[i] );
		}
		fileList.showFile()
	}) 
	
	//프로필 이미지 선택처리
	$('input#file-single').change(function(){
//		console.log( $(this) )
//		console.log( this.files )
		
		var _preview = $('#file-attach .file-preview'); //이미지 미리보기요소
		var _delete = $('#file-attach .file-delete');	//파일삭제요소
		var _name = $('#file-attach .file-name');		//파일명 요소
		
		var attached = this.files[0];
		console.log( attached )
		if( attached ){
			// 파일사이즈 제한
			if( rejectedFile(attached, $(this)) ) return;
			// 파일명 보여지게
			if( _name.length>0 )	_name.text( attached.name );
			_delete.removeClass('d-none'); //삭제버튼 보이게
			
			//이미지파일인지 확인			
			if( isImage( attached.name ) ){
				singleFile = attached; //선택한 파일정보를 관리
				// 미리보기 태그가 있을때만
				if( _preview.length > 0 ){
					_preview.html( "<img>" );
					
				}else{
					//첨부파일이 이미지인데, 미리보기 요소가 없으면 동적으로 만들어 보이게 처리
					// 삭제버튼 앞에 넣기
					_delete.before( "<span class='file-preview'><img></span>" );
					_preview = $('#file-attach .file-preview')
					
				}
				var reader = new FileReader();
				reader.readAsDataURL( attached );
				reader.onload = function(e){
//						_preview.children("img").attr("src", e.target.result);	
					_preview.children("img").attr("src", this.result);	
				}					
				
			}else{
				//이전 선택했던 이미지파일처리
				_preview.empty();
				
				if( $(this).hasClass("image-only") ){
					singleFile = '';  //이미지가 아닐 파일인 경우는 관리정보를 초기화
					$(this).val('');  		//실제file 태그의 정보 초기화
					_delete.addClass('d-none'); //삭제버튼 안 보이게
				}
				
			}
			
		}else{
			// 파일선택 창에서 취소를 클릭한 경우: 어떤처리도 하지 않는다
			// 파일정보는 관리된 singleFile 변수에 있다
		}
		
	})
	
	
	$('.date').change(function(){
		$(this).next('.date-delete').css('display', 'inline')
	})

	$('[name=phone]').keyup(function(){
		toPhone( $(this) );	
	})
	
	
	$('body').on('dragover dragleave drop', function(e){
		e.preventDefault();
	})
	
	//드래그드랍으로 파일첨부 처리	
	$('.file-drag').on({
		'dragover dragleave drop' : function(e){
			e.preventDefault();
			
			//드래그오버시 입력태그에 커서가 있을때처럼 보여지게
			if( e.type=='dragover' )  	$(this).addClass('drag-over') 
			else 						$(this).removeClass('drag-over')
		},
		'drop': function(e){
			console.log( e.originalEvent.dataTransfer.files )
//			var files = e.originalEvent.dataTransfer.files ;
//			files = filteredFile( files );  // 10M 초과 파일은 걸러내기
			var files = filteredFile( e.originalEvent.dataTransfer.files );  // 10M 초과 파일은 걸러내기
			
			for(var i=0; i<files.length; i++){
				//폴더는 담지 않는다
				if( files[i].type == "" ){
					alert("폴더는 첨부할 수 없습니다")
				}else{
					fileList.setFile( files[i] );
				}
			}
			console.log( fileList )
			fileList.showFile();
		}
	})
	
	
	
	var today = new Date();
	var range = today.getFullYear()-150 + ':' + today.getFullYear();
		if($('.date').length>0){
			console.log(1)
		$.datepicker.setDefaults({
			dateFormat: "yy-mm-dd",
			changeYear: true,
			changeMonth: true,
			yearRange: range,
			showMonthAfterYear: true,
			monthNamesShort: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월"
						, "9월", "10월", "11월", "12월"],
			dayNamesMin: [ "일", "월" , "화", "수", "목", "금", "토"],
			maxDate: today, 
		})
		
		$( ".date" ).datepicker();
		$( ".date" ).attr('readonly', true)
	}
}) 

//서브밋 전 다중파일첨부 정보 file태그에 담기
function multipleFileUpload(){
	var transfer = new DataTransfer();
	var files = fileList.getFile();
	if( files.length>0 ){
		for( var i=0; i<files.length; i++ ){
			//업로드해야 하는 파일들만(true) 추가
			if( fileList.info.upload[i] ) transfer.items.add( files[i] ) 
		}
	}
	$('input#file-multiple').prop('files', transfer.files )
}

//입력항목 입력여부 확인
function emptyCheck(){
	var ok = true;
	$('.check-empty').each(function(){
		if( $(this).val()=="" ){
			alert( $(this).attr('title')+ " 입력하세요" );
			$(this).focus();
			ok = false;
			return ok;
		}		
	})
	return ok;
}

$(function(){
		$('#listModal button.close').click(function(){
			console.log("asdsad");
			$('#listModal').addClass("fade").removeClass("show");
		})
})



