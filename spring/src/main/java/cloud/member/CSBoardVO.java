package cloud.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CSBoardVO {
	int csboard_id, csboard_viewcount;
	String writer, csboard_title, csboard_content, csboard_writedate;
	
}
