package cloud.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AlarmVO {
	String member_id, alarm_content, alarm_time;
	int alarm_id;
}
