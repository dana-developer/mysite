package mysite.repository;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private DataSource dataSource;
	
	private SqlSession sqlSession;
	
	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public List<BoardVo> findPageAll(int offset1, int offset2){
		return sqlSession.selectList("board.findPageAll", Map.of("offset1",offset1, "offset2", offset2));
	}
	
	public int countPages() {
		return sqlSession.selectOne("board.countPages");
	}
	
	public BoardVo findById(long id) {
		return sqlSession.selectOne("board.findById", id);
	}
	
	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);	
	}
	
	public int updateViewCnt(Long id) {
		return sqlSession.update("board.updateViewCnt", id);	
	}
	

	public int insertOrigin(BoardVo vo, int gNo) {
		return sqlSession.insert("board.insertOrigin", Map.of("vo", vo, "gNo2", gNo));
	}
	
	public int findMaxGroupId() {
		return sqlSession.selectOne("board.findMaxGroupId");
	}
	
	public int updateONo(BoardVo vo) {
		return sqlSession.update("board.updateONo", vo);
	}
	
	public int insertReply(BoardVo vo) {
		return sqlSession.insert("board.insertReply", vo);
	}
	
	public int deleteById(Long id, Long userId) {
		return sqlSession.delete("board.deleteById", Map.of("id", id, "userId", userId));
	}
	

	public List<BoardVo> findPageAllByKeyword(int offset1, int offset2, String keyword) {
		return sqlSession.selectList("board.findPageAllByKeyword", 
				Map.of("offset1", offset1, "offset2", offset2, "keyword", "%" + keyword + "%"));	
	}
	
	public int countPagesByKeyword(String keyword) {
		return sqlSession.selectOne("board.countPagesByKeyword", "%" + keyword + "%");		
	}
}
