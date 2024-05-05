package test;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

import myutil.MyUtil;
import com.product_detail.model.*;

public class TryJDBC_1 extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		res.setContentType("text/html"); // p.128
		PrintWriter out = res.getWriter();
		// Step1. - Load driver
		MyUtil.myLoadDriver();
		try {
			// Step2. - Get connection 
			con = DriverManager.getConnection(MyUtil.URL, MyUtil.USER, MyUtil.PASSWORD);
			// Step3. - Send SQL command
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM product_detail");
			
			out.println(
				"<HTML>"
				+ "<header><title>tryJDBC1.java</title></header>"
				+ "<body><ul>");
			
			while (rs.next()) {
				out.println(
					"<li>"
					+ rs.getString(1) + " " + rs.getString(2)
					+ "</li>");
			}
			
			out.println("</ul></body></html>");
		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			MyUtil.closeResources(con, null, rs);
		}
	}

}
