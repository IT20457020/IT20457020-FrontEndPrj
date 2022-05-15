<%@page import="com.payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.min.js"></script>
<script src="Components/payment.js"></script>
</head>
<body>

<div class="container"><div class="row"><div class="col-6">

	<h1>Items Management </h1>

	<form id="formPayment" name="formPayment">
 		Customer Name:
 		<input id="cusName" name="cusName" type="text" class="form-control form-control-sm">
 		<br> 
 		Email Address:
		<input id="email" name="email" type="text" class="form-control form-control-sm">
 		<br> 
 		Bill ID:
 		<input id="billID" name="billID" type="text" class="form-control form-control-sm">
 		<br> 
 		Area Office:
		<input id="areaOffice" name="areaOffice" type="text" class="form-control form-control-sm">
 		<br>
 		
 		Area Office:
		<input id="cardNum" name="cardNum" type="text" class="form-control form-control-sm">
 		<br>
 		
 		Area Office:
		<input id="payAmount" name="payAmount" type="text" class="form-control form-control-sm">
 		<br>
 		<input id="btnSave" name="btnSave" type="button" value="Pay" class="btn btn-primary">
 		<input type="hidden" id="hidPaymentIDSave" name="hidPaymentIDSave" value="">
	</form>
	
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>

	<br>
	<div id="divPaymentGrid">
 		<%
 		payment paymentObj = new payment();
			out.print(paymentObj.readPayments());
 		%>
	</div>
</div> </div> </div>
</body>
</html>