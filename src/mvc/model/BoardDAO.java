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
	
	//�̱��� �������� BoardDAO ��ü�� �ϳ��� ���� �����ϴ� �ڵ�
	public static BoardDAO getInstance() {
		if(instance == null) {
			instance = new BoardDAO();
		}
		return instance;
	}
	
	//board���̺��� ���ڵ� ������ �������� �޼���
	public int getListCount(String items, String text) {
		
		int count = 0;
		String sql = "";
		
		//�Ķ���ͷ� �Ѿ���� �˻������ �� �� �ƹ����� ���� ��
		if(items == null && text == null) {
			sql = "select count(*) from board ";
		}
		//�Ķ���ͷ� �Ѿ���� ���� �˻��ϱ�
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
			System.out.println("getListCount()����" + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e2) {
				System.out.println("getListCount() close()ȣ�� ����" + e2.getMessage());
				e2.printStackTrace();
			}
		}
		return count;
	}
	
	//board���̺� �ִ� ���ڵ� �������� �޼���
	public ArrayList<BoardDTO> getBoardList(int page, int limit, String items, String text){
		
		int total_record = getListCount(items,text);
		int start = (page - 1) * limit;  //�������� 0�������� �����Ƿ� -1�� �Ѵ�.
		int index = start + 1;
		
		String sql = "";
		dtos = new ArrayList<BoardDTO>();
		
		//�Ķ���ͷ� �Ѿ���� �˻������ �Ѵ� �ƹ����� ���ٸ�...
		//items : ����,����,�۾���  ,  text : �˻��ܾ�
		if(items == null && text == null) {
			sql = "select * from board order by num desc";
		}
		else {
			//�Ķ���ͷ� �Ѿ���� ������ �˻���� �ϰԲ� ������ �ۼ���.
			sql = "select * from board where " + items + " like '%" + text + "%' order by num desc";
		}
		
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			rs = pstmt.executeQuery();
			//ex)6�������� ���� �ִٰ� 1�������� Ŭ���� �ϰ� �Ǹ�...
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
				
				//�ε����� ������ ������ �Ǽ� ���� �۴ٸ�....
				if(index < (start + limit) && index <= total_record) {
					index++;
				}
				else {
					break;
				}				
			}				
		} catch (Exception e) {
			System.out.println("getBoardList()����" + e.getMessage());
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			} catch (Exception e2) {
				System.out.println("getBoardList() close()ȣ�� ����" + e2.getMessage());
				e2.printStackTrace();
			}
		}		
		return dtos;
	}
	
	public String getLoginName(String id) {
		
		String name = null;
		String sql = "select * from members where id = ?";
		
		System.out.println("getLoginName()����");
		try {
			conn = DBConnection.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				name = rs.getString("name");
			}
		} catch (Exception e) {
			System.out.println("getLoginNameNum���� �߻� : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch (Exception e2) {
				System.out.println("getLoginName close()ȣ�� ����" + e2.getMessage());
				e2.printStackTrace();
			}
		}
		
		return name;
	}
	
	//DB�� board���̺� ���ο� ���� ������ ��� �Խñ� ��ȣ�� 1�� ����
	public void insertBoard(BoardDTO board) {
		
		String sql = "alter table board auto_increment = 1" ;
		try {
			//auto increment �κ��� �ٽ� 1�� �缳�� ���ִ� �κ�
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
			System.out.println("insertBoard() ����Ϸ�");
		} catch (Exception e) {
			System.out.println("insertBoard() ���� �߻� : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				if(rs != null)  rs.close();
			} catch (Exception e2) {
				System.out.println("insertBoard close()ȣ�� ����" + e2.getMessage());
				e2.printStackTrace();
			}
		} 
	}
	
	//���õ� �Խñ��� �󼼳����� �������� �޼���
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
			
			System.out.println("getBoardByNum() ���� �Ϸ�");
		} catch (Exception e) {
			System.out.println("getBoardByNum() ���� �߻� : " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				if(rs != null)  rs.close();
			} catch (Exception e2) {
				System.out.println("getBoardByNum close()ȣ�� ����" + e2.getMessage());
				e2.printStackTrace();
			}
		}
		//��ȸ�� ���� �޼��带 ȣ��
		updateHit(num);
		return board;
	}
	
	//���õ� ���� ��ȸ�� ���� �޼���
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
			
			System.out.println("updateHit() ���� �Ϸ�");
			
		} catch (Exception e) {
			
			System.out.println("updateHit() ���� �߻� : " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				if(rs != null)  rs.close();
				
			} catch (Exception e2) {
				
				System.out.println("updateHit close()ȣ�� ����" + e2.getMessage());
				e2.printStackTrace();
				
			}
		}
	}
	
	//������ �Խñ��� ������ DB�� �����ϴ� �޼���
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
			
			System.out.println("updateBoard() ���� �Ϸ�");
			
		} catch (Exception e) {
			
			System.out.println("updateBoard() ���� �߻� : " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				
			} catch (Exception e2) {
				
				System.out.println("updateBoard close()ȣ�� ����" + e2.getMessage());
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
			System.out.println("deleteBoard() ���� �Ϸ�");
			
		} catch (Exception e) {
			
			System.out.println("deleteBoard() ���� �߻� : " + e.getMessage());
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if(pstmt != null)  pstmt.close();
				if(conn != null)  conn.close();
				
			} catch (Exception e2) {
				
				System.out.println("deleteBoard close()ȣ�� ����" + e2.getMessage());
				e2.printStackTrace();
				
			}
		}
	}
	
}
