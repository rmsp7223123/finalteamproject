/**
 * 회원정보 관련 처리
 */
var member = {
	test:'test',
	
	// 태그별로 상태확인
	tagStatus: function( tag, input ){
		if( tag.is("[name=member_name]") )			return this.nameStatus( tag.val() );
		else if ( tag.is("[name=member_nickname]") )	return this.userpwCheckStatus( tag.val(), input );
		else if ( tag.is("[name=member_birth]") )		return this.useridStatus( tag.val() )
		else if ( tag.is("[name=member_phone]") )		return this.phoneStatus( tag.val() )
	},
	
	//이메일 입력 상태 확인
	phoneStatus: function( phone ){
		var reg = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
		
		if( phone=="" )		return this.common.empty;
		else if( reg.test(phone) ) 		return this.phone.valid;
		else							return this.phone.invalid;
	},
	
	phone: {
		valid: 	 { is:true,   desc:'유효합니다' }, 
		invalid: { is:false,  desc:'유효하지 않습니다' }, 
	},
	
	
	
	common: {
		empty: { is:false,  desc:'입력하세요' },
		space: { is:false,  desc:'공백없이 입력하세요' },
		min:   { is:false,  desc:'5자이상 입력하세요' },
		max:   { is:false,  desc:'10자이내 입력하세요' },
	},
	
	space: /\s/g,
	
	userpw: {
		invalid: { is:false,  desc:'영문 대/소문자, 숫자만 입력하세요' },
		lack:    { is:false,  desc:'영문 대/소문자, 숫자를 모두 포함해야 합니다'},
		valid:   { is:true,   desc:'사용가능합니다' },
		equal:   { is:true,   desc:'비밀번호와 일치합니다' },
		notEqual:{ is:false,  desc:'비밀번호가 일치하지 않습니다' },
	},
	
	showStatus: function( target ){
		var status = this.tagStatus( target, true )
		target.closest('.input-check').find('.desc').text( status.desc )
						.removeClass('text-success text-danger')
						.addClass( status.is ? 'text-success' : 'text-danger')
		
	},
	
	// 비밀번호를 다시 입력할때 비밀번호확인 항목을 초기화처리 추가
	userpwStatus: function( pw, input ){
		if( input ){
			$('[name=userpw_ck]').val('');
			$('[name=userpw_ck]').closest('.input-check').find('.desc').text('')
								 .removeClass('text-success text-danger');
			
		}
		
		var reg = /[^A-Za-z0-9]/g, upper = /[A-Z]/g, lower = /[a-z]/g, digit = /[0-9]/g ;
		if( pw=="" )					return this.common.empty;
		else if( pw.match(this.space) )	return this.common.space;
		else if( reg.test(pw) )			return this.userpw.invalid;
		else if( pw.length < 5 )		return this.common.min;
		else if( pw.length > 10 )		return this.common.max;
		else if( ! upper.test(pw) || ! lower.test(pw) 
				|| ! digit.test(pw) )	return this.userpw.lack;
		else							return this.userpw.valid;
	},
	
	userpwCheckStatus: function( pwCheck ){
		if( pwCheck=="" )		return this.common.empty;
		else if( pwCheck == $('[name=userpw]').val() )	return this.userpw.equal;
		else											return this.userpw.notEqual;
	},
	
} 