package cloud.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalendarVO {
	String member_id, calendar_content, calendar_date, calendar_importance;
	int calendar_id;
}
