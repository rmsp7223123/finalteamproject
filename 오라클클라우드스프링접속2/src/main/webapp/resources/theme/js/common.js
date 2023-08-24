$(function(){
		$('#listModal button.close').click(function(){
			$('#listModal').removeClass("show").addClass("fade");
			$("#listModal").modal("hide");
		})
	})