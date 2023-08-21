package cloud.gps;

import java.util.HashMap;
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

	public List<GpsVO> senior_list(String senior_latitude, String senior_longitude, String zoom_level) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("senior_latitude", senior_latitude);
		map.put("senior_longitude", senior_longitude);
		map.put("zoom_level", zoom_level+"");
		return sql.selectList("gps.list", map);
	}

	public List<GpsVO> senior_detail(int key) {
		return sql.selectList("gps.detail", key);
	}
	
	public List<GpsVO> senior_like(String member_id) {
		return sql.selectList("gps.likelist", member_id);
	}

	public void bmark(int key, String member_id) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key", key+"");
		map.put("member_id", member_id);
		sql.insert("gps.bmark", map);
	}

	public void addlike(int key) {
		sql.insert("gps.addlike", key);
	}

	public void likecnt(int key) {
		sql.update("gps.likecnt", key);
	}

	public void delbmark(int key, String member_id) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key", key+"");
		map.put("member_id", member_id);
		sql.delete("gps.delbmark", map);
	}

	public void unlikecnt(int key) {
		sql.update("gps.unlikecnt", key);
	}

	public String likeyet(int key, String member_id) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("key", key+"");
		map.put("member_id", member_id);
		return sql.selectOne("gps.likeyet", map);
	}
	
	public List<GpsVO> search_result(String keyword) {
		return sql.selectList("gps.search", keyword);
	}

}
