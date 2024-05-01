<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product_detail.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	ProductDetailDAO detailDAO = new ProductDetailDAOImpl();
	/* 此行的 list 物件將提供 page1.file 的第 11(? 行取得查詢到的總筆數，再由 page1.file 進行分頁 */
	List<ProductDetail> list = detailDAO.getAll();
	/* 將上一行的 list 變數(物件)存入當前頁面 pageContext，再由底下的第 37(tmp 行的 JSTL forEach 印出結果 */
	pageContext.setAttribute("list", list); 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>所有明細資料 - listAllDetail_byDAO.jsp</title>
<style>
	table#table-1 {
		background-color: #CCCCFF;
		border: 2px;
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
	th, td {
		padding: 5px;
		text-align: center;
	}
</style>
</head>
<body bgcolor='white'>
	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<%-- 
	<p>Zoaholic: 1</p>
	 --%>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有員工資料 - listAllDetail_byDAO.jsp</h3>
				<h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
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
		
		<%@ include file="page1.file" %>
		<%-- 
		<%@ include file="page1.jsp" %> 
		--%>
		<%-- c:forEach p.253 --%>
		<%-- 
		<p>list: </p>
		<c:out value="${list}"></c:out>
		<p>pageIndex=<%=pageIndex%> | ${pageIndex}</p> <%-- 0 | "" -- %>
		 --%>
		<c:forEach var="prodDetail" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
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
	<%@ include file="page2.jsp" %> 
	--%>
	<%@ include file="page2.file" %>
	</body>
</html>