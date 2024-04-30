package com.product_detail.controller;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.product_detail.model.ProductDetail;
import com.product_detail.model.ProductDetailDAO;
import com.product_detail.model.ProductDetailDAOImpl;

public class DetailServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// req.getParameter("name") 之字元編碼處理
		// > TomCat 8.5: p.316(方案2) useBodyEncodingForURI="true" (修改 server.xml:63)
		// 強迫在傳送 GET 的 req 參數時，使用與 POST 相同的字元編碼
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// Query with getAll
		if ("getAll".equals(action)) {
			// --- 1. Start query data, don;t need format check ---------------
			ProductDetailDAO detailDao = new ProductDetailDAOImpl();
			List<ProductDetail> list = detailDao.getAll();

			// --- 2. Query finish. Prepare to send the success view ----------
			HttpSession session = req.getSession(); // Session Tracking p.147
			session.setAttribute("list", list); // Put list(query from DB) to session
			// --- 3. Send the success view -----------------------------------
				// webapp(/)detail/listAllDetail_getFromSession.jsp
			System.out.println("I'm here---------------------------");
			String url = "/detail/listAllDetail_getFromSession.jsp";
//			String url = "listAllDetail_getFromSession.jsp";
				// 成功轉交 listAllDetail_getFromSession.jsp | p.192
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
			System.out.println("I've forward!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			return;
		}
		
		// Query with FindByPk
		if ("getOne_For_Display".equals(action)) { // 來自 select_page.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			// p.121 小 > 協同的範圍
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Receive request parameters, format check ----------------
			String str = req.getParameter("prod_detail_id");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入明細編號");
			}
			// Send the use back to the form when errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/detail/select_page.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			Integer prodDetailId = null;
			try {
				prodDetailId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("明細ID格式不正確");
			}
			// Send the use back to the form when errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/detail/select_page.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			// --- 2. Start query data ----------------------------------------
			ProductDetailDAO detailDao = new ProductDetailDAOImpl();
			ProductDetail prodDetail = detailDao.findByPK(prodDetailId);
			if (prodDetail == null) { errorMsgs.add("查無資料"); }
			// Send the use back to the form when errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/detail/select_page.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			// --- 3. Finish query. Prepare to send the success view ----------
			req.setAttribute("prodDetail", prodDetail); // Query from DB, store to req
			String url = "/detail/listOneDetail.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneDetail.jsp
			successView.forward(req, res);
		}

	}
}
