package cloud.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlarmVO {
	String member_id, alarm_content, alarm_time, member_phone_id;
	int alarm_id;
}
