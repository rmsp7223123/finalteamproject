package cloudWeb.csboard;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CSBoardCommentVO {
	int csboard_comment_id, csboard_id;
	String writer, csboard_comment_content, csboard_comment_writedate, nickname;
}
