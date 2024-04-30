<%-- Copy from 01: select_page.jsp, show word languages  
	p.210 Directive Elements < % @... % > 用來提供整個網頁的說明
	@ page: ...
	@ include: @ include file="/myFolder/myFile.sub" p.195 | "myFolder/myFile.sub"
	@ taglib: 自訂 JSP 標籤(p.227) 與 JSTL(p.241)  
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>
<%@ page import="com.product_detail.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	// 取得 DetailServlet.java(controller)存入 session 的 list 物件
	// 將提供 page1.file 取得查詢到的總筆數，再由 page1.file 進行分頁
	// ~= useBean
	List<ProductDetail> prodDetailList = (List<ProductDetail>)session.getAttribute("list"); // list 變數
%>
	<%-- p.218 id:以專案分工命名 --%>
	<%-- 
	<jsp:useBean id="prodDetailList" scope="session" type="java.util.List<ProductDetail>" /> 
	--%>
	
<html>
<head>
	<title>listAllDetail_getFromSession.jsp</title>
	<style>
		table#table-1 {
			background-color: #CCCCFF;
			border: 2px solid black;
			text-align: center;
		}
		table#table-1 h4 {
			color: red;
			display: block;
			margin-bottom: 1px;
		}
		h4 {
			color: blue;
			display: inline;
		}
	<%-- 
	</style>
	merge 2 style? 
	<style>
	--%>
		table {
			width: 800px;
			background-color: white;
			margin-top: 5px;
			margin-bottom: 5px;
		}
		table, th, td {
			border: 1px solid #CCCCFF;
		}
		table, th, td {
			padding: 5px;
			text-align: center;
		}
	</style>
</head>
<body bgcolor='white'>
<h2>listAllDetail_getFromSession.jsp</h2>
<p>Zoaholic: 7</p>
<%-- 有抓到，沒顯示? --%>
<%-- 
<p>prodDetailList.size() = <%=prodDetailList.size()%></p>
 --%>
<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr>
		<td>
			<h3>所有明細的資料 - listAllDetail_getFromSession.jsp</h3>
			<%-- 
			src catch fail: forward/sendDirect (/) p.195, 196
			DGBifReader.java p.184 
			--%>
			<h4><a href='select_page.jsp'><img src="imges/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
		</td>
	</tr>
</table>

<table>
	<tr>
		<th>明細ID</th>
		<th>訂單ID</th>
		<th>產品ID</th>
		<th>單價</th>
		<th>數量</th>
		<th>小計</th>
	</tr>
	
	<%-- 
	<%@ include file="page1.file" %> 
	--%>
	<%@ include file="page1.jsp" %> 
	<%-- c:forEach p.253 --%>
	<c:forEach var="prodDetail" items="${prodDetailList}" begin="<%=pageIndex%>" end="<%=pageIndex + rowsPerPage - 1%>">
		<tr>
			<td>${prodDetail.prodDetailId}</td>
			<td>${prodDetail.prodOrdId}</td>
			<td>${prodDetail.prodId}</td>
			<td>${prodDetail.unitPrice}</td>
			<td>${prodDetail.prodCount}</td>
			<td>${prodDetail.prodSum}</td>
		</tr>
	</c:forEach>
</table>

<%-- 
<%@ include file="page2.file" %> 
--%>
<%@ include file="page2.jsp" %>
</body>
</html>
