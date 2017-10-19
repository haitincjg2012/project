// JavaScript Document
$(document).ready(function(){
	
	$("#add").click(function(){
		
		$("#select4 option:selected").appendTo("#select3");
		
		});
		
	$("#add_all").click(function(){
		
		$("#select4 option").appendTo("#select3");
		
		});
		
	$("#remove").click(function(){
		
		$("#select3 option:selected").appendTo("#select4");
		
		});	
		
	$("#remove_all").click(function(){
		
		$("#select3 option").appendTo("#select4");
		
		});	
		
		$("#select4").dblclick(function(){
			
		$("#select4 option:selected").appendTo("#select3");	
			
			});
		
				$("#select3").dblclick(function(){
			
		$("#select3 option:selected").appendTo("#select4");	
			
			});
		

	});