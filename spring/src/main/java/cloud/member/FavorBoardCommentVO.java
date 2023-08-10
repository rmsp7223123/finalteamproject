package cloud.member;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FavorBoardCommentVO {
	
	int fav_board_comment_id, fav_board_id;
	String writer, fav_board_comment_content, fav_board_comment_writedate;

}
