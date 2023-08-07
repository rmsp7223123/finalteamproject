package cloud.gps;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GpsDAO {
	@Autowired @Qualifier("project") private SqlSession sql;
	
	public List<GpsVO> senior_list() {
		return sql.selectList("gps.list");
	}

	
	
	

}
