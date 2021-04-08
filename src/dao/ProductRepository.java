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
	//ProductRepository인스턴스를 하나만 공유할 수 있도록 싱글톤 패턴을 이용
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
	//모든 상품 정보를 넘겨주는 메서드
	public ArrayList<Product> getAllProducts(){
		String sql = "select * from product";
		
		try {
			conn = getConnection(); //커넥션 얻기
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery(); //DB에 저장되어 있는 상품을 모두 가져온다.
			
			while (rs.next()) {
				Product product = new Product();
				//위의 빈 객체인 product에 각각 db에서 가져온 값을 저장한다.
				product.setProductId(rs.getString("p_id"));
				product.setPname(rs.getString("p_name"));
				product.setUnitPrice(rs.getInt("p_unitPrice"));
				product.setDescription(rs.getString("p_description"));
				product.setCategory(rs.getString("p_manufacturer"));
				product.setUnitsInStock(rs.getLong("p_unitsInStock"));
				product.setCondition(rs.getString("p_condition"));
				product.setFilename(rs.getString("p_filename"));
				
				//ArrayList컬렉션에 product객체 추가
				listOfProducts.add(product);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null ) rs.close();
				if(pstmt != null ) pstmt.close();
				if(conn != null ) conn.close();
				System.out.println("DB연동 해제");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		//위에서 각 객체가 저장되어 ArrayList를 리턴
		return listOfProducts;
	}
	
	//listOfProducts컬렉션에 저장된 상품 목록을 조회를 해서 상품ID와 일치하는 상품을 리턴한다.
	public Product getProductById(String productId) {
		
		String sql = "select * from product where p_id = ?";
		Product productById = new Product();
		
		try {
			conn = getConnection(); //커넥션 얻기
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			//인자값으로 넘어온 productId에 해당하는 상품을 ResultSet객체에 하나만 저장된다.
			rs = pstmt.executeQuery();
			
			//rs가 없을 시
			if(!rs.next()) {
				return null;
			}
			//인자값으로 넘어온 productId값이 있을 시
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
				System.out.println("DB연동 해제");
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			
		}
		return productById;
	}
	
	//상품 추가 메서드
	public void addProduct(Product product) {
		listOfProducts.add(product);
	}
	
	//Connection객체를 리턴하는 DB접속 메서드 생성
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); //드라이버명
			conn = DriverManager.getConnection(url, user, password); //DB연결 객체 얻기
			System.out.println("DB연동 완료");
		} catch (Exception e) {
			System.out.println("DB연동 실패");
			e.printStackTrace();
		}
		return conn;
	}
}
