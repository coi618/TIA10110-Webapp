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

public class DetailServletBK extends HttpServlet {

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
			// --- 1. Start query data, don't need format check ---------------
			ProductDetailDAO detailDao = new ProductDetailDAOImpl();
//			List<ProductDetail> list = detailDao.getAll();
			List<ProductDetail> prodDetailList = detailDao.getAll();

			// --- 2. Query finish. Prepare to send the success view ----------
			HttpSession session = req.getSession(); // Session Tracking p.147
//			session.setAttribute("list", list); // Put list(query from DB) to session
			session.setAttribute("list", prodDetailList); // useBean id = "list" + page*.file use "list"
			/*
			 * Check what session.XetAttribute("XXX") carry
			System.out.println("session.getAttribute(\"list\")" + session.getAttribute("list"));
			*/
			// --- 3. Send the success view -----------------------------------
				// webapp(/)detail/listAllDetail_getFromSession.jsp
//			System.out.println("I'm here---------------------------");
			String url = "/detail/listAllDetail_getFromSession.jsp";
			// p.195 1) 對項先執行(不管 4xx 5xx，先把 URL 貼過來) -> JSP file [/detail/http:/localhost:8081/TIA10110-Webapp/detail/listAllDetail_getFromSession.jsp] not found
//			url = "http://localhost:8081/TIA10110-Webapp/detail/listAllDetail_getFromSession.jsp";
			// 2) http:// 拿掉 -> JSP file [/detail/localhost:8081/TIA10110-Webapp/detail/listAllDetail_getFromSession.jsp] not found
//			url = "localhost:8081/TIA10110-Webapp/detail/listAllDetail_getFromSession.jsp";
			// 3) 保留 / -> JSP file [/localhost:8081/TIA10110-Webapp/detail/listAllDetail_getFromSession.jsp] not found
//			url = "/localhost:8081/TIA10110-Webapp/detail/listAllDetail_getFromSession.jsp";
			// 4) 移除 (ROOT: /localhost:8081) -> JSP file [/TIA10110-Webapp/detail/listAllDetail_getFromSession.jsp] not found
//			url = "/TIA10110-Webapp/detail/listAllDetail_getFromSession.jsp";
			// 5) 移除 (專案名: /Webapp) -> Worked, url: http://localhost:8081/TIA10110-Webapp/detail/detail.do?action=getAll
//			url = "/detail/listAllDetail_getFromSession.jsp";
			// 6) 移除 同層(select_page.jsp)資料夾 /detail -> Worked, url: http://localhost:8081/TIA10110-Webapp/detail/detail.do?action=getAll
//			url = "listAllDetail_getFromSession.jsp";
			// Test p.109
//			System.out.println(
//					"req.getScheme() = " 		+ req.getScheme() + 
//					"\nreq.getServerName() = " 	+ req.getServerName() +
//					"\nreq.getServerPort() = " 	+ req.getServerPort() +
//					"\nreq.getContextPath() = " + req.getContextPath() +
//					"\nreq.getServletPath() = " + req.getServletPath() +
//					
//					"\nreq.getPathInfo() = " 	+ req.getPathInfo() +
//					"\nreq.getPathTranslated() = " + req.getPathTranslated() +
//					"\nreq.getRequestURI() = " 	+ req.getRequestURI() +
//					"\nreq.getQueryString() = " + req.getQueryString() +
//					"\nreq.getProtocol() = " 	+ req.getProtocol() +
//					
//					"\nreq.getMethod() = " 		+ req.getMethod() +
//					"\nreq.getHeader(\"Content-Type\") = " + req.getHeader("Content-Type") +
//					"\nreq.getContentType() = " + req.getContentType() +
//					"\nreq.getContentLength() = " + req.getContentLength() 
//					);
			
				// 成功轉交 listAllDetail_getFromSession.jsp | p.192
			RequestDispatcher successView = req.getRequestDispatcher(url); 
			successView.forward(req, res);
//			System.out.println("I've forward!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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
