/**
 * 회원정보 관련 처리
 */
var member = {
	test:'test',
	
	// 태그별로 상태확인
	tagStatus: function( tag, input ){
		if ( tag.is("[name=member_nickname]") )			return this.nicknameStatus( tag.val())
		else if ( tag.is("[name=member_phone]") )		return this.phoneStatus( tag.val() )
	},
	
	//이메일 입력 상태 확인
	phoneStatus: function( member_phone ){
		var reg = /^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$/;
		
		if( member_phone=="" )		return this.common.empty;
		else if( reg.test(member_phone) ) 		return this.phone.valid;
		else							return this.phone.invalid;
	},
	
	phone: {
		valid: 	 { is:true,   desc:'' }, 
		invalid: { is:false,  desc:'전화번호를 정확히 입력해주세요' }, 
	},
	
	
	//아이디 입력 상태 확인: 영문소문자나 숫자만
	nicknameStatus: function( member_nickname ){
		if( member_nickname=="" )						return this.common.empty;
		else if( member_nickname.match(this.space) )		return this.common.space;
		else if( member_nickname.length<2 )				return this.common.min;
		else if( member_nickname.length>15 )				return this.common.max;
		else											return this.nickname.valid;
	},
	
	nickname: {
		valid: { is:true,  desc:'중복확인을 완료해주세요'},		
		usable: { is:true,  desc:'사용가능한 닉네임입니다'},		
		unUsable: { is:false,  desc:'이미 사용중인 닉네임입니다'}		
	},
	
	common: {
		empty: { is:false,  desc:'항목을 입력하세요' },
		space: { is:false,  desc:'공백없이 입력하세요' },
		min:   { is:false,  desc:'2자이상 입력하세요' },
		max:   { is:false,  desc:'15자이내 입력하세요' },
	},
	
	space: /\s/g,
	
		
	showStatus: function( target ){
		var status = this.tagStatus( target, true )
		target.closest('.input-check').find('.desc').text( status.desc )
						.removeClass('text-success text-danger')
						.addClass( status.is ? 'text-success' : 'text-danger')
		
	}
	
} 