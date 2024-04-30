<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	int rowsPerPage = 1; // 每頁的筆數
	int rowNumber = 0;   // 總筆數 
	int pageNumber = 0;  // 總頁數
	int whichPage = 1;   // 第幾頁
	int pageIndexArray[] = null;
	int pageIndex = 0;
%>

<%
	/* Test: This is command in the format 1 */
	// Test: This is command in the format 2
	
	/* 	
		DetailServlet.java > session.setAttribute("list", list); X 
		listAllDetail_getFromSession.jsp > List<ProductDetail> prodDetailList = (List<ProductDetail>)session.getAttribute("list");
	*/
	/* rowNumber = prodDetailList.size(); */ // Error:500 | Been include to *.jsp, cannot debug
	rowNumber = 12;
	if (rowNumber % rowsPerPage != 0) {
		pageNumber = rowNumber / rowsPerPage + 1;
	} else {
		pageNumber = rowNumber / rowsPerPage;
	}
	
	pageIndexArray = new int[pageNumber];
	for (int i = 0; i < pageIndexArray.length; i++) {
		pageIndexArray[i] = i * rowsPerPage;
	}
%>

<%
	/* Fix the logic when free */
	try {
		whichPage = Integer.parseInt(request.getParameter("whichPage"));
		pageIndex = pageIndexArray[whichPage - 1];
	} catch (NumberFormatException e) { // error at 1st call
		whichPage = 1;
		pageIndex = 0;
	} catch (ArrayIndexOutOfBoundsException ae) {
		if (pageNumber > 0) { // Out of range
			whichPage = pageNumber;
			pageIndex = pageIndexArray[pageNumber - 1];
		}
	}
%>