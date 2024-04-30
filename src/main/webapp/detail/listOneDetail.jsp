<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.product_detail.model.*" %>
<%-- 此頁練習採用 Script 的寫法取值 --%>

<%
	ProductDetail prodDetail = (ProductDetail) request.getAttribute("prodDetail"); 
	// DetailServlet.java: 93, 存入 req 的 prodDetail 物件
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>明細資料 - listOneDetail.jsp</title>
<style></style>
</head>
<body>
<a href="select_page.jsp">回首頁</a>

<table>
	<tr>
		<th>明細ID</th>
		<th>訂單ID</th>
		<th>產品ID</th>
		<th>單價</th>
		<th>數量</th>
		<th>小計</th>
	</tr>
	<tr>
	<!-- p.198 JSP / p.241 JSTL -->
		<td><%=prodDetail.getProdDetailId()%></td>
		<td><%=prodDetail.getProdOrdId()%></td>
		<td><%=prodDetail.getProdId()%></td>
		<td><%=prodDetail.getUnitPrice()%></td>
		<td><%=prodDetail.getProdCount() %></td>
		<td><%=prodDetail.getProdSum()%></td>
	</tr>
</table>

</body>
</html>