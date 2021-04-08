package mvc.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import mvc.database.DBConnection;

public class BoardDAO {
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private ArrayList<BoardDTO> dtos = null;
	private static BoardDAO instance;
	
	public BoardDAO() {
	}
	
	//싱글톤 패턴으로 BoardDAO 객체를 하나만 만들어서 리턴하는 코드
	public static BoardDAO getInstance() {
		if(instance == null) {
			instance = new BoardDAO();
		}
		return instance;
	}
	
	//board테이블의 레코드 개수를 가져오는 메서드
	public int getListCount(String items, String text) {
		
		int count = 0;
		String sql = "";
		
		//파라메터로 넘어오는 검색기능이 둘 다 아무값도 없을 시
		if(items == null && text == null) {
			sql = "select count(*) from board ";
		}
		//파라메터로 넘어오는 값을 검색하기
		else {
			sql = "select count(*) from board where" + items + "like '%" + text + "%'";
		}
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
		}catch (Exception e) {
			System.out.println("getListCount()예외" + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e2) {
				System.out.println("getListCount() close()호출 예외" + e2.getMessage());
				e2.printStackTrace();
			}
		}
		return count;
	}
	
	//board테이블에 있는 레코드 가져오는 메서드
	public ArrayList<BoardDTO> getBoardList(int page, int limit, String items, String text){
		
		int total_record = getListCount(items,text);
		int start = (page - 1) * limit;  //페이지는 0페이지가 없으므로 -1을 한다.
		int index = start + 1;
		
		String sql = "";
		dtos = new ArrayList<BoardDTO>();
		
		//파라메터로 넘어오는 검색기능이 둘다 아무값도 없다면...
		//items : 제목,본문,글쓴이  ,  text : 검색단어
		if(items == null && text == null) {
			sql = "select * from board order by num desc";
		}
		else {
			//파라메터로 넘어오는 값으로 검색기능 하게끔 쿼리를 작성함.
			sql = "select * from board where " + items + " like '%" + text + "%' order by num desc";
		}
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			//ex)6페이지를 보고 있다가 1페이지를 클릭을 하게 되면...
			while(rs.absolute(index)) {
				BoardDTO board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setRegist_day(rs.getString("regist_day"));
				board.setHit(rs.getInt("hit"));
				board.setIp(rs.getString("ip"));
				
				dtos.add(board);
				
				//인덱스가 가져올 데이터 건수 보다 작다면....
				if(index < (start + limit) && index <= total_record) {
					index++;
				}
				else {
					break;
				}				
			}				
		} catch (Exception e) {
			System.out.println("getBoardList()예외" + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("getBoardList() close()호출 예외" + e2.getMessage());
				e2.printStackTrace();
			}
		}		
		return dtos;
	}
	
	public String getLoginName(String id) {
		
		String name = null;
		String sql = "select * from members where id = ?";
		
		System.out.println("getLoginName()들어옴");
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				name = rs.getString("name");
			}
		} catch (Exception e) {
			System.out.println("getLoginNameNum예외 발생 : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e2) {
				System.out.println("getLoginName close()호출 예외" + e2.getMessage());
				e2.printStackTrace();
			}
		}
		
		return name;
	}
	
	//DB에 board테이블에 새로운 글을 저장할 경우 게시글 번호를 1로 시작
	public void insertBoard(BoardDTO board) {
		
		String sql = "alter table board auto_increment = 1" ;
		try {
			//auto increment 부분을 다시 1로 재설정 해주는 부분
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
			
			sql = "insert into board values(?,?,?,?,?,?,?,?)";
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getNum());
			pstmt.setString(2, board.getId());
			pstmt.setString(3, board.getName());
			pstmt.setString(4, board.getSubject());
			pstmt.setString(5, board.getContent());
			pstmt.setString(6, board.getRegist_day());
			pstmt.setInt(7, board.getHit());
			pstmt.setString(8, board.getIp());

			pstmt.executeUpdate();
			System.out.println("insertBoard() 수행완료");
		} catch (Exception e) {
			System.out.println("insertBoard() 예외 발생 : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				if(rs != null)  rs.close();
			} catch (Exception e2) {
				System.out.println("insertBoard close()호출 예외" + e2.getMessage());
				e2.printStackTrace();
			}
		} 
	}
	
	//선택된 게시글의 상세내용을 가져오는 메서드
	public BoardDTO getBoardByNum(int num, int page) {
		
		BoardDTO board = null;
		String sql = "select * from board where num = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				board = new BoardDTO();
				board.setNum(rs.getInt("num"));
				board.setId(rs.getString("id"));
				board.setName(rs.getString("name"));
				board.setSubject(rs.getString("subject"));
				board.setContent(rs.getString("content"));
				board.setRegist_day(rs.getString("regist_day"));
				board.setHit(rs.getInt("hit"));
				board.setIp(rs.getString("ip"));
			}
			
			System.out.println("getBoardByNum() 수행 완료");
		} catch (Exception e) {
			System.out.println("getBoardByNum() 예외 발생 : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				if(rs != null)  rs.close();
			} catch (Exception e2) {
				System.out.println("getBoardByNum close()호출 예외" + e2.getMessage());
				e2.printStackTrace();
			}
		}
		//조회수 증가 메서드를 호출
		updateHit(num);
		return board;
	}
	
	//선택된 글의 조회수 증가 메서드
	public void updateHit(int num) {
		
		int hit = 0;
		String sql = "select hit from board where num = ?";
		
		try {
			
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				hit = rs.getInt("hit") + 1;
			}
			
			sql = "update board set hit = ? where num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, hit);
			pstmt.setInt(2, num);
			pstmt.executeUpdate();
			
			System.out.println("updateHit() 수행 완료");
			
		} catch (Exception e) {
			
			System.out.println("updateHit() 예외 발생 : " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				if(rs != null)  rs.close();
				
			} catch (Exception e2) {
				
				System.out.println("updateHit close()호출 예외" + e2.getMessage());
				e2.printStackTrace();
				
			}
		}
	}
	
	//수정한 게시글의 내용을 DB에 저장하는 메서드
	public void updateBoard(BoardDTO board) {
		String sql = "update board set subject = ?, content = ?, regist_day = ?, hit = ? where num = ?";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getSubject());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getRegist_day());
			pstmt.setInt(4, board.getHit());
			pstmt.setInt(5, board.getNum());
			
			pstmt.executeUpdate();
			
			System.out.println("updateBoard() 수행 완료");
			
		} catch (Exception e) {
			
			System.out.println("updateBoard() 예외 발생 : " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				
			} catch (Exception e2) {
				
				System.out.println("updateBoard close()호출 예외" + e2.getMessage());
				e2.printStackTrace();
				
			}
		}
	}
	
	public void deleteBoard(int num) {
		String sql = "delete from board where num = ? ";
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			pstmt.executeUpdate();
			System.out.println("deleteBoard() 수행 완료");
			
		} catch (Exception e) {
			
			System.out.println("deleteBoard() 예외 발생 : " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				
			} catch (Exception e2) {
				
				System.out.println("deleteBoard close()호출 예외" + e2.getMessage());
				e2.printStackTrace();
				
			}
		}
	}
	
}
