package cloudWeb.member;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberVO {
	private String member_id, member_pw, member_name, member_nickname, 
		   member_gender, member_phone, member_profileimg, member_phone_id, member_birth, member_address;
	private int member_manner_score, member_admin, no;
}
