package cloudWeb.csboard;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CSBoardVO {
	int csboard_id, csboard_viewcount, no;
	String writer, csboard_title, csboard_content, csboard_writedate, csboard_secret, nickname, comment;
	
}
