package mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		int count = 0;
		String sql = "insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_id)"
			 +" values (? , ? , 0, now(), ?, 1, 0, ?)";
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){			
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, gNo);
			pstmt.setLong(4, vo.getUserId());
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;
	}
	
	public int findMaxGroupId() {
		int count = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select ifnull(max(g_no), 1) from board");
			ResultSet rs = pstmt.executeQuery();
		){	
			if(rs.next()) {
				count = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;
	}
	
	public int updateONo(BoardVo vo) {
		int count = 0;
		String sql = "update board set o_no = o_no+1 where g_no = ? and o_no >= ?";
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){			
			pstmt.setLong(1, vo.getgNo());
			pstmt.setLong(2, vo.getoNo()+1);
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;
	}
	
	public int insertReply(BoardVo vo) {
		int count = 0;
		String sql = "insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_id)"
				   +" values (? , ? , 0, now(), ?, ?, ?, ?)";
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){			
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getgNo());
			pstmt.setLong(4, vo.getoNo()+1);
			pstmt.setLong(5, vo.getDepth()+1);
			pstmt.setLong(6, vo.getUserId());
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;
	}
	
	public int deletById(Long id, Long userId) {
		int count = 0;
		String sql = "delete from board where id = ? and user_id = ?";
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){			
			
			pstmt.setLong(1, id);
			pstmt.setLong(2, userId);

			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;
	}
	

	public List<BoardVo> findPageAllByKeyword(int offset1, int offset2, String keyword) {
		List<BoardVo> result = new ArrayList<>();
		String sql = "select a.id, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.g_no, a.o_no, a.depth, a.user_id, b.name"
				    + " from board a"
				    + " join user b on a.user_id = b.id"
				    + " where a.title like ?"
				+ " order by g_no desc, o_no asc"
				    +" limit ?, ?";
		try (
				Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
		){			
			pstmt.setString(1, "%"+keyword+"%");
			pstmt.setLong(2, offset1);
			pstmt.setLong(3, offset2);
			
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()) {
				Long id = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long hit = rs.getLong(4);
				String regDate = rs.getString(5);
				Long gNo = rs.getLong(6);
				Long oNo = rs.getLong(7);
				Long depth = rs.getLong(8);
				Long userId = rs.getLong(9);
				String userName = rs.getString(10);
				
				BoardVo vo = new BoardVo();
				vo.setId(id);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
				vo.setUserId(userId);
				vo.setUserName(userName);
				
				result.add(vo);
			}
			
			rs.close();
						
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} 
		
		return result;	
	}
	
	public int countPagesByKeyword(String keyword) {
		int count = 0;
		
		try (
			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select count(id) from board where title like ?");
		){	
			pstmt.setString(1, "%"+keyword+"%");
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				count = rs.getInt(1);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;
	}
}
