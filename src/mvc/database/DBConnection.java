package mvc.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	
	public static Connection getConnection() {
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/webmarket?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String user = "root";
		String password = "a1234";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("�����ͺ��̽��� ����Ǿ����ϴ�.");
		} catch (Exception e) {
			System.out.println("�����ͺ��̽��� ������ ���� �ʾҽ��ϴ�.");
			e.printStackTrace();
		}		
		return conn;
	}	
}

