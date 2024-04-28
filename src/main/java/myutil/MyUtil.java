package myutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MyUtil {
	public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	public static final String DB = "g2_product"; /* jdbcsample | g2_product | db01 */
	
	public static final String URL = 
			"jdbc:mysql://localhost:3306/"
			+ DB /* Configure using database */
			+ "?serverTimezone=Asia/Taipei";			
	
	public static final String USER = "root";
	public static final String PASSWORD = "123456";
	
	/* My own Driver loader, just for code readable */
	public static void myLoadDriver() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException ce) {
			ce.printStackTrace();
		}
	}
	
	/* CloseResources */
	public static void closeResources(Connection con, PreparedStatement pstmt, ResultSet rs) {
		// TODO Auto-generated method stub
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException se) {
				System.err.println("Error occur when rs.close().");
				se.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				System.err.println("Error occur when pstmt.close().");
				se.printStackTrace();
			}
		}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException se) {
				System.err.println("Error occur when con.close().");
				se.printStackTrace();
			}
		}
	}
}
