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
	var status = validatePaymentForm();
	if (status != true)
	{
		 $("#alertError").text(status);
 		 $("#alertError").show();
 		 return;
 	}
 	
	// If valid-------------------------
 	var type = ($("#hidPaymentIDSave").val() == "") ? "POST" : "PUT";

	$.ajax(
 	{
 		url : "PaymentsAPI",
 		type : type,
 		data : $("#formPayment").serialize(),
 		dataType : "text",
 		complete : function(response, status)
 		{
 			onPaymentSaveComplete(response.responseText, status);
 		}
 	}); 
 });

function onPaymentSaveComplete(response, status)
	{
		if (status == "success")
		{
			 var resultSet = JSON.parse(response);
 			 if (resultSet.status.trim() == "success")
			 {
 				$("#alertSuccess").text("Successfully saved.");
 				$("#alertSuccess").show();
 				$("#divPaymentGrid").html(resultSet.data);
 			 } 
 			 else if (resultSet.status.trim() == "error")
			 {
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			 }
 		} 
 		else if (status == "error")
 		{
 			$("#alertError").text("Error while saving.");
 			$("#alertError").show();
 		} 
 		else
 		{
 			$("#alertError").text("Unknown error while saving..");
 			$("#alertError").show();
 		}
		$("#hidPaymentIDSave").val("");
 		$("#formPayment")[0].reset();
}

	// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
		 $("#hidPaymentIDSave").val($(this).data("paymentID"));
		 $("#cusName").val($(this).closest("tr").find('td:eq(0)').text());
		 $("#email").val($(this).closest("tr").find('td:eq(1)').text());
		 $("#billID").val($(this).closest("tr").find('td:eq(2)').text());
 		 $("#areaOffice").val($(this).closest("tr").find('td:eq(3)').text());
 		 $("#cardNum").val($(this).closest("tr").find('td:eq(2)').text());
 		 $("#payAmount").val($(this).closest("tr").find('td:eq(2)').text());
	});
	
	
	
	$(document).on("click", ".btnRemove", function(event)
	{
 		$.ajax(
 		{
 			url : "PaymentsAPI",
 			type : "DELETE",
 			data : "paymentID=" + $(this).data("paymentID"),
 			dataType : "text",
 			complete : function(response, status)
 			{
 				onPaymentDeleteComplete(response.responseText, status);
 			}
 		});
	});


	function onPaymentDeleteComplete(response, status)
	{
		if (status == "success")
 		{
 			var resultSet = JSON.parse(response);
 			if (resultSet.status.trim() == "success")
 			{
 				$("#alertSuccess").text("Successfully deleted.");
 				$("#alertSuccess").show();
 				$("#divPaymentGrid").html(resultSet.data);
 			} 
 			else if (resultSet.status.trim() == "error")
 			{
 				$("#alertError").text(resultSet.data);
 				$("#alertError").show();
 			}
 		} 
 		else if (status == "error")
 		{
 				$("#alertError").text("Error while deleting.");
 				$("#alertError").show();
 		} 
 		else
 		{
 				$("#alertError").text("Unknown error while deleting..");
 				$("#alertError").show();
 		}
}
	

	// CLIENT-MODEL================================================================
	function validatePaymentForm()
	{
		// CUSTOMER NAME
		if ($("#cusName").val().trim() == "")
		{
 			return "Insert Customer Name .";
 		}

		// EMAIL
		if ($("#email").val().trim() == "")
 		{
 			return "Insert Email Address.";
 		}

		// BILL ID
		if ($("#billID").val().trim() == "")
 		{
 			return "Insert Bill ID.";
 		}
 		
 		//AREA OFFICE
 		// EMAIL
		if ($("#areaOffice").val().trim() == "")
 		{
 			return "Insert Area Office.";
 		}
 		
 		//CARD NUMBER
 
		if ($("#cardNum").val().trim() == "")
 		{
 			return "Insert Credit/Debit Card Number.";
 		}
		// PAY AMOUNT
		var tmpPayAmount = $("#payAmount").val().trim();
		if (!$.isNumeric(tmpPayAmount))
		{
 			return "Insert a numerical value for Pay Amount.";
 		}
 		
		// convert to decimal price
		$("#payAmount").val(parseFloat(tmpPayAmount).toFixed(2));


		return true;
	}
	
	
	
	
