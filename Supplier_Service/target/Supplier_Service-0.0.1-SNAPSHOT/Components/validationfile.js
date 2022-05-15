$(document).ready(function() 
{  
		$("#alertSuccess").hide();  
	    $("#alertError").hide(); 
}); 
 
 
// SAVE ============================================ 
$(document).on("click", "#btnSave", function(event) 
{  
	// Clear alerts---------------------  
	$("#alertSuccess").text("");  
	$("#alertSuccess").hide();  
	$("#alertError").text("");  
	$("#alertError").hide(); 
 
	// Form validation-------------------  
	var status = validateSupplierForm();  
	if (status != true)  
	{   
		$("#alertError").text(status);   
		$("#alertError").show();   
		return;  
	} 
 
	// If valid------------------------  
	var type = ($("#hidSupplierIDSave").val() == "") ? "POST" : "PUT"; 

	$.ajax( 
	{  
			url : "SupplierService",   
			type : type,  
			data : $("#supplierForm").serialize(),  
			dataType : "text",  
			complete : function(response, status)  
			{   
				onBSupplierSaveComplete(response.responseText, status);  
			} 
	}); 
}); 


function onSupplierSaveComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			$("#alertSuccess").text("Successfully saved.");    
			$("#alertSuccess").show(); 

			$("#divSupplyierGrid").html(resultSet.data);   
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		} 

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while saving.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while saving..");   
		$("#alertError").show();  
	} 

	$("#hidSupplierIDSave").val("");  
	$("#formSupplier")[0].reset(); 
} 

 
// UPDATE========================================== 
$(document).on("click", ".btnUpdate", function(event) 
{     
	$("#hidSupplierIDSave").val($(this).closest("tr").find('#hidSupplierIDUpdate').val());     
	$("#AccNo").val($(this).closest("tr").find('td:eq(0)').text());     
	$("#UnitPrice").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#StartDate").val($(this).closest("tr").find('td:eq(2)').text());
	$("#LastDate").val($(this).closest("tr").find('td:eq(3)').text());   
	$("#NoOfUnits").val($(this).closest("tr").find('td:eq(4)').text()); 
}); 




//REMOVE===========================================
$(document).on("click", ".btnRemove", function(event) 
{  
	$.ajax(  
	{   
		url : "ServiceBilling",   
		type : "DELETE",   
		data : "Sup_ID=" + $(this).data("supplierid"),   
		dataType : "text",   
		complete : function(response, status)   
		{    
			onSupplierDeleteComplete(response.responseText, status);   
		}  
	}); 
}); 

function onSupplierDeleteComplete(response, status) 
{  
	if (status == "success")  
	{   
		var resultSet = JSON.parse(response); 

		if (resultSet.status.trim() == "success")   
		{    
			
			$("#alertSuccess").text("Successfully deleted.");    
			$("#alertSuccess").show(); 
		
			$("#divSupplierGrid").html(resultSet.data); 
			
		} else if (resultSet.status.trim() == "error")   
		{    
			$("#alertError").text(resultSet.data);    
			$("#alertError").show();   
		}
		

	} else if (status == "error")  
	{   
		$("#alertError").text("Error while deleting.");   
		$("#alertError").show();  
	} else  
	{   
		$("#alertError").text("Unknown error while deleting..");   
		$("#alertError").show();  
	}
}
 
// CLIENT-MODEL========================================================================= 
function validateSupplierForm() 
{  
	// ACCOUNT NO-----------------------
	 var tmpAcc = $("#AccountNo").val().trim();
	if (!$.isNumeric(tmpAcc)) 
		{
		return "Insert Account No.";
		} 
		// PRICE-------------------------------
	 var tmpPirce = $("#UnitPrice").val().trim();
	if (!$.isNumeric(tmpPirce)) 
	 {
	 return "Insert Price.";
	 }
	
	// DATE---------------------------  
	if ($("#StartDate").val().trim() == "")  
	{   
		return "Insert Date.";  
	}
	
	// 	DATE-------------------------------
	if ($("#LastDate").val().trim() == "")  
	{   
		return "Insert Date.";  
	}
	
	// UNIT-------------------------------
	 var tmpUnit = $("#NoOfUnits").val().trim();
	if (!$.isNumeric(tmpUnit)) 
	 {
	 return "Insert Unit.";
	 }


	return true; 
}