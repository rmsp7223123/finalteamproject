package cloud.game;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GameDAO {
	@Autowired
	@Qualifier("project")
	private SqlSession sql;
	
	public List<GameVO> game_rank(GameVO vo) {
		return sql.selectList("game.scorelist", vo);
	}
	
	//게임 처음 하는 경우
	public void game_first(GameVO vo) {
		sql.insert("game.scoreinsert", vo);
	}
	
	//게임 두 번 이상 한 경우
	public void game_second(GameVO vo) {
		sql.update("game.scoreupdate", vo);
	}

}
