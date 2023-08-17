package cloud.game;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GameDAO {
	@Autowired
	@Qualifier("project")
	private SqlSession sql;

}
