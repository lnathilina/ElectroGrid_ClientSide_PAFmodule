<%@ page import="com.Supplier"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Supplier</title>
<link rel="stylesheet" href="Views/bootstrap.min.css"> 
<script src="Components/jquery-3.4.1.min.js"></script> 
<script src="Components/validationfile.js"></script> 
</head>
<body>
<div class="container"> 
	<div class="row">  
		<div class="col-6"> 
			<h1>Supplier Management</h1>
				<form id="formSupplier" name="formSupplier" method="post" action="SupplierUI.jsp">  
					Account No:  
 	 				<input id="AccNo" name="AccNo" type="text"  class="form-control form-control-sm">
					<br>Start Date:   
  					<input id=StartDate name="StartDate" type="date" class="form-control form-control-sm">   
  					<br>End Date:   
  					<input id="LastDate" name="LastDate" type="date"  class="form-control form-control-sm">
  					<br>No Of Units:   
  					<input id="NoOfUnits" name="NoOfUnits" type="text"  class="form-control form-control-sm">
  					<br>Amount:   
  					<input id="UnitPrice" name="UnitPrice" type="text"  class="form-control form-control-sm">
					<br>  
					<input id="btnSave" name="btnSave" type="button" value="SAVE" class="btn btn-primary">  
					<input type="hidden" id="hidSupplierIDSave" name="hidSupplierIDSave" value=""> 
				</form>
				
				<div id="alertSuccess" class="alert alert-success"> </div>
				
			   <div id="alertError" class="alert alert-danger"></div>
				
			   <br>
				<div id="divSUpplierGrid">
					<%
					Supplier supplierObj = new Supplier();
									out.print(supplierObj.readSupplier());
					%>
				</div>
				
				 
			</div>
		</div>
</div>
 
</body>
</html>