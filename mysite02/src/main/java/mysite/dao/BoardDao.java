package mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;

public class BoardDao {
	public List<BoardVo> findPageAll(int offset1, int offset2){
		List<BoardVo> result = new ArrayList<>();
		String sql = "select a.id, a.title, a.contents, a.hit, date_format(a.reg_date, '%Y-%m-%d %h:%i:%s'), a.g_no, a.o_no, a.depth, a.user_id, b.name"
				    + " from board a"
				    + " join user b on a.user_id = b.id"
				+ " order by g_no desc, o_no asc"
				    +" limit ?, ?";
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
		){			
			pstmt.setLong(1, offset1);
			pstmt.setLong(2, offset2);
			
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
	
	public int countPages() {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select count(id) from board");
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
	
	public BoardVo findById(long id) {
		BoardVo vo = new BoardVo();
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select title, contents, user_id, g_no, o_no, depth from board where id = ?");
		){	
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();

			if(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				Long userId = rs.getLong(3);
				Long g_no = rs.getLong(4);
				Long o_no = rs.getLong(5);
				Long depth = rs.getLong(6);
				
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserId(userId);
				vo.setId(id);
				vo.setgNo(g_no);
				vo.setoNo(o_no);
				vo.setDepth(depth);
			}
			
			rs.close();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return vo;
	}
	
	public int update(BoardVo vo) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set title = ?, contents = ? where id = ?");
		){			
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getId());
			
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;	
	}
	
	public int updateViewCnt(Long id) {
		int count = 0;
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update board set hit = hit+1 where id = ?");
		){			
			
			pstmt.setLong(1, id);
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;
	}
	

	public int insertOrigin(BoardVo vo, int gNo) {
		int count = 0;
		String sql = "insert into board (title, contents, hit, reg_date, g_no, o_no, depth, user_id)"
			 +" values (? , ? , 0, now(), ?, 1, 0, ?)";
		
		try (
			Connection conn = getConnection();
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
			Connection conn = getConnection();
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
			Connection conn = getConnection();
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
			Connection conn = getConnection();
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
	
	public int deletById(Long boardId) {
		int count = 0;
		String sql = "delete from board where id = ?";
		
		try (
			Connection conn = getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
		){			
			
			pstmt.setLong(1, boardId);
			count = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		return count;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		
			String url = "jdbc:mariadb://192.168.0.19:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}
}
