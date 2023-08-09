package cloud.gps;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GpsDAO {
	@Autowired
	@Qualifier("project")
	private SqlSession sql;

	public List<GpsVO> senior_list() {
		return sql.selectList("gps.list");
	}

	public List<GpsVO> senior_like() {
		return sql.selectList("gps.likelist");
	}

	public void bmark(int key) {
		sql.insert("gps.bmark", key);
	}

	public void addlike(int key) {
		sql.insert("gps.addlike", key);
	}

	public void likecnt(int key) {
		sql.update("gps.likecnt", key);
	}

	public void delbmark(int key) {
		sql.delete("gps.delbmark", key);
	}

	public void unlikecnt(int key) {
		sql.update("gps.unlikecnt", key);
	}

	public String likeyet(int key) {
		return sql.selectOne("gps.likeyet", key);
	}

	public List<GpsVO> marker() {
		return sql.selectList("gps.marker");
	}
}
