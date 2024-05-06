package com.product_order.controller;

import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Timestamp;

import com.product_order.model.*;

public class OrderServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		// Query with getAll
//		if ("getAll".equals(action)) {// In 0201, getAll(link) doesn't need to check, link to JSP directly.}
		
		// Query with FindByPk
		if ("getOneForDisplay".equals(action)) { // 來自 selectPage.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Receive request parameters, format check ----------------
			String str = req.getParameter("prodOrdId");
			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入明細編號");
			}
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/order/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			Integer prodOrdId = null;
			try {
				prodOrdId = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("明細ID格式不正確");
			}
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/order/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			// --- 2. Start query data ----------------------------------------
			// Service 做法 
			ProductOrderService orderSvc = new ProductOrderService();
			ProductOrder prodOrd = orderSvc.getOneOrder(prodOrdId);
			if (prodOrd == null) { errorMsgs.add("查無資料"); }
			// Forward back with errorMsgs
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/order/selectPage.jsp");
				failureView.forward(req, res);
				return; // 中斷程式
			}
			
			// --- 3. Finish query. Prepare to send the success view ----------
			req.setAttribute("prodOrd", prodOrd); // Query from DB, store to req
			String url = "/order/listOneOrder.jsp"; 
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneOrder.jsp
			successView.forward(req, res);
		} // END of if ("getOneForDisplay".equals(action)
		
		if ("getOneForUpdate".equals(action)) { // 來自 listAllOrder.jsp 的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Check input format --------------------------------------
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId"));
			// --- 2. Query data ----------------------------------------------
			var orderSvc = new ProductOrderService();
			ProductOrder prodOrd = orderSvc.getOneOrder(prodOrdId);
			// --- 3. Finish query. Prepare to send the success view ----------
			req.setAttribute("prodOrd", prodOrd);
			String url = "/order/updateOrderInput.jsp"; // here
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 updateOrderInput.jsp
			successView.forward(req, res);
		} // END of if ("getOneForUpdate".equals(action))
		
		if ("update".equals(action)) { // 來自 updateOrderInput.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view, check if necessary?
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Check input format --------------------------------------
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId"));
			Integer memId = 	Integer.valueOf(req.getParameter("memId")); 
			Timestamp estTime = Timestamp.valueOf(req.getParameter("estTime")); 
			Integer ordStatus = Integer.valueOf(req.getParameter("ordStatus"));
			Integer total = 	Integer.valueOf(req.getParameter("total")); 
			String  recipient = req.getParameter("recipient");
			String  recAddr = 	req.getParameter("recAddr");
			
			// data forward check
//			System.out.println("JSP: prodOrdId = " + prodOrdId);
//			System.out.println("EL : prodOrdId    = " + prodOrdId);
//			System.out.println("EL : unitPrice    = " + unitPrice);
//			System.out.println("EL : prodCount    = " + prodCount);
//			System.out.println("EL : prodSum      = " + prodSum);
			
			var prodOrd = new ProductOrder(prodOrdId, memId, estTime, ordStatus, total, recipient, recAddr);	
			// 做錯誤檢查
//			if (prodCount < 1) { // HERE
//				errorMsgs.add("商品數量需 > 0");
//				prodOrd.setProdCount(1); // 將商品數量設為 1
//				prodOrd.setProdSum(prodOrd.getUnitPrice()); // 將明細小計設為 1*價格 
//			} // END of (prodCount <= 0)
			
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("prodOrd", prodOrd);
				RequestDispatcher failureView = req.getRequestDispatcher("/order/updateOrderInput.jsp");
				failureView.forward(req, res);
				return; // 程式中斷
			} // END of if (!errorMsgs.isEmpty())

			// --- 2. Update data ---------------------------------------------
			var orderSvc = new ProductOrderService();
			prodOrd = orderSvc.updateOrder(prodOrd); // 反正都包好了，直接用 obj 
			// --- 3. Update success. Prepare send the success view -----------
			req.setAttribute("prodOrd", prodOrd); // After DB update success, save obj to req 
			String url = "/order/listOneOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // forward to listOneOrder.jsp 
			successView.forward(req, res);
			
		} // END of if ("update".equals(action))
		
		if ("insert".equals(action)) { // 來自 allDetail.jsp 的請求 | 未實作
			// Unimplemented
		} // END of if ("insert".equals(action))
		
		if ("delete".equals(action)) { // 來自 listAllOrder.jsp 的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store in req scope for ErrorPage view
			req.setAttribute("errorMsgs", errorMsgs);
			
			// --- 1. Receive parameter ---------------------------------------
			// Get from table, no need check format
			Integer prodOrdId = Integer.valueOf(req.getParameter("prodOrdId"));
			// --- 2. Delete data ---------------------------------------------
			var orderSvc = new ProductOrderService();
			orderSvc.deleteOrder(prodOrdId);
			
			// --- 3. Finish delete. Prepare send the success view ------------
			String url = "/order/listAllOrder.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
		} // END of if ("delete".equals(action))
	}
}
