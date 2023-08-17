package cloud.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlarmVO {
	String member_id, alarm_content, alarm_time, receive_id;
	int alarm_id;
}
