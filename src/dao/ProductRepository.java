package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.Product;

public class ProductRepository {
	
	private ArrayList<Product> listOfProducts = new ArrayList<>();
	//ProductRepository�ν��Ͻ��� �ϳ��� ������ �� �ֵ��� �̱��� ������ �̿�
	private static ProductRepository instance = new ProductRepository();
	public static ProductRepository getInstance() {
		return instance;
	}
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private static String url = "jdbc:mysql://localhost:3306/webmarket?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = "root";
	private static String password = "a1234";
	
	public ProductRepository() {
	}
	//��� ��ǰ ������ �Ѱ��ִ� �޼���
	public ArrayList<Product> getAllProducts(){
		String sql = "select * from product";
		
		try {
			conn = getConnection(); //Ŀ�ؼ� ���
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); //DB�� ����Ǿ� �ִ� ��ǰ�� ��� �����´�.
			
			while (rs.next()) {
				Product product = new Product();
				//���� �� ��ü�� product�� ���� db���� ������ ���� �����Ѵ�.
				product.setProductId(rs.getString("p_id"));
				product.setPname(rs.getString("p_name"));
				product.setUnitPrice(rs.getInt("p_unitPrice"));
				product.setDescription(rs.getString("p_description"));
				product.setCategory(rs.getString("p_manufacturer"));
				product.setUnitsInStock(rs.getLong("p_unitsInStock"));
				product.setCondition(rs.getString("p_condition"));
				product.setFilename(rs.getString("p_filename"));
				
				//ArrayList�÷��ǿ� product��ü �߰�
				listOfProducts.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(pstmt != null ) pstmt.close();
				if(conn != null ) conn.close();
				System.out.println("DB���� ����");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		//������ �� ��ü�� ����Ǿ� ArrayList�� ����
		return listOfProducts;
	}
	
	//listOfProducts�÷��ǿ� ����� ��ǰ ����� ��ȸ�� �ؼ� ��ǰID�� ��ġ�ϴ� ��ǰ�� �����Ѵ�.
	public Product getProductById(String productId) {
		
		String sql = "select * from product where p_id = ?";
		Product productById = new Product();
		
		try {
			conn = getConnection(); //Ŀ�ؼ� ���
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			//���ڰ����� �Ѿ�� productId�� �ش��ϴ� ��ǰ�� ResultSet��ü�� �ϳ��� ����ȴ�.
			rs = pstmt.executeQuery();
			
			//rs�� ���� ��
			if(!rs.next()) {
				return null;
			}
			//���ڰ����� �Ѿ�� productId���� ���� ��
			if(rs.next()) {
				productById.setProductId(rs.getString("p_id"));
				productById.setPname(rs.getString("p_name"));
				productById.setUnitPrice(rs.getInt("p_unitPrice"));
				productById.setDescription(rs.getString("p_description"));
				productById.setCategory(rs.getString("p_manufacturer"));
				productById.setUnitsInStock(rs.getLong("p_unitsInStock"));
				productById.setCondition(rs.getString("p_condition"));
				productById.setFilename(rs.getString("p_filename"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(pstmt != null ) pstmt.close();
				if(conn != null ) conn.close();
				System.out.println("DB���� ����");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return productById;
	}
	
	//��ǰ �߰� �޼���
	public void addProduct(Product product) {
		listOfProducts.add(product);
	}
	
	//Connection��ü�� �����ϴ� DB���� �޼��� ����
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); //����̹���
			conn = DriverManager.getConnection(url, user, password); //DB���� ��ü ���
			System.out.println("DB���� �Ϸ�");
		} catch (Exception e) {
			System.out.println("DB���� ����");
			e.printStackTrace();
		}
		return conn;
	}
}
