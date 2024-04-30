<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Select Page</title>
	<style>
		table#table-1 {
			width: 450px;
			background-color: #CCCCFF;
			margin-top: 5px;
			margin-bottom: 10px;
			border: 3px ridge Gray;
			height: 80px;
			text-align: center;
		}
		table#table-1 h4 {
			color: red;
			display: block;
			margin-bottom: 1px;
		}
		h4{
			color: blue;
			display:inline;
		}
	</style>
</head>
<body bgcolor='white'>
<h2>select_page.jsp</h2>

	<table id="table-1">
		<tr><td><h3>TIA10110-Webapp Product detail: Home</h3><h4>( MVC )</h4></td></tr>
	</table>
	<p>This is the Home page for TIA10110-Webapp Product detail: Home</p>
	<p>Zoaholic: 3</p>
	<h3>資料查詢:</h3>
	
	<%-- ErrorMsgs list --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<ul>
	<!-- 404 -1. Cannot find listAllDetail_getFromSession.jsp -->
		<!-- BUG: Don't know why but work. Write usbBean & after --0430 09:01 -->
		<li><a href='detail.do?action=getAll'>List</a> all Detail (getFromSession).<br></li>
	<!-- Not print data -->
		<li><a href='listAllDetail_byDAO.jsp'>List</a> all Detail (byDAO).<br></li>
		
		<li>
			<FORM METHOD="post" ACTION="detail.do" >
			<b>輸入明細ID (Ex: 12000001):</b>
			<input type="text" name="prod_detail_id">
			<input type="hidden" name="action" value="getOne_For_Display">
			<input type="submit" value="送出">
			<h4>(資料格式驗証 by Controller).</h4>
			</FORM>
		</li>
		
		<li>
			<FORM METHOD="post" ACTION="detail.do" name="form1">
				<b>輸入明細ID (Ex: 12000001):</b>
				<input type="text" name="prod_detail_id">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出" onclick="fun1()">
				<h4>(資料格式驗証 by JavaScript).</h4>
			</FORM>
		</li>
		
		<!-- Why useBean here? p.218 -->
		
		<jsp:useBean id="detailDao" scope="page" class="com.product_detail.model.ProductDetailDAOImpl" />
		 
		<li> <!-- BUG: Options not show 4/30 -->
			<FROM METHOD="post" ACTION="detail.do">
				<b>選擇明細ID:</b>
				<select size="1" name="prod_detail_id">
					<c:forEach var="prodDetail" items="${detailDAO.all}"> ${detailDAO.all} <%-- ?什麼語法? .all 是什麼 --%>
						<option value="${prodDetail.prodDetailId}">${prodDetail.prodDetailId}					
					</c:forEach>
				</select>
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出">
			</FROM>
		</li>
		 	
	</ul>
	
	<script>
		function fun1() {
			with(document.form1){
				if (prod_detail_id.value=="") {
					alert("請輸入明細ID!");
				} else if (isNaN(prod_detail_id.value)){ // isNaN(): Not a Number
					alert("明細ID格式不正確"); 
				} else if ((prod_detail_id.value > 12999999) || (prod_detail_id.value < 12000000))  {
					alert("請輸入介於12000000~12999999之間的數字!");
				} else {
					submit();
				}
			}
		}
	</script>
	
</body>
</html>